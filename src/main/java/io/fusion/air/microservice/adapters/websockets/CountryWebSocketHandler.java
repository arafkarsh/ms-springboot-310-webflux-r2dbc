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
package io.fusion.air.microservice.adapters.websockets;

import io.fusion.air.microservice.domain.exceptions.DataNotFoundException;
import io.fusion.air.microservice.domain.ports.services.CountryReactiveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import io.fusion.air.microservice.domain.entities.example.CountryEntity;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
public class CountryWebSocketHandler implements WebSocketHandler {

    private final CountryReactiveService countryReactiveService;

    public CountryWebSocketHandler(CountryReactiveService countryReactiveService) {
        this.countryReactiveService = countryReactiveService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        Flux<String> countryFlux = countryReactiveService.findAll()
                .log("CountryWebSocketHandler.handle()")
                .onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST)
                .delayElements(Duration.ofSeconds(3))
                .switchIfEmpty(Mono.error(new DataNotFoundException("No countries found!")))
                .map(CountryEntity::toString);

        return session.send(countryFlux.map(session::textMessage));
    }
}
