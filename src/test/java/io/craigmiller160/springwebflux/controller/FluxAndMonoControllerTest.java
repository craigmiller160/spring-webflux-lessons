package io.craigmiller160.springwebflux.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "36000")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FluxAndMonoControllerTest {
    private final WebTestClient webTestClient;

    @Test
    @SneakyThrows
    public void test_returnFlux() {
        final Flux<Integer> response = callEndpoint("/flux")
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

        callEndpoint("/flux")
                .expectBody().json("[1,2,3,4,5]");
    }

    private WebTestClient.ResponseSpec callEndpoint(final String uri) {
        return webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_returnFluxDelay() {
        final Flux<Integer> response = callEndpoint("/flux-delay")
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

        callEndpoint("/flux-delay")
                .expectBody().json("[1,2,3,4,5]");
    }

    @Test
    public void test_returnFluxDelayStream() {
        final Flux<Integer> response = callEndpoint("/flux-delay-stream")
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

        final byte[] responseBytes = callEndpoint("/flux-delay-stream")
                .expectBody()
                .returnResult()
                .getResponseBody();
        assertNotNull(responseBytes);
        final String expected = "1\n2\n3\n4\n5\n";
        assertEquals(expected, new String(responseBytes));
    }


}
