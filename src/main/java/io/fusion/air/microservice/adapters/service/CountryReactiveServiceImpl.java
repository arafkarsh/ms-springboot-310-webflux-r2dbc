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
package io.fusion.air.microservice.adapters.service;

import io.fusion.air.microservice.adapters.repository.CountryRepository;
import io.fusion.air.microservice.domain.entities.example.CountryEntity;
import io.fusion.air.microservice.domain.ports.services.CountryReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Service
public class CountryReactiveServiceImpl implements CountryReactiveService {

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Save the Country
     *
     * @param country
     */
    @Override
    public void save(CountryEntity country) {
            countryRepository.save(country);
    }

    @Override
    public Flux<CountryEntity> findAll() {
        return countryRepository.findAll();
    }

    /**
     * Returns the Country By Country Code
     *
     * @param code
     * @return
     */
    @Override
    public Mono<CountryEntity> findByCountryCode(String code) {
        return countryRepository.findByCountryCode(code);
    }

    /**
     * Returns the Country by Country ID
     *
     * @param countryId
     * @return
     */
    @Override
    public Mono<CountryEntity> findByCountryId(String countryId) {
        return countryRepository.findByCountryId(countryId);
    }

    /**
     * Delete the Country
     *
     * @param country
     */
    @Override
    public void delete(CountryEntity country) {
        countryRepository.delete(country);
    }
}
