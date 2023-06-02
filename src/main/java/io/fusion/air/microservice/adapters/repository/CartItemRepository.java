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
package io.fusion.air.microservice.adapters.repository;

import io.fusion.air.microservice.domain.entities.example.CartItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;


/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
@Repository
public interface CartItemRepository extends ReactiveCrudRepository<CartItemEntity, UUID> {

    /**
     * Returns All the Items for the Customer ID
     * @param customerId
     * @return
     */
    public Flux<CartItemEntity> findByCustomerId(UUID customerId);

    /**
     * Returns all the Items for the Customer ID and Product ID
     * @param customerId
     * @param productId
     * @return
     */
    public Flux<CartItemEntity> findByCustomerIdAndProductId(UUID customerId, UUID productId);

}
