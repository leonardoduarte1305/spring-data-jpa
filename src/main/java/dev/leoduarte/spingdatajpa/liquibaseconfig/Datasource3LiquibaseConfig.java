package dev.leoduarte.spingdatajpa.liquibaseconfig;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.datasourcemysql3.enabled", value = "true")
public class Datasource3LiquibaseConfig implements SpringLiquibaseFiller {

    @Qualifier("mysql3DataSource")
    private final DataSource datasource;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasourcemysql3.liquibase", ignoreInvalidFields = true)
    public LiquibaseProperties liquibasePropertiesForDataSource3() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase datasource3SpringLiquibase(
            @Qualifier("liquibasePropertiesForDataSource3")
            LiquibaseProperties properties) {
        return springLiquibase(datasource, properties);
    }

}
