package dev.leoduarte.spingdatajpa.domain.callback;

import dev.leoduarte.spingdatajpa.domain.interceptors.domain.CreditCard;
import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CreditCardJPACallback {

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCard creditCard) {
        System.out.println("================== CreditCardJPACallback.beforeInsertOrUpdate()");
        creditCard.setCcNumber(getEncryptionService().encrypt(creditCard.getCcNumber()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCard creditCard) {
        System.out.println("================== CreditCardJPACallback.postLoad()");
        creditCard.setCcNumber(getEncryptionService().decrypt(creditCard.getCcNumber()));
    }

    private EncryptionService getEncryptionService() {
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }
}
