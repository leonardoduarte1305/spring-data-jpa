package dev.leoduarte.spingdatajpa.lazyoperations.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "BASE_ENTITY_LAZY")
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityLazy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BASE_ENTITY_LAZY")
    private Long id;

    @Column(name = "PROPERTY_ONE")
    private String propertyOne;

    @Column(name = "PROPERTY_TWO")
    private String propertyTwo;

    @JoinColumn(name = "CHILD_ONE_TO_ONE")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private ChildOneToOne childOneToOne;

    @JoinColumn(name = "CHILD_ONE_TO_MANY")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Builder.Default
    private Set<ChildOneToMany> childOneToMany = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CHILD_MANY_TO_ONE")
    private ChildManyToOne childManyToOne;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "MANY_TO_MANY_TABLE",
            joinColumns = @JoinColumn(name = "ID_BASE_ENTITY_LAZY"),
            inverseJoinColumns = @JoinColumn(name = "ID_CHILD_MANY_TO_MANY_TABLE"))
    @Builder.Default
    private Set<ChildManyToMany> childManyToMany = new HashSet<>();

}
