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

import io.fusion.air.microservice.domain.entities.example.CountryEntity;
import io.fusion.air.microservice.domain.models.example.Country;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public interface CountryReactiveService {


    /**
     * Save the Country
     * @param country
     */
    public  Mono<CountryEntity> save(Country country);

    /**
     * Save the Country
     * @param country
     */
    public  Mono<CountryEntity> save(CountryEntity country);

    public Flux<CountryEntity> findAll();

    /**
     * Returns the Country By Country Code
     * @param code
     * @return
     */
    public Mono<CountryEntity> findByCountryCode(String code);

    /**
     * Returns the Country by Country ID
     * @param countryId
     * @return
     */
    public Mono<CountryEntity> findByCountryId(String countryId);

    /**
     * Delete the Country
     * @param country
     */
    public void delete(CountryEntity country);

}
