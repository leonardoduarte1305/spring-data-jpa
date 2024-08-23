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

@ConditionalOnProperty(prefix = "spring.datasourcemysql2.enabled", value = "true")
@EnableJpaRepositories(
        basePackages = {"dev.leoduarte.spingdatajpa.lazyoperations"},
        entityManagerFactoryRef = "mysql2EntityManagerFactory",
        transactionManagerRef = "mysql2TransactionManager"
)
@Configuration
public class DatasourceMysql2 {

    private static final String MYSQL2_PERSISTENCE_UNIT = "mysql2";

    @Bean
    @ConfigurationProperties("spring.datasourcemysql2.datasource")
    public DataSourceProperties mysql2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysql2DataSource(@Qualifier("mysql2DataSourceProperties") DataSourceProperties mysql2DataSourceProperties) {
        return mysql2DataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysql2EntityManagerFactory(
            @Qualifier("mysql2DataSource") DataSource mysql2DataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysql2DataSource)
                .packages("dev/leoduarte/spingdatajpa")
                .persistenceUnit(MYSQL2_PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    public PlatformTransactionManager mysql2TransactionManager(
            @Qualifier("mysql2EntityManagerFactory")
            LocalContainerEntityManagerFactoryBean mysql2EntityManagerFactory) {
        return new JpaTransactionManager(mysql2EntityManagerFactory.getObject());
    }
}
