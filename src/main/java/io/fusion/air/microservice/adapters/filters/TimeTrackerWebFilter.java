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
package io.fusion.air.microservice.adapters.filters;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */

@Component
public class TimeTrackerWebFilter implements WebFilter {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Log Time
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    long endTime = System.currentTimeMillis();
                    // log.info("Path: {}, Time Elapsed: {} ms", exchange.getRequest().getPath(), endTime - startTime);
                    String details = exchange.getRequest().getMethod().toString() + ":" + exchange.getRequest().getPath().toString();
                    logTime("WSD",  startTime,  "Completed", details);
                });
    }

    /**
     * Log Time
     * @param _method
     * @param _startTime
     * @param _status
     */
    private void logTime(String _method, long _startTime, String _status, String _details) {
        long timeTaken=System.currentTimeMillis() - _startTime;
        log.info("4|{}|TIME={} ms|{}|CLASS={}|",_method, timeTaken, _status, _details);
    }
}
