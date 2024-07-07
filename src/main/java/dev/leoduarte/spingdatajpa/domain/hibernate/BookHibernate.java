package dev.leoduarte.spingdatajpa.domain.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
        @NamedQuery(name = "book_find_all", query = "FROM BookHibernate "),
        @NamedQuery(name = "book_find_by_isbn", query = "FROM BookHibernate b WHERE b.isbn = :isbn")
})

@Table(name = "BOOK_HIBERNATE")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String isbn;
    private String publisher;

    @Transient
    private AuthorHibernate author;

}
