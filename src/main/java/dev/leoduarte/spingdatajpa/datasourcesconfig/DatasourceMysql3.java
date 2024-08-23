package dev.leoduarte.spingdatajpa.datasourcesconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@ConditionalOnProperty(prefix = "spring.datasourcemysql3.enabled", value = "true")
@EnableJpaRepositories(
        basePackages = {"dev.leoduarte.spingdatajpa.dao"},
        entityManagerFactoryRef = "mysql3EntityManagerFactory",
        transactionManagerRef = "mysql3TransactionManager"
)
@Configuration
public class DatasourceMysql3 {

    private static final String MYSQL3_PERSISTENCE_UNIT = "mysql3";

    @Bean
    @ConfigurationProperties("spring.datasourcemysql3.datasource")
    public DataSourceProperties mysql3DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysql3DataSource(@Qualifier("mysql3DataSourceProperties") DataSourceProperties mysql3DataSourceProperties) {
        return mysql3DataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysql3EntityManagerFactory(
            @Qualifier("mysql3DataSource") DataSource mysql3DataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysql3DataSource)
                .packages("dev/leoduarte/spingdatajpa")
                .persistenceUnit(MYSQL3_PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    public PlatformTransactionManager mysql3TransactionManager(
            @Qualifier("mysql3EntityManagerFactory")
            LocalContainerEntityManagerFactoryBean mysql3EntityManagerFactory) {
        return new JpaTransactionManager(mysql3EntityManagerFactory.getObject());
    }
}
