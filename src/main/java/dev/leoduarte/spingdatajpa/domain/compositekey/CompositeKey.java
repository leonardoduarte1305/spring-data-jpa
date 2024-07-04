package dev.leoduarte.spingdatajpa.domain.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompositeKey implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    protected String title;

    @Id
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    protected String isbn;
}
