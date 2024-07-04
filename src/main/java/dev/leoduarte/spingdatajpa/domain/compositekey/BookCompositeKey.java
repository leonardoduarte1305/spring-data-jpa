package dev.leoduarte.spingdatajpa.domain.compositekey;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "BOOK_COMPOSITE_KEY")
@IdClass(CompositeKey.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookCompositeKey {

    @Id
    protected String title;

    @Id
    protected String isbn;

    private String publisher;
    private Long authorId;

}
