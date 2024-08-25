package dev.leoduarte.spingdatajpa.liquibaseconfig;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.datasourcemysql1.enabled", value = "true")
public class Datasource1LiquibaseConfig implements SpringLiquibaseFiller {

    @Qualifier("mysql1DataSource")
    private final DataSource datasource;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourcemysql1.liquibase", ignoreInvalidFields = true)
    public LiquibaseProperties liquibasePropertiesForDataSource1() {
        return new LiquibaseProperties();
    }

    @Bean
    @Primary
    public SpringLiquibase datasource1SpringLiquibase(
            @Qualifier("liquibasePropertiesForDataSource1")
            LiquibaseProperties properties) {
        return springLiquibase(datasource, properties);
    }

}
