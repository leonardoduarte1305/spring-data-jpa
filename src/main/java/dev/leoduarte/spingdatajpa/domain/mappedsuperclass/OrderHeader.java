package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Profile("default")
@Table(name = "ORDER_HEADER")
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private Address shippingToAddress;
    private Billing billingToAddress;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.PERSIST)
    private Set<OrderLine> orderLines = new HashSet<>();
}
