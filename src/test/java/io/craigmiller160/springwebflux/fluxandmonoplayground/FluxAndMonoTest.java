package io.craigmiller160.springwebflux.fluxandmonoplayground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FluxAndMonoTest {
    @Test
    public void fluxTest() {
        Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("FooBar")))
                .concatWith(Flux.just("After Error"))
                .log()
                .subscribe(System.out::println, Throwable::printStackTrace);
    }
}
