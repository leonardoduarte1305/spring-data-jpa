package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

@Getter
@Setter
@Profile("default")
@Table(name = "ORDER_LINE")
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine extends BaseEntity {

    @EqualsAndHashCode.Include
    @Column(name = "QUANTITY_ORDER")
    private Integer quantityOrdered;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne(optional = false, targetEntity = Product.class)
    private Product product;
}
