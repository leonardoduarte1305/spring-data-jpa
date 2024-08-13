package dev.leoduarte.spingdatajpa.lazyoperations.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "ENTITY_WITH_DATA_ANNOTATION_MESS")
@AllArgsConstructor
@NoArgsConstructor
public class EntityWithDataAnnotationMess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENTITY_WITH_DATA_ANNOTATION_MESS")
    private Long id;

    @Column(name = "PROPERTY_ONE")
    private String propertyOne;

    @Column(name = "PROPERTY_TWO")
    private String propertyTwo;

    @JoinColumn(name = "MESS_CHILD_ONE_TO_ONE")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private ChildOneToOne childOneToOne;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MESS_CHILD_MANY_TO_ONE")
    private ChildManyToOne childManyToOne;

}
