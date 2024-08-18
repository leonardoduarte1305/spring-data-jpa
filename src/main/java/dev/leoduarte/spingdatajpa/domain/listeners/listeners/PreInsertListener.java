package dev.leoduarte.spingdatajpa.domain.listeners.listeners;

import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreInsertListener extends AbstractEncryptionListener implements PreInsertEventListener {

    public PreInsertListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        System.out.println("In preInsert uses encrypt");
        this.encrypt(event.getEntity(), event.getState(), event.getPersister().getPropertyNames());
        return false;
    }
}
