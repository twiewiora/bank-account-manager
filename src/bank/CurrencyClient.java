package bank;

import gen.grpc.*;
import gen.grpc.Currency;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyClient implements Runnable {

	private static final Logger logger = Logger.getLogger(CurrencyClient.class.getName());

	private final ManagedChannel channel;

    private final StreamCurrencyGrpc.StreamCurrencyBlockingStub streamCurrencyBlockingStub;

    private final Map<Currency, Double> currencyMap = new HashMap<>();

	public CurrencyClient(String host, int port, List<Currency> currencies) {
		channel = ManagedChannelBuilder.forAddress(host, port)
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
				.usePlaintext(true)
				.build();

        streamCurrencyBlockingStub = StreamCurrencyGrpc.newBlockingStub(channel);
	}

	@Override
	public void run() {
        CurrencyList currencyListToRequest = CurrencyList.newBuilder().addAllCurrency(currencyMap.keySet()).build();
	    try {
            CurrencyValueList currencyValueListResponse = streamCurrencyBlockingStub.getCurrencyStates(currencyListToRequest);

            for (CurrencyValue currencyValue : currencyValueListResponse.getValuesList()) {
                logger.log(Level.INFO, "[Download] Currency: " + currencyValue.getCurrency().toString() + " :: " + currencyValue.getValue());
                currencyMap.put(currencyValue.getCurrency(), currencyValue.getValue());
            }

            Iterator<CurrencyValue> currencyValueIterator;
            try {
                currencyValueIterator = streamCurrencyBlockingStub.getCurrenciesStream(currencyListToRequest);
                while (currencyValueIterator.hasNext()) {
                    CurrencyValue currencyValue = currencyValueIterator.next();

                    currencyMap.put(currencyValue.getCurrency(), currencyValue.getValue());
//                    logger.log(Level.INFO, "[Update] Currency: " + currencyValue.getCurrency().toString() + " :: " + currencyValue.getValue());
                }
            } catch (StatusRuntimeException ex) {
                logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            }
        } catch (io.grpc.StatusRuntimeException e){
            logger.log(Level.WARNING, "Could not locate currency server");
            System.exit(1);
        }
	}

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Double getCurrencyCource(Currency currency) {
	    return currencyMap.get(currency);
    }
}
