package gen.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: currency.proto")
public final class StreamCurrencyGrpc {

  private StreamCurrencyGrpc() {}

  public static final String SERVICE_NAME = "currency.StreamCurrency";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<gen.grpc.CurrencyList,
      gen.grpc.CurrencyValue> METHOD_GET_CURRENCIES_STREAM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "currency.StreamCurrency", "GetCurrenciesStream"),
          io.grpc.protobuf.ProtoUtils.marshaller(gen.grpc.CurrencyList.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(gen.grpc.CurrencyValue.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<gen.grpc.CurrencyList,
      gen.grpc.CurrencyValueList> METHOD_GET_CURRENCY_STATES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "currency.StreamCurrency", "GetCurrencyStates"),
          io.grpc.protobuf.ProtoUtils.marshaller(gen.grpc.CurrencyList.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(gen.grpc.CurrencyValueList.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamCurrencyStub newStub(io.grpc.Channel channel) {
    return new StreamCurrencyStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamCurrencyBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StreamCurrencyBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static StreamCurrencyFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StreamCurrencyFutureStub(channel);
  }

  /**
   */
  public static abstract class StreamCurrencyImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCurrenciesStream(gen.grpc.CurrencyList request,
        io.grpc.stub.StreamObserver<gen.grpc.CurrencyValue> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CURRENCIES_STREAM, responseObserver);
    }

    /**
     */
    public void getCurrencyStates(gen.grpc.CurrencyList request,
        io.grpc.stub.StreamObserver<gen.grpc.CurrencyValueList> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CURRENCY_STATES, responseObserver);
    }

    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_CURRENCIES_STREAM,
            asyncServerStreamingCall(
              new MethodHandlers<
                gen.grpc.CurrencyList,
                gen.grpc.CurrencyValue>(
                  this, METHODID_GET_CURRENCIES_STREAM)))
          .addMethod(
            METHOD_GET_CURRENCY_STATES,
            asyncUnaryCall(
              new MethodHandlers<
                gen.grpc.CurrencyList,
                gen.grpc.CurrencyValueList>(
                  this, METHODID_GET_CURRENCY_STATES)))
          .build();
    }
  }

  /**
   */
  public static final class StreamCurrencyStub extends io.grpc.stub.AbstractStub<StreamCurrencyStub> {
    private StreamCurrencyStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamCurrencyStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamCurrencyStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamCurrencyStub(channel, callOptions);
    }

    /**
     */
    public void getCurrenciesStream(gen.grpc.CurrencyList request,
        io.grpc.stub.StreamObserver<gen.grpc.CurrencyValue> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_CURRENCIES_STREAM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCurrencyStates(gen.grpc.CurrencyList request,
        io.grpc.stub.StreamObserver<gen.grpc.CurrencyValueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_STATES, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StreamCurrencyBlockingStub extends io.grpc.stub.AbstractStub<StreamCurrencyBlockingStub> {
    private StreamCurrencyBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamCurrencyBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamCurrencyBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamCurrencyBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<gen.grpc.CurrencyValue> getCurrenciesStream(
        gen.grpc.CurrencyList request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_CURRENCIES_STREAM, getCallOptions(), request);
    }

    /**
     */
    public gen.grpc.CurrencyValueList getCurrencyStates(gen.grpc.CurrencyList request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CURRENCY_STATES, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamCurrencyFutureStub extends io.grpc.stub.AbstractStub<StreamCurrencyFutureStub> {
    private StreamCurrencyFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamCurrencyFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamCurrencyFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamCurrencyFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gen.grpc.CurrencyValueList> getCurrencyStates(
        gen.grpc.CurrencyList request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_STATES, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CURRENCIES_STREAM = 0;
  private static final int METHODID_GET_CURRENCY_STATES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamCurrencyImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamCurrencyImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CURRENCIES_STREAM:
          serviceImpl.getCurrenciesStream((gen.grpc.CurrencyList) request,
              (io.grpc.stub.StreamObserver<gen.grpc.CurrencyValue>) responseObserver);
          break;
        case METHODID_GET_CURRENCY_STATES:
          serviceImpl.getCurrencyStates((gen.grpc.CurrencyList) request,
              (io.grpc.stub.StreamObserver<gen.grpc.CurrencyValueList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class StreamCurrencyDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gen.grpc.CurrencyProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StreamCurrencyGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamCurrencyDescriptorSupplier())
              .addMethod(METHOD_GET_CURRENCIES_STREAM)
              .addMethod(METHOD_GET_CURRENCY_STATES)
              .build();
        }
      }
    }
    return result;
  }
}
