package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Profile;

@Profile("default")
@EqualsAndHashCode
@Embeddable
public class Billing {

    private String address;
    private String city;
    private String state;
    private String zipCode;
}
