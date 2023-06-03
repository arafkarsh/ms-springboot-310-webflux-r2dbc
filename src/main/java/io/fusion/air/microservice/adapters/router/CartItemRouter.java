package io.fusion.air.microservice.adapters.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Configuration
public class CartItemRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/cart/customer/{customerId}",
                    beanClass = CartItemHandler.class,
                    beanMethod = "getUser",
                    operation = @Operation(
                            summary = "Get user by id",
                            security = { @SecurityRequirement(name = "bearer-key") },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Cart retrieved",
                                            content = {@Content(mediaType = "application/json")}
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Unable to retrieve the Customer Cart!",
                                            content = @Content
                                    )
                            }
                    )
            )
            //... similarly for other routes
    })
    public RouterFunction<ServerResponse> route(CartItemHandler cartItemHandler) {
        System.out.println("Inside Router .... !<><><><><><<<<<< <<<< <<< <<< ");
        return RouterFunctions
                .route(GET("/ms-webflux/api/v1/cart/customer/{customerId}"), cartItemHandler::getCart);
    }
}
