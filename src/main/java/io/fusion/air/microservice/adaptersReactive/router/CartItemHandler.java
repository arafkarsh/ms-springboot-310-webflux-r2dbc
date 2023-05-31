package io.fusion.air.microservice.adaptersReactive.router;

import io.fusion.air.microservice.domain.ports.services.CartReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
public class CartItemHandler {

    @Autowired
    private CartReactiveService cartReactiveService;


    public Flux<ServerResponse> getCart(ServerRequest request) {
        UUID cartId = UUID.fromString(request.pathVariable("customerId"));
        return cartReactiveService.findByCustomerId(cartId)
                .flatMap(cart -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(cart)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
