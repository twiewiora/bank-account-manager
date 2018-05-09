package currencyService;

import java.io.IOException;
import java.util.logging.Logger;

import gen.grpc.Currency;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class CurrencyServer {

	private static final Logger logger = Logger.getLogger(CurrencyServer.class.getName());

	private final int PORT = 50051;

	private Server server;

	private StreamCurrencyImpl streamCurrency = new StreamCurrencyImpl();

    public static void main(String[] args) throws IOException, InterruptedException {
        final CurrencyServer server = new CurrencyServer();
        server.start();
        server.blockUntilShutdown();
    }

	private void start() throws IOException {

	    for (Currency currency : Currency.values()) {
	        if (!currency.equals(Currency.UNRECOGNIZED) && !currency.equals(Currency.PLN)) {
	            new Thread(new CurrencySimulator(streamCurrency, currency)).start();
            }
        }

		server = ServerBuilder.forPort(PORT)
				.addService(streamCurrency)
				.build()
				.start();
		logger.info("Currency server started, listening on " + PORT);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				CurrencyServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}
}
