package currencyService;

import gen.grpc.Currency;
import gen.grpc.CurrencyValue;
import io.grpc.StatusRuntimeException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencySimulator implements Runnable {

    private static final Logger logger = Logger.getLogger(CurrencyServer.class.getName());

    private StreamCurrencyImpl streamCurrency;

    private Currency currency;

    public CurrencySimulator(StreamCurrencyImpl streamCurrency, Currency currency) {
        this.streamCurrency = streamCurrency;
        this.currency = currency;
    }

    public void run() {
        while (true) {
            try {
                try {
                    Thread.sleep((long)(Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CurrencyValue arg = CurrencyValue
                        .newBuilder()
                        .setCurrency(currency)
                        .setValue(Math.random() + 1)
                        .build();
                streamCurrency.putNewCourse(arg);
            }
            catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            }
        }
    }
}
