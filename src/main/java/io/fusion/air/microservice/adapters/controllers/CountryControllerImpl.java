/**
 * (C) Copyright 2021 Araf Karsh Hamid
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
package io.fusion.air.microservice.adapters.controllers;

import io.fusion.air.microservice.domain.entities.example.CountryEntity;
import io.fusion.air.microservice.domain.exceptions.BusinessServiceException;
import io.fusion.air.microservice.domain.exceptions.DataNotFoundException;
import io.fusion.air.microservice.domain.exceptions.InputDataException;
import io.fusion.air.microservice.domain.exceptions.UnableToSaveException;
import io.fusion.air.microservice.domain.models.core.StandardResponse;
import io.fusion.air.microservice.domain.models.example.Country;
import io.fusion.air.microservice.domain.ports.services.CountryReactiveService;
import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.controllers.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Product Controller for the Service
 *
 * Only Selected Methods will be secured in this packaged - which are Annotated with
 * @AuthorizationRequired
 * @Operation(summary = "Cancel Product", security = { @SecurityRequirement(name = "bearer-key") })
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@RestController
// "/ms-cache/api/v1"
@RequestMapping("${service.api.path}/country")
@Tag(name = "Country API", description = "Ex. io.f.a.m.adapters.controllers.CountryControllerImpl")
public class CountryControllerImpl extends AbstractController {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
	@Autowired
	private ServiceConfiguration serviceConfig;
	private String serviceName;

	@Autowired
	private CountryReactiveService countryReactiveService;

	/**
	 * GET Method Call to Get the Country By Country Code
	 * 
	 * @return
	 */
    @Operation(summary = "Get the Country By Country Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Country Retrieved!",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
            description = "Invalid Country Code.",
            content = @Content)
    })
	@GetMapping("/code/{countryCode}")
	public Mono<StandardResponse> getCountryByCode(@PathVariable("countryCode") String _countryCode)throws Exception {
		log.info("|"+name()+"|Request to Get Country By Code for > "+_countryCode);
		return countryReactiveService.findByCountryCode(_countryCode)
				.log("countryReactiveService.findByCountryCode(code)")
				.flatMap(data -> {
					StandardResponse stdResponse = createSuccessResponse("Data Fetch Success!");
					stdResponse.setPayload(data);
					return Mono.just(stdResponse);
				})
				.switchIfEmpty(Mono.error(new BusinessServiceException("Data not found for > "+_countryCode)));
	}

	/**
	 * GET Method Call to Get the Country By Country ID
	 *
	 * @return
	 */
	@Operation(summary = "Get the Country By Country ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Country Retrieved!",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "400",
					description = "Invalid Country ID.",
					content = @Content)
	})
	@GetMapping("/id/{countryId}")
	public Mono<StandardResponse> getCountryById(@PathVariable("countryId") String _countryId)throws Exception {
		log.info("|"+name()+"|Request to Get Country By ID for > "+_countryId);
		return countryReactiveService.findByCountryId(_countryId)
				.log("countryReactiveService.findByCountryId(id)")
				.flatMap(data -> {
					StandardResponse stdResponse = createSuccessResponse("Data Fetch Success!");
					stdResponse.setPayload(data);
					return Mono.just(stdResponse);
				});
	}

	/**
	 * GET Method Call to Get All the Countries
	 *
	 * @return
	 */
	@Operation(summary = "Get All the Countries")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "List All the Countries",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "400",
					description = "No Country Data available!",
					content = @Content)
	})
	@GetMapping(path = "/all/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CountryEntity> getAllCountries() throws Exception {
		log.info("|"+name()+"|Request to get All Countries ... ");
		return countryReactiveService.findAll()
				.log("countryReactiveService.findAll()")
				.switchIfEmpty(Mono.error(new DataNotFoundException("No countries found!")));

	}

	/**
	 * GET Method Call to Get All the Countries with a Delay in Every Record
	 *
	 * @return
	 */
	@Operation(summary = "Get All the Countries with a Delay in every Record")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "List All the Countries",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "400",
					description = "No Country Data available!",
					content = @Content)
	})
	@GetMapping(path = "/all/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CountryEntity> getAllCountriesWithDelay() throws Exception {
		log.info("|"+name()+"|Request to get All Countries ... ");
		return countryReactiveService.findAll()
				.log("countryReactiveService.findAll()")
				.delayElements(Duration.ofSeconds(3))
				.switchIfEmpty(Mono.error(new DataNotFoundException("No countries found!")));

	}

	/**
	 * POST Method Call to Save the Country
	 *
	 * @return
	 */
	@Operation(summary = "Save the Country")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Save the Country",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "400",
					description = "Unable to save the Country!",
					content = @Content)
	})
	@PostMapping(path = "/")
	public Mono<StandardResponse> saveCountry(@Valid @RequestBody Country country) throws Exception {
		log.info("|"+name()+"|Request to Save the Country ... ");
		if(country != null) {
			return countryReactiveService.save(country)
					.log("countryReactiveService.save(country)")
					.flatMap(data -> {
						StandardResponse stdResponse = createSuccessResponse("Data Save Success!");
						stdResponse.setPayload(data);
						return Mono.just(stdResponse);
					})
					.switchIfEmpty(Mono.error(new UnableToSaveException("Unable to save data! ")));
		}
		throw new InputDataException("Invalid Input Data!");
	}

 }