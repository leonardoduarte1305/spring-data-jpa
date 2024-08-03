package dev.leoduarte.spingdatajpa.lazyoperations.filter;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link ChildManyToMany}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ChildManyToManyDto implements Serializable {
    private Long id;
    private String propertyOne;
    private String propertyTwo;
}
