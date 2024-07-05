package dev.leoduarte.spingdatajpa.domain.embeddedkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class CompositeEmbeddedKey {

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    protected String title;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    protected String isbn;
}
