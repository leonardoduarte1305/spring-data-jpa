package dev.leoduarte.spingdatajpa.domain.embeddedkey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "BOOK_EMBEDDED_KEY")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookEmbeddedKey {

    @EmbeddedId
    private CompositeEmbeddedKey embeddedKey;

    private String publisher;
    private Long authorId;
}
