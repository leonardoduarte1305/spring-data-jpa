package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

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
public class OrderHeader extends BaseEntity {

    private String customer;
}
