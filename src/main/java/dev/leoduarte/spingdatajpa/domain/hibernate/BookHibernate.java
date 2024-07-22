package dev.leoduarte.spingdatajpa.domain.hibernate;

import jakarta.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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

    @CreationTimestamp
    @Column(name = "CREATED_DATE", updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "LAST_MODIFIED_DATE", updatable = false)
    private Timestamp modifiedDate;

    private String title;
    private String isbn;
    private String publisher;

    @Transient
    private AuthorHibernate author;

    public BookHibernate(Long id, String title, String isbn, String publisher, AuthorHibernate author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.author = author;
    }
}
