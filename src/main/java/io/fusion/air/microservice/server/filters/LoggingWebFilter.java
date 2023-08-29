/**
 * (C) Copyright 2022 Araf Karsh Hamid
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
package io.fusion.air.microservice.server.filters;

import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.models.RequestMap;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.view.RequestContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
public class LoggingWebFilter implements WebFilter {
    @Autowired
    private ServiceConfiguration serviceConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        RequestMap requestContext = new RequestMap()
                .addIPAddress(request.getRemoteAddress().getHostString())
                .addPort(String.valueOf(request.getRemoteAddress().getPort()))
                .addURI(request.getURI().toString())
                .addProtocol(request.getMethod().toString())
                .addService(serviceConfig.getServiceName());

        // Set requestContext as an attribute on the ServerWebExchange
        exchange.getAttributes().put("requestContext", requestContext);

        return chain.filter(exchange)
                .contextWrite(Context.of("requestContext", requestContext))
                .doOnEach(signal -> {
                });
    }

}
