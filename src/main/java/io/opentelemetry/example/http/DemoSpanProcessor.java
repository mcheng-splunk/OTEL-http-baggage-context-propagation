package io.opentelemetry.example.http;

import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.ReadableSpan;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.api.baggage.Baggage;
/**
 * See <a
 * href="https://github.com/open-telemetry/opentelemetry-specification/blob/master/specification/trace/sdk.md#span-processor">
 * OpenTelemetry Specification</a> for more information about {@link SpanProcessor}.
 *
 * @see DemoAutoConfigurationCustomizerProvider
 */
public class DemoSpanProcessor implements SpanProcessor {
    @Override
    public void onStart(Context parentContext, ReadWriteSpan span) {
        //Baggage.current().asMap().forEach((s, baggageEntry) -> span.setAttribute(s, baggageEntry.getValue()));
        Baggage.fromContext(parentContext).asMap().forEach((s, baggageEntry) -> span.setAttribute(s, baggageEntry.getValue()));
        System.out.println("baggageEntry size: "  +  Baggage.fromContext(parentContext).size());
//        System.out.println("DemoSpanProcess parentContext : " + parentContext);
//        System.out.println("DemoSpanProcessor span: " + span);

        span.setAttribute("custom", "demo123");
    }

    @Override
    public boolean isStartRequired() {
        return true;
    }

    @Override
    public void onEnd(ReadableSpan span) {}

    @Override
    public boolean isEndRequired() {
        return false;
    }

    @Override
    public CompletableResultCode shutdown() {
        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode forceFlush() {
        return CompletableResultCode.ofSuccess();
    }
}
