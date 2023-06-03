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
package io.fusion.air.microservice.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// REACTIVE IMPORTS

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Configuration
// Data Models
@EntityScan("io.fusion.air.microservice.domain.*")
@EnableTransactionManagement
public class DatabaseConfig {

    @Autowired
    private ServiceConfiguration serviceConfig;

    // =================================================================================================================
    // REACTIVE Database
    // =================================================================================================================
    @Bean
    public ConnectionFactory connectionFactory() {
        switch(serviceConfig.getDataSourceVendor()) {
            case ServiceConfiguration.DB_H2:
                // For H2 Database
                return getH2ConnectionFactory();
            case ServiceConfiguration.DB_POSTGRESQL:
                // For PostgreSQL Database
                return getPostgreSQLConnectionFactory();
        }
        // Default H2 Database
        return getH2ConnectionFactory();
    }

    /**
     * Returns the Reactive Transaction Manager
     * @param connectionFactory
     * @return
     */
    @Bean
    @Qualifier("reactiveTransactionManager")
    public ReactiveTransactionManager reactiveTransactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }


    /**
     * Create H2 Connection Factory
     * @return
     */
    private ConnectionFactory getH2ConnectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "h2")
                .option(PROTOCOL, "mem")
                .option(DATABASE, "ms-cache")
                .option(USER, "sa")
                .option(PASSWORD, "")
                .build());
    }

    /**
     * Create PostgreSQL Connection Factory
     * @return
     */
    private ConnectionFactory getPostgreSQLConnectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "postgresql")
                .option(HOST, "localhost")
                .option(PORT, 5433)
                .option(DATABASE, "ms_cache")
                .option(USER, "postgres")
                .option(PASSWORD, "")
                .build());
    }

    // =================================================================================================================
    // Database INIT
    // =================================================================================================================
    /**
     * Create the Database and Tables if it doesn't exist
     * @param connectionFactory
     * @return
     */
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("ms-webflux-h2.sql")));

        return initializer;
    }

}
