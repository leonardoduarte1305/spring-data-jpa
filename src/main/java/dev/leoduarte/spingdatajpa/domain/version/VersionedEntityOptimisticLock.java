package dev.leoduarte.spingdatajpa.domain.version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "VERSIONED_ENTITY")
@NoArgsConstructor
public class VersionedEntityOptimisticLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VERSIONED_ENTITY")
    private Long id;

    @Column(name = "PROPERTY_ONE")
    private String propertyOne;

    @Version
    @Column(name = "ACTUAL_VERSION")
    private Integer version;

    public VersionedEntityOptimisticLock(String propertyOne) {
        this.propertyOne = propertyOne;
    }
}
