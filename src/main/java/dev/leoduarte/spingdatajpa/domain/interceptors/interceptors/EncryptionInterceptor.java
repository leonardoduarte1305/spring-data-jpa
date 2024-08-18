package dev.leoduarte.spingdatajpa.domain.interceptors.interceptors;

import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
//@Component
public class EncryptionInterceptor implements Interceptor {

    private final EncryptionService encryptionService;

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        System.err.println("I am in onFlushDirty");
        Object[] newstate = processFields(entity, currentState, propertyNames, "onFlushDirty");

        return Interceptor.super.onFlushDirty(entity, id, newstate, previousState, propertyNames, types);
    }

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        System.err.println("I am in onLoad");
        Object[] newstate = processFields(entity, state, propertyNames, "onLoad");

        return Interceptor.super.onLoad(entity, id, newstate, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        System.err.println("I am in onSave");
        Object[] newstate = processFields(entity, state, propertyNames, "onSave");

        return Interceptor.super.onSave(entity, id, newstate, propertyNames, types);
    }

    private Object[] processFields(Object entity, Object[] state, String[] propertyNames, String type) {
        List<String> annotatedFields = getAnnotatedFilds(entity);

        for (String field : annotatedFields) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field)) {
                    if (StringUtils.hasText(state[i].toString())) {
                        if ("onSave".equals(type) | "onFlushDirty".equals(type)) {
                            state[i] = encryptionService.encrypt(state[i].toString());
                        } else if ("onLoad".equals(type)) {
                            state[i] = encryptionService.decrypt(state[i].toString());
                        }
                    }
                }
            }
        }

        return state;
    }

    private List<String> getAnnotatedFilds(Object entity) {
        List<String> annotatedFields = new ArrayList<>();

        for (Field fields : entity.getClass().getDeclaredFields()) {
            if (!Objects.isNull(fields.getAnnotation(ShouldBeEncrypted.class))) {
                annotatedFields.add(fields.getName());
            }
        }

        return annotatedFields;
    }
}
