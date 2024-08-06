package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "AUTHOR_NPLUSONE")
@NoArgsConstructor
public class AuthorNPlusOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookNPlusOne> books;


    @OneToMany(mappedBy = "batchFetchedAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // STRATEGY 4 - BATCH
    private List<BookNPlusOne> batchFetchedBooks;

    @OneToMany(mappedBy = "subSelectedAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) // STRATEGY 5 - SUBSELECT
    private List<BookNPlusOne> subselectBooks;

    // To see the JPA/Hibernate messing when brings two lists with JOIN FETCH
    @OneToMany(mappedBy = "authorComJoinFetch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookNPlusOne> bookComJoinFetch;

    // To see the JPA/Hibernate messing when brings two lists with JOIN FETCH
    @OneToMany(mappedBy = "authorComJoinFetch2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookNPlusOne> bookComJoinFetch2;

    public AuthorNPlusOne(String firstName) {
        this.firstName = firstName;
    }

    public void setAllBooks(List<BookNPlusOne> receivedBooks) {
        this.books = receivedBooks;
        this.batchFetchedBooks = receivedBooks;
        this.subselectBooks = receivedBooks;
    }
}
