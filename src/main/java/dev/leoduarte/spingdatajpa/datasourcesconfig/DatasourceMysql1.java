package dev.leoduarte.spingdatajpa.datasourcesconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@ConditionalOnProperty(prefix = "spring.datasourcemysql1.enabled", value = "true")
@EnableJpaRepositories(
        basePackages = {"dev.leoduarte.spingdatajpa.domain"},
        entityManagerFactoryRef = "mysql1EntityManagerFactory",
        transactionManagerRef = "mysql1TransactionManager"
)
@Configuration
public class DatasourceMysql1 {

    private static final String MYSQL1_PERSISTENCE_UNIT = "mysql1";

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasourcemysql1.datasource")
    public DataSourceProperties mysql1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource mysql1DataSource(@Qualifier("mysql1DataSourceProperties") DataSourceProperties mysql1DataSourceProperties) {
        return mysql1DataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysql1EntityManagerFactory(
            @Qualifier("mysql1DataSource") DataSource mysql1DataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysql1DataSource)
                .packages("dev/leoduarte/spingdatajpa")
                .persistenceUnit(MYSQL1_PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysql1TransactionManager(
            @Qualifier("mysql1EntityManagerFactory")
            LocalContainerEntityManagerFactoryBean mysql1EntityManagerFactory) {
        return new JpaTransactionManager(mysql1EntityManagerFactory.getObject());
    }
}
