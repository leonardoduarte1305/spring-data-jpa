package dev.leoduarte.spingdatajpa.lazyoperations.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "CHILD_MANY_TO_MANY_TABLE")
@AllArgsConstructor
@NoArgsConstructor
public class ChildManyToMany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHILD_MANY_TO_MANY_TABLE")
    private Long id;

    @Column(name = "PROPERTY_ONE")
    private String propertyOne;

    @Column(name = "PROPERTY_TWO")
    private String propertyTwo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MANY_TO_MANY_TABLE",
            joinColumns = @JoinColumn(name = "ID_CHILD_MANY_TO_MANY_TABLE"),
            inverseJoinColumns = @JoinColumn(name = "ID_BASE_ENTITY_LAZY"))
    @Builder.Default
    private Set<BaseEntityLazy> childManyToMany = new HashSet<>();

    public void addBaseEntity(BaseEntityLazy baseEntityLazy) {
        childManyToMany.add(baseEntityLazy);
    }
}
