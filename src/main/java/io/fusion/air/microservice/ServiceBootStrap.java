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
package io.fusion.air.microservice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.controllers.HealthController;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.slf4j.Logger;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import reactor.tools.agent.ReactorDebugAgent;
// Error: java: package reactor.blockhound does not exist
// import reactor.blockhound.BlockHound;


/**
 * MicroService - Spring Boot WebFlux Reactive Application
 * API URL : http://localhost:9092/service/api/v1/swagger-ui.html
 *
 * @author arafkarsh
 */
@EnableWebFlux
@SpringBootApplication
/**
 * This @SpringBootApplication annotation is a convenience annotation that adds all of the following:
 *
 * @Configuration: Tags the class as a source of bean definitions for the application context.
 * @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans,
 * and various property settings.
 * @ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example
 * package, letting it find the controllers.
 */
public class ServiceBootStrap {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());

	// All CAPS Words will be replaced using data from application.properties
	private final String title = "<h1>Welcome to MICRO service<h1/>"
			+"<h3>Copyright (c) COMPANY, 2022</h3>"
			+"<h5>Build No: BN :: Build Date: BD :: </h5>";

	@Autowired
	private ServiceConfiguration serviceConfig;

	// Get the Service Name from the properties file
	@Value("${service.name:NameNotDefined}")
	private String serviceName = "Unknown";
	
	/**
	 * Start the Microservice
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Start the Server
		start(args);
		// API URL : http://localhost:9090/service/api/v1/swagger-ui.html
	}

	/**
	 // Error: java: package reactor.blockhound does not exist
	static {
		// BlockHound.install();
		BlockHound.builder()
			.allowBlockingCallsInside("java.util.UUID", "randomUUID")
			.install();
	}
	*/

	/**
	 * Start the Server
	 * @param args
	 */
	public static void start(String[] args) {
		log.info("Booting Service ..... ..");
		try {
			log.info("Starting Reactor Debug Agent ..... ..");
			ReactorDebugAgent.init();
			// context = SpringApplication.examples(ServiceBootStrap.class, args);
			SpringApplication.run(ServiceBootStrap.class, args);
			log.info("Booting Service ..... ...Startup completed!");
		} catch (Exception e) {
			log.info("ERROR IN Booting Service ..... ...");
			e.printStackTrace();
		}
	}

	/**
	 * Micro Service - Home Page
	 * @return
	 */
	@GetMapping("/root")
	public String home() {
		log.info("Request to Home Page of Service... ");
		return (serviceConfig == null) ? this.title :
				this.title.replaceAll("MICRO", serviceConfig.getServiceName())
						.replaceAll("COMPANY", serviceConfig.getServiceOrg())
						.replaceAll("BN", "" + serviceConfig.getBuildNumber())
						.replaceAll("BD", serviceConfig.getBuildDate());
	}

	/**
	 * CommandLineRunner Prints all the Beans defined ...
	 * @param ctx
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug(beanName);
			}
		};
	}

	/**
	 * Open API v3 Docs - All
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public GroupedOpenApi allPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/**")
				.build();
	}

	/**
	 * Open API v3 Docs - MicroService
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public GroupedOpenApi appPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-core")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/**")
				.pathsToExclude(serviceConfig.getServiceApiPath()+"/service/**", serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	/**
	 * Open API v3 Docs - Core Service
	 * Reference: https://springdoc.org/faq.html
	 * Change the Resource Mapping in HealthController
	 *
	 * @see HealthController
	 */
	@Bean
	public GroupedOpenApi configPublicApi() {
		// System.out.println;
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-config")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	@Bean
	public GroupedOpenApi systemPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-health")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/service/**")
				.build();
	}

	/**
	 * Open API v3
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public OpenAPI buildOpenAPI() {
		return new OpenAPI()
				.servers(getServers())
				.info(new Info()
						.title(serviceConfig.getServiceName()+" Service")
						.description(serviceConfig.getServiceDetails())
						.version(serviceConfig.getServerVersion())
						.license(new License().name("License: "+serviceConfig.getServiceLicense())
								.url(serviceConfig.getServiceUrl()))
				)
				.externalDocs(new ExternalDocumentation()
						.description(serviceConfig.getServiceName()+" Service Source Code")
						.url(serviceConfig.getServiceApiRepository())
				)
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT"))
				);
	}

	/**
	 * Get the List of Servers for Open API Docs - Swagger
	 * @return
	 */
	private List<Server> getServers() {
		List<Server> serverList = new ArrayList<Server>();

		Server dev = new Server();
		dev.setUrl(serviceConfig.getServerHostDev());
		dev.setDescription(serviceConfig.getServerHostDevDesc());
		Server uat = new Server();
		uat.setUrl(serviceConfig.getServerHostUat());
		uat.setDescription(serviceConfig.getServerHostUatDesc());
		Server prod = new Server();
		prod.setUrl(serviceConfig.getServerHostProd());
		prod.setDescription(serviceConfig.getServerHostProdDesc());

		serverList.add(dev);
		serverList.add(uat);
		serverList.add(prod);

		return serverList;
	}

	/**
	 * Returns the REST Template
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns the Object Mapper
	 * @return
	 */

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.findAndRegisterModules();
	}
}
