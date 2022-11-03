package io.craigmiller160.springwebflux.controller;

import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {
    @GetMapping("/flux")
    public Flux<Integer> returnFlux() {
        return Flux.just(1,2,3,4,5)
                .log();
    }

    @GetMapping("/flux-delay")
    public Flux<Integer> returnFluxDelay() {
        return Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofMillis(300))
                .log();
    }

    @GetMapping(value = "/flux-delay-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> returnFluxDelayStream() {
        return Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofMillis(300))
                .log();
    }

    @GetMapping("/bad-blocking-flux")
    @SneakyThrows
    public Flux<Integer> badBlockingFlux() {
        Thread.sleep(1000);
        return Flux.just(1,2,3,4,5)
                .log();
    }
}
