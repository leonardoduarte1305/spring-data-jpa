package dev.leoduarte.spingdatajpa.lazyoperations.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "CHILD_ONE_TO_MANY_TABLE")
@AllArgsConstructor
@NoArgsConstructor
public class ChildOneToMany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHILD_ONE_TO_MANY_TABLE")
    private Long id;

    @Column(name = "PROPERTY_ONE")
    private String propertyOne;

    @Column(name = "PROPERTY_TWO")
    private String propertyTwo;

}
