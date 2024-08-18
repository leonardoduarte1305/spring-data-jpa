package dev.leoduarte.spingdatajpa.domain.interceptors.config;

import dev.leoduarte.spingdatajpa.domain.interceptors.interceptors.EncryptionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    private final EncryptionInterceptor encryptionInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor);
    }
}
