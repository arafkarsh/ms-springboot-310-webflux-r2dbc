package io.fusion.air.microservice.adapters.router;

import io.fusion.air.microservice.domain.ports.services.CartReactiveService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;


import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
public class CartItemHandler {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    @Autowired
    private CartReactiveService cartReactiveService;

    public Mono<ServerResponse> getCart(ServerRequest request) {
        log.info("Inside Handler - getCart() ");
        String customerId = request.pathVariable("customerId");
        // UUID cartId = UUID.fromString(request.pathVariable("cardId"));
        return cartReactiveService
                .findByCustomerId(customerId)                               // Find the Cart by Customer ID
                .collectList()                                             // convert the Flux<Cart> to Mono<List<Cart>>
                .flatMap(carts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(carts)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
