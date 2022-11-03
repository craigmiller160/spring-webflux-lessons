package io.craigmiller160.springwebflux.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.hamcrest.Matchers.contains;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FluxAndMonoControllerTest {
    private final WebTestClient webTestClient;

    @Test
    @SneakyThrows
    public void test_returnFlux_testAsFlux() {
        final Flux<Integer> response = webTestClient.get()
                .uri("/flux")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    public void test_returnFlux_testAsJson() {
        webTestClient.get()
                .uri("/flux")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[1,2,3,4,5]");
    }
}
