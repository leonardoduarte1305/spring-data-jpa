package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

@Getter
@Setter
@Profile("default")
@Table(name = "ORDER_HEADER")
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverrides({
        @AttributeOverride(
                name = "billingToAddress.address", // This match the Java property
                column = @Column(name = "billing_to_address", length = 30)), // This match the database property
        @AttributeOverride(
                name = "billingToAddress.city", // This match the Java property
                column = @Column(name = "billing_to_city", length = 30)), // This match the database property
        @AttributeOverride(
                name = "billingToAddress.state", // This match the Java property
                column = @Column(name = "billing_to_state", length = 30)), // This match the database property
        @AttributeOverride(
                name = "billingToAddress.zipCode", // This match the Java property
                column = @Column(name = "billing_to_zip_code", length = 30)) // This match the database property
})
public class OrderHeader extends BaseEntity {

    private String customer;

    @Embedded
    private Address shippingToAddress;

    @Embedded
    private Billing billingToAddress;
}
