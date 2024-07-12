package dev.leoduarte.spingdatajpa.domain.springdatajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@NamedQueries(value = {
        // Used => @NamedQuery(name = "EntityName.matchingNameWithRepositorysMethodsName",
        @NamedQuery(name = "BookSpringJPA.namedQueryToUse",
                query = "FROM BookSpringJPA b WHERE b.title = :title")
})
@Table(name = "BOOK_SPRING_JPA")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookSpringJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long author;
}
