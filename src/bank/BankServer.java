package bank;

import gen.grpc.Currency;
import gen.thrift.*;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankServer {

    private static final Logger logger = Logger.getLogger(BankServer.class.getName());

    private final int PORT = 9091;

    private CurrencyClient currencyClient = new CurrencyClient("localhost", 50051,
            Arrays.asList(Currency.EUR, Currency.USD, Currency.CHF, Currency.PLN));

    private Map<Integer, BankAccount> usersMap = new ConcurrentHashMap<>();

    public static void main(String [] args) {
        final BankServer bankServer = new BankServer();
        logger.log(Level.INFO, "Starting currencyClient on BankServer ...");
        new Thread(bankServer.currencyClient).start();
        bankServer.start();
    }

    public void start() {
        try {
            AccountFactory.Processor<AccountFactoryHandler> processor1 = new AccountFactory.Processor<>(new AccountFactoryHandler(this));
            AccountService.Processor<AccountServiceHandler> processor2 = new AccountService.Processor<>(new AccountServiceHandler(this));
            PremiumAccountService.Processor<PremiumAccountServiceHandler> processor3 = new PremiumAccountService.Processor<>(new PremiumAccountServiceHandler(this));

            TServerTransport serverTransport = new TServerSocket(PORT);

            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TMultiplexedProcessor multiplex = new TMultiplexedProcessor();
            multiplex.registerProcessor("AccountFactory", processor1);
            multiplex.registerProcessor("AccountService", processor2);
            multiplex.registerProcessor("PremiumAccountService", processor3);

            TServer server = new TSimpleServer(
                    new Args(serverTransport)
                            .protocolFactory(protocolFactory)
                            .processor(multiplex));

            logger.log(Level.INFO, "Starting the multiplex server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Currency thriftToGrpcCurrency(CurrencyType currencyType) {
        switch (currencyType){
            case PLN:
                return Currency.PLN;
            case EUR:
                return Currency.EUR;
            case USD:
                return Currency.USD;
            case CHF:
                return Currency.CHF;
        }
        return null;
    }

    public Map<Integer, BankAccount> getUsersMap() {
        return usersMap;
    }

    public CurrencyClient getCurrencyClient() {
        return currencyClient;
    }

    //    public static void multithread()
//    {
//        try {
//            Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(1));
//            TNonblockingServerTransport nonblockserverTransport = new TNonblockingServerSocket(9092);
//
//            // Use this for a multithreaded server
//            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(nonblockserverTransport).processor(processor));
//            System.out.println("Starting the multithread server...");
//            server.serve();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
