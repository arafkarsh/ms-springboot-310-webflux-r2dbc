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
import io.fusion.air.microservice.domain.exceptions.DataNotFoundException;
import io.fusion.air.microservice.domain.exceptions.DatabaseException;
import io.fusion.air.microservice.domain.models.example.Country;
import io.fusion.air.microservice.domain.ports.services.CountryReactiveService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Service
public class CountryReactiveServiceImpl implements CountryReactiveService {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Save the Country
     *
     * @param country
     */
    @Override
    public Mono<CountryEntity> save(Country country) {
        return countryRepository.save(new CountryEntity(country));
    }

    /**
     * Save the Country
     *
     * @param country
     */
    @Override
    public  Mono<CountryEntity> save(CountryEntity country) {
           return countryRepository.save(country);
    }

    @Override
    public Flux<CountryEntity> findAll() {
        return countryRepository.findAll()
                .switchIfEmpty(Mono.error(new DataNotFoundException("FAILED to fetch data!")));
    }

    /**
     * Returns the Country By Country Code
     *
     * @param code
     * @return
     */
    @Override
    public Mono<CountryEntity> findByCountryCode(String code) {
        return countryRepository.findByCountryCode(code)
                // If the Query is Empty Mono will have no data and will NOT throw an Exception
                // Following onErrorResume will never be called if the Query is Empty
                .onErrorResume(e -> {
                    log.error("Database ERROR:", e);
                    return Mono.error(new DataNotFoundException("FAILED to fetch data! "+e.getMessage(), e));
                });
    }

    /**
     * Returns the Country by Country ID
     *
     * @param countryId
     * @return
     */
    @Override
    public Mono<CountryEntity> findByCountryId(String countryId) {
        return countryRepository.findByCountryId(countryId)
                .switchIfEmpty(Mono.error(new DataNotFoundException("FAILED to fetch data > "+countryId)));
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
