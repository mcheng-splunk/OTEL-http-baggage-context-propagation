# HTTP Example

**Note:** This is an advanced scenario useful for people that want to *manually* instrument their own code. 

This is a simple example that demonstrates how to use the OpenTelemetry SDK 
to *manually* instrument a simple HTTP based Client/Server application. 
The example creates the **Root Span** on the client and sends the context
over the HTTP request. On the server side, the example shows how to extract the context
and create a **Child Span** with attached a **Span Event**. 

Based code is [here](https://github.com/open-telemetry/opentelemetry-java-docs/tree/main/http)

The example has been extended to propagate **Baggage**.

# How to run

## Prerequisites
* Java 1.8.231
* Be on the project root folder

## 1 - Compile 
```shell script
../gradlew shadowJar
```

## 2 - Start the Server
```shell script
java -cp ./build/libs/opentelemetry-examples-http-0.1.0-SNAPSHOT-all.jar io.opentelemetry.example.http.HttpServer
```
 
## 3 - Start the Client
```shell script
java -cp ./build/libs/opentelemetry-examples-http-0.1.0-SNAPSHOT-all.jar io.opentelemetry.example.http.HttpClient
```

---

# Sample Output

## Server Logs

```json
baggageEntry size: 0
Served Client: /127.0.0.1:61545
eventAttributes: {answer="Hello World!"}
May 29, 2023 2:57:35 PM io.opentelemetry.exporter.logging.LoggingSpanExporter export
INFO: 'GET /' : 18ea498f1bb5ffcd98a17142fb0aedba 1717275845322714 SERVER [tracer: io.opentelemetry.example.http.HttpServer:] AttributesMap{data={http.target=/, custom=demo123, component=http, http.method=GET, http.scheme=http, http.host=localhost:8080}, capacity=128, totalAddedValues=6}
carrier.getRequestHeaders().get(key).get(0) :00-8ce6e7877cb48e91b6b8376926a129f1-e746c57f16a4caa1-01
carrier.getRequestHeaders().keySet() :[Accept, Connection, Host, User-agent, Traceparent]
carrier.getRequestHeaders().values() :[[*/*], [keep-alive], [127.0.0.1:8080], [Java/19.0.1], [00-8ce6e7877cb48e91b6b8376926a129f1-e746c57f16a4caa1-01]]
server context is {opentelemetry-trace-span-key=PropagatedSpan{ImmutableSpanContext{traceId=8ce6e7877cb48e91b6b8376926a129f1, spanId=e746c57f16a4caa1, traceFlags=01, traceState=ArrayBasedTraceState{entries=[]}, remote=true, valid=true}}}

```

## Client Logs

```json
Response Code: 0
Response Msg:
baggageScope is: io.opentelemetry.context.ThreadLocalContextStorage$ScopeImpl@7eccc1ae
baggageEntry size: 1
client Context after inject is: {opentelemetry-baggage-key={user.name=ImmutableEntry{value=jack, metadata=ImmutableEntryMetadata{value=}}}, opentelemetry-trace-span-key=SdkSpan{traceId=2cc0075c8edf91c6809c3e6faef288f8, spanId=390bfdd7ca310837, parentSpanContext=ImmutableSpanContext{traceId=00000000000000000000000000000000, spanId=0000000000000000, traceFlags=00, traceState=ArrayBasedTraceState{entries=[]}, remote=false, valid=false}, name=/, kind=CLIENT, attributes=AttributesMap{data={custom=demo123, component=http, http.method=GET, user.name=jack, http.url=http://127.0.0.1:8080}, capacity=128, totalAddedValues=5}, status=ImmutableStatusData{statusCode=UNSET, description=}, totalRecordedEvents=0, totalRecordedLinks=0, startEpochNanos=1685343490735000000, endEpochNanos=0}}
May 29, 2023 2:58:10 PM io.opentelemetry.exporter.logging.LoggingSpanExporter export
INFO: '/' : 2cc0075c8edf91c6809c3e6faef288f8 390bfdd7ca310837 CLIENT [tracer: io.opentelemetry.example.http.HttpClient:] AttributesMap{data={custom=demo123, component=http, http.method=GET, user.name=jack, http.url=http://127.0.0.1:8080}, capacity=128, totalAddedValues=5}
```