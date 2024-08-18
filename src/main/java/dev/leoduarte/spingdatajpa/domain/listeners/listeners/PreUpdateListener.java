package dev.leoduarte.spingdatajpa.domain.listeners.listeners;

import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreUpdateListener extends AbstractEncryptionListener implements PreUpdateEventListener {

    public PreUpdateListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        System.out.println("In preUpdate uses encrypt");
        this.encrypt(event.getEntity(), event.getState(), event.getPersister().getPropertyNames());
        return false;
    }
}
