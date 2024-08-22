package dev.leoduarte.spingdatajpa.domain.listeners.config;

import dev.leoduarte.spingdatajpa.domain.listeners.listeners.PostLoadListener;
import dev.leoduarte.spingdatajpa.domain.listeners.listeners.PreInsertListener;
import dev.leoduarte.spingdatajpa.domain.listeners.listeners.PreUpdateListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class ListenerRegistration implements BeanPostProcessor {

    private final PostLoadListener postLoadListener;
    private final PreInsertListener preInsertListener;
    private final PreUpdateListener preUpdateListener;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof LocalContainerEntityManagerFactoryBean) {
//            LocalContainerEntityManagerFactoryBean lemf = (LocalContainerEntityManagerFactoryBean) bean;
//            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) lemf.getNativeEntityManagerFactory();
//            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//
//            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
//            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
//            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
//        }
        return bean;
    }
}
