package io.craigmiller160.springwebflux.fluxandmonoplayground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FluxAndMonoTest {
    @Test
    public void fluxTest() {
        Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("FooBar")))
                .concatWith(Flux.just("After Error"))
                .log()
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithoutError() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("FooBar")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyErrorMatches(ex -> ex instanceof RuntimeException && ex.getMessage().equals("FooBar"));
    }

    @Test
    public void fluxTestElementsCount() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
}
