package io.fusion.air.microservice.adapters.router;

import io.fusion.air.microservice.domain.ports.services.CartReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

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

    public Mono<ServerResponse> getCart(ServerRequest request) {
        System.out.println("Inside Handler - getCart()  .... !<><><><><><<<<<< <<<< <<< <<< ");
        String customerId = request.pathVariable("customerId");
        // UUID cartId = UUID.fromString(request.pathVariable("cardId"));
        return cartReactiveService.findByCustomerId(customerId)
                .collectList() // convert the Flux<Cart> to Mono<List<Cart>>
                .flatMap(carts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(carts)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
