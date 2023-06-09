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
package io.fusion.air.microservice.domain.ports.services;

import io.fusion.air.microservice.domain.entities.example.CartItemEntity;
import io.fusion.air.microservice.domain.models.example.CartItem;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public interface CartReactiveService {

    /**
     * Get All Cart Items by Customer ID
     * @param customerId
     * @return
     */
    public Flux<CartItemEntity> findByCustomerId(String customerId);

    /**
     * Save Cart Item
     * @param cartItem
     */
    public void saveCartItem(CartItem cartItem);

    /**
     * Delete Cart Item
     * @param itemId
     */
    public void deleteCartItem(UUID itemId);
}
