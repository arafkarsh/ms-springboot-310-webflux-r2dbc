/**
 * (C) Copyright 2023 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.adapters.router;

import io.fusion.air.microservice.domain.exceptions.DataNotFoundException;
import io.fusion.air.microservice.domain.models.core.StandardResponse;
import io.fusion.air.microservice.domain.ports.services.CartReactiveService;
import io.fusion.air.microservice.server.router.AbstractHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class CartItemHandler extends AbstractHandler {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    @Autowired
    private CartReactiveService cartReactiveService;

    public Mono<ServerResponse> getCart(ServerRequest request) {
        String customerId = request.pathVariable("customerId");
        return cartReactiveService
            .findByCustomerId(customerId)
            .log("Finding Cart for Customer ID = "+customerId)   // Find the Cart by Customer ID
            .collectList()
            .log("Converted Flux<Cart> to Mono<List> 1")         // convert the Flux<Cart> to Mono<List<Cart>>
            .flatMap(carts -> {
                if (carts.isEmpty()) {
                    return Mono.error(new DataNotFoundException("Cart not found for customer ID: " + customerId));
                }
                // Create a StandardResponse object
                StandardResponse stdResponse = createSuccessResponse("Data Retrieved!");
                stdResponse.setPayload(carts);                          // Set the payload with carts

                return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromValue(stdResponse))
                    .log("Pass 3: Found Data!");               // Set the body with StandardResponse
            })
            .onErrorResume(DataNotFoundException.class, Mono::error)    // Propagate DNF Exception
            .onErrorResume(Mono::error);                                // Propagate UNKNOWN Exception
    }
}
