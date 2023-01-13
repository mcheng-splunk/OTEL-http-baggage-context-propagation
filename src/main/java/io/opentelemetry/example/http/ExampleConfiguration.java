
/**
 * All SDK management takes place here, away from the instrumentation code, which should only access
 * the OpenTelemetry APIs.
 */
class ExampleConfiguration {

  /**
   * Initializes the OpenTelemetry SDK with a logging span exporter and the W3C Trace Context
   * propagator.
   *
   * @return A ready-to-use {@link OpenTelemetry} instance.
   */
  static OpenTelemetry initOpenTelemetry() {
    SdkTracerProvider sdkTracerProvider =
        SdkTracerProvider.builder()
//            .addSpanProcessor(SimpleSpanProcessor.create(new LoggingSpanExporter()))
//            .build();
            .addSpanProcessor(new DemoSpanProcessor())
            .addSpanProcessor(SimpleSpanProcessor.create(new LoggingSpanExporter()))
            .build();

    OpenTelemetrySdk sdk =
        OpenTelemetrySdk.builder()
            .setTracerProvider(sdkTracerProvider)
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .setPropagators(ContextPropagators.create(W3CBaggagePropagator.getInstance()))
            .build();

    Runtime.getRuntime().addShutdownHook(new Thread(sdkTracerProvider::close));
    return sdk;
  }
}

