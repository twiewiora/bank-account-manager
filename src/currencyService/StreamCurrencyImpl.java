package currencyService;

import gen.grpc.Currency;
import gen.grpc.CurrencyList;
import gen.grpc.CurrencyValue;
import gen.grpc.CurrencyValueList;
import gen.grpc.StreamCurrencyGrpc.StreamCurrencyImplBase;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StreamCurrencyImpl extends StreamCurrencyImplBase {

    private Map<Currency, Double> currencyMap = new ConcurrentHashMap<>();

    private List<CurrencyTask> currencyTasks = new ArrayList<>();

    @Override
	public void getCurrenciesStream(CurrencyList currencyList, StreamObserver<CurrencyValue> responseObserver) {
        System.out.println("[getCurrenciesStream] Add bank to currency stream");

        currencyTasks.add(new CurrencyTask(currencyList.getCurrencyList(), responseObserver));
	}

	@Override
    public void getCurrencyStates(CurrencyList currencyList, StreamObserver<CurrencyValueList> responseObserver) {
        System.out.println("[getCurrencyStates] Update currency courses");

        CurrencyValueList.Builder builder = CurrencyValueList.newBuilder();

        for (Currency currency: currencyList.getCurrencyList()) {
            builder = builder.addValues(CurrencyValue
                    .newBuilder()
                    .setCurrency(currency)
                    .setValue(currencyMap.get(currency))
                    .build());
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    public void putNewCourse(CurrencyValue arg) {
        Currency type = arg.getCurrency();
        double value = arg.getValue();

        System.out.println("Updating course of " + type + ", new course: " + value);
        currencyMap.put(type, value);

        for (CurrencyTask currencyTask : new ArrayList<>(currencyTasks)) {
            if (currencyTask.currencyList.contains(type)) {
                try {
                    currencyTask.observer.onNext(arg);
                } catch (StatusRuntimeException e) {
                    currencyTasks.remove(currencyTask);
                }
            }
        }
    }
}
