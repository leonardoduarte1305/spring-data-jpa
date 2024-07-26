package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PRODUCT_STATUS")
    private ProductStatus status;
}
