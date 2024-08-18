package dev.leoduarte.spingdatajpa.domain.listeners.listeners;

import dev.leoduarte.spingdatajpa.domain.interceptors.interceptors.ShouldBeEncrypted;
import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public abstract class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    public void encrypt(Object entity, Object[] state, String[] propertyNames) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames), this::isFieldEncrypted);
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames) {
        int index = getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[index];
        state[index] = encryptionService.encrypt(currentValue.toString());
    }

    private int getPropertyIndex(String name, String[] propertyNames) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (name.equals(propertyNames[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Proeprty not found: " + name);
    }

    private boolean isFieldEncrypted(Field field) {
        return AnnotationUtils.findAnnotation(field, ShouldBeEncrypted.class) != null;
    }

    public void decrypt(Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity), this::isFieldEncrypted);
    }

    private void decryptField(Field field, Object entity) {
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        ReflectionUtils.setField(field, entity, encryptionService.decrypt(value.toString()));
    }

}
