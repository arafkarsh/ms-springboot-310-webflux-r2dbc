package io.fusion.air.microservice.adapters.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Configuration
public class CartItemRouter {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    @Value("${service.api.path:PATH}")
    private String path;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/cart/customer/{customerId}",
                    beanClass = CartItemHandler.class,
                    beanMethod = "getCart",
                    operation = @Operation(
                            summary = "Get Cart by Customer ID",
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
        log.info("Inside Router .... Getting all the Route Mappings for Cart <<<< <<< <<< ");
        return RouterFunctions
                .route(GET(path+"/cart/customer/{customerId}"), cartItemHandler::getCart);
    }
}
