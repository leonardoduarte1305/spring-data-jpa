package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Profile;

@Profile("default")
@EqualsAndHashCode
@Embeddable
public class Address {

    // As these @Column definition are here they are not necessary
    // to be registered in @AttributeOverrides inside the main class

    @Column(name = "shippingAddress", length = 30)
    private String address;

    @Column(name = "shippingCity", length = 30)
    private String city;

    @Column(name = "shippingState", length = 30)
    private String state;

    @Column(name = "shippingZipCode", length = 30)
    private String zipCode;

}
