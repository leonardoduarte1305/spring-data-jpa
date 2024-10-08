package dev.leoduarte.spingdatajpa.domain.interceptors.domain;

import dev.leoduarte.spingdatajpa.domain.callback.CreditCardConverter;
import dev.leoduarte.spingdatajpa.domain.callback.CreditCardJPACallback;
import dev.leoduarte.spingdatajpa.domain.interceptors.interceptors.ShouldBeEncrypted;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name = "CREDIT_CARD")
@AllArgsConstructor
@NoArgsConstructor
//@EntityListeners({CreditCardJPACallback.class})
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CREDIT_CARD")
    private Long id;

    @Column(name = "CC_NUMBER")
//    @ShouldBeEncrypted
    @Convert(converter = CreditCardConverter.class)
    private String ccNumber;

    @Column(name = "CVV")
    private String cvv;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

}
