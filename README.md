# mn-cassandra-health-jackson

If your health endpoint is configured like the below, and your jackson serialization-inclusion configuration is set to include `NON_NULL` (or `ALWAYS`)  
the health endpoint fails due to no serializer being available for the `Endpoint` used in the `nodeMap.put("endpoint", node.getEndPoint());` call

```yaml
endpoints:
  health:
    details-visible: anonymous
    sensitive: false

jackson:
   serialization-inclusion: non_null
#   serialization-inclusion: always
```

1. Start Cassandra with `docker-compose up`
2. Wait a minute or two for it to initialize
3. Start this application
4. Make a GET call to `/health`
5. Observe that a 500 is thrown with the following error
    1. If you remove `jackson.serialization-inclusion` from the config and try again, things work as expected

```text
io.micronaut.http.codec.CodecException: Error encoding object [io.micronaut.management.health.indicator.DefaultHealthResult@bab6b32] to JSON: No serializer found for class com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: io.micronaut.management.health.indicator.DefaultHealthResult["details"]->java.util.HashMap["cassandra"]->io.micronaut.management.health.indicator.DefaultHealthResult["details"]->java.util.LinkedHashMap["nodes (10 max.)"]->java.util.HashMap["0382ff6b-f73c-4fe5-955e-586caedb0d85"]->java.util.HashMap["endpoint"])
	at io.micronaut.json.codec.MapperMediaTypeCodec.encode(MapperMediaTypeCodec.java:221)
	at io.micronaut.json.codec.MapperMediaTypeCodec.encode(MapperMediaTypeCodec.java:270)
	at io.micronaut.http.server.netty.RoutingInBoundHandler.encodeBodyAsByteBuf(RoutingInBoundHandler.java:1437)
	at io.micronaut.http.server.netty.RoutingInBoundHandler.encodeBodyWithCodec(RoutingInBoundHandler.java:1377)
	at io.micronaut.http.server.netty.RoutingInBoundHandler.encodeResponseBody(RoutingInBoundHandler.java:1189)
	at io.micronaut.http.server.netty.RoutingInBoundHandler.encodeHttpResponse(RoutingInBoundHandler.java:1032)
	at io.micronaut.http.server.netty.RoutingInBoundHandler.access$000(RoutingInBoundHandler.java:150)
	at io.micronaut.http.server.netty.RoutingInBoundHandler$2.doOnNext(RoutingInBoundHandler.java:609)
	at io.micronaut.http.server.netty.RoutingInBoundHandler$2.doOnNext(RoutingInBoundHandler.java:601)
	at io.micronaut.core.async.subscriber.CompletionAwareSubscriber.onNext(CompletionAwareSubscriber.java:56)
	at reactor.core.publisher.StrictSubscriber.onNext(StrictSubscriber.java:89)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onNext(FluxContextWrite.java:107)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onNext(FluxOnErrorResume.java:79)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:712)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drain(FluxFlatMap.java:588)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onSubscribe(FluxFlatMap.java:955)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onSubscribe(ReactorSubscriber.java:50)
	at reactor.core.publisher.FluxJust.subscribe(FluxJust.java:68)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8660)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:426)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at io.micronaut.core.async.publisher.Publishers$1.doOnNext(Publishers.java:248)
	at io.micronaut.core.async.subscriber.CompletionAwareSubscriber.onNext(CompletionAwareSubscriber.java:56)
	at reactor.core.publisher.StrictSubscriber.onNext(StrictSubscriber.java:89)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onNext(FluxOnErrorResume.java:79)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:712)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drain(FluxFlatMap.java:588)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onSubscribe(FluxFlatMap.java:955)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onSubscribe(ReactorSubscriber.java:50)
	at reactor.core.publisher.FluxJust.subscribe(FluxJust.java:68)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8660)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:426)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onNext(FluxOnErrorResume.java:79)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:712)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drain(FluxFlatMap.java:588)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onSubscribe(FluxFlatMap.java:955)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onSubscribe(ReactorSubscriber.java:50)
	at reactor.core.publisher.FluxJust.subscribe(FluxJust.java:68)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8660)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:426)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onNext(FluxOnErrorResume.java:79)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:712)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drain(FluxFlatMap.java:588)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onSubscribe(FluxFlatMap.java:955)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onSubscribe(ReactorSubscriber.java:50)
	at reactor.core.publisher.FluxJust.subscribe(FluxJust.java:68)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8660)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:426)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.tryEmit(FluxFlatMap.java:543)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onNext(FluxFlatMap.java:984)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:200)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:74)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:712)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drain(FluxFlatMap.java:588)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onSubscribe(FluxFlatMap.java:955)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onSubscribe(ReactorSubscriber.java:50)
	at reactor.core.publisher.FluxJust.subscribe(FluxJust.java:68)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8660)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:426)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.MonoNext$NextSubscriber.onNext(MonoNext.java:82)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:129)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onNext(ReactorSubscriber.java:57)
	at reactor.core.publisher.FluxHide$SuppressFuseableSubscriber.onNext(FluxHide.java:137)
	at reactor.core.publisher.Operators$BaseFluxToMonoOperator.completePossiblyEmpty(Operators.java:2034)
	at reactor.core.publisher.MonoCollectList$MonoCollectListSubscriber.onComplete(MonoCollectList.java:118)
	at io.micronaut.reactive.reactor.instrument.ReactorSubscriber.onComplete(ReactorSubscriber.java:71)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.checkTerminated(FluxFlatMap.java:846)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.drainLoop(FluxFlatMap.java:608)
	at reactor.core.publisher.FluxFlatMap$FlatMapMain.innerComplete(FluxFlatMap.java:894)
	at reactor.core.publisher.FluxFlatMap$FlatMapInner.onComplete(FluxFlatMap.java:997)
	at io.micronaut.core.async.publisher.AsyncSingleResultPublisher$ExecutorServiceSubscription.lambda$request$1(AsyncSingleResultPublisher.java:104)
	at io.micronaut.scheduling.instrument.InvocationInstrumenterWrappedRunnable.run(InvocationInstrumenterWrappedRunnable.java:47)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: io.micronaut.management.health.indicator.DefaultHealthResult["details"]->java.util.HashMap["cassandra"]->io.micronaut.management.health.indicator.DefaultHealthResult["details"]->java.util.LinkedHashMap["nodes (10 max.)"]->java.util.HashMap["0382ff6b-f73c-4fe5-955e-586caedb0d85"]->java.util.HashMap["endpoint"])
	at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
	at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
	at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:408)
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.failForEmpty(UnknownSerializer.java:53)
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.serialize(UnknownSerializer.java:30)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(MapSerializer.java:869)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeWithoutTypeInfo(MapSerializer.java:760)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:720)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:35)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(MapSerializer.java:869)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeWithoutTypeInfo(MapSerializer.java:760)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:720)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:35)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(MapSerializer.java:869)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeWithoutTypeInfo(MapSerializer.java:760)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:720)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:35)
	at io.micronaut.jackson.modules.BeanIntrospectionModule$BeanIntrospectionPropertyWriter.serializeAsField(BeanIntrospectionModule.java:948)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(MapSerializer.java:869)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeWithoutTypeInfo(MapSerializer.java:760)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:720)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:35)
	at io.micronaut.jackson.modules.BeanIntrospectionModule$BeanIntrospectionPropertyWriter.serializeAsField(BeanIntrospectionModule.java:948)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
	at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
	at com.fasterxml.jackson.databind.ObjectMapper.writeValue(ObjectMapper.java:3828)
	at io.micronaut.jackson.databind.JacksonDatabindMapper.writeValue(JacksonDatabindMapper.java:124)
	at io.micronaut.jackson.databind.JacksonDatabindMapper.writeValue(JacksonDatabindMapper.java:129)
	at io.micronaut.json.codec.MapperMediaTypeCodec.encode(MapperMediaTypeCodec.java:219)
	... 91 common frames omitted

```

There are also 2 test that prove out this failure.
