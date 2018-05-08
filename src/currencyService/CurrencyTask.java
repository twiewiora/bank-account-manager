package currencyService;

import gen.grpc.Currency;
import gen.grpc.CurrencyValue;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class CurrencyTask {

    final List<Currency> currencyList;

    final StreamObserver<CurrencyValue> observer;

    public CurrencyTask(List<Currency> currencyList, StreamObserver<CurrencyValue> observer) {
        this.currencyList = new ArrayList<Currency>(currencyList);
        this.observer = observer;
    }
}
