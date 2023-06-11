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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
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

        String reqId = UUID.randomUUID().toString();
        String ip = request.getRemoteAddress().getHostString();
        String port = String.valueOf(request.getRemoteAddress().getPort());
        String uri = request.getURI().toString();
        String protocol = request.getMethod().toString();
        String user = "john.doe";
        String name = serviceConfig.getServiceName();

        Map<String, Object> params = new HashMap<>();
        params.put("ReqId", reqId);
        params.put("IP", ip);
        params.put("Port", port);
        params.put("URI", uri);
        params.put("Protocol", protocol);
        params.put("user", user);
        params.put("Service", name);

        return chain.filter(exchange)
                // .subscriberContext(Context.of(params))
                .doOnEach(signal -> {
                    // Here you can do something with the context before the request is finished
                    // Context context = signal.getContext();
                    // Log the data from the context
                })
                .doFinally(signalType -> {
                    // Clear the context (if necessary)
                });
    }

}
