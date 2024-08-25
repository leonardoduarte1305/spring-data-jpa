package dev.leoduarte.spingdatajpa.liquibaseconfig;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import javax.sql.DataSource;

public interface SpringLiquibaseFiller {

    default SpringLiquibase springLiquibase(DataSource datasource, LiquibaseProperties properties) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(datasource);
        springLiquibase.setChangeLog(properties.getChangeLog());
        springLiquibase.setContexts(properties.getContexts());
        springLiquibase.setDefaultSchema(properties.getDefaultSchema());
        springLiquibase.setDropFirst(properties.isDropFirst());
        springLiquibase.setShouldRun(properties.isEnabled());
        springLiquibase.setLabelFilter(properties.getLabelFilter());
        springLiquibase.setChangeLogParameters(properties.getParameters());
        springLiquibase.setRollbackFile(properties.getRollbackFile());
        return springLiquibase;
    }
}
