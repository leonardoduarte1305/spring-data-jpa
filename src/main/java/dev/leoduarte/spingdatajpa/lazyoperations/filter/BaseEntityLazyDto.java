package dev.leoduarte.spingdatajpa.lazyoperations.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntityLazyDto implements Serializable {
    private Long id;
    private String propertyOne;
    private String propertyTwo;
    private ChildOneToOneDto childOneToOne;
    private Set<ChildOneToManyDto> childOneToMany = new HashSet<>();
    private ChildManyToOneDto childManyToOne;
    private Set<ChildManyToManyDto> childManyToMany = new HashSet<>();
}
