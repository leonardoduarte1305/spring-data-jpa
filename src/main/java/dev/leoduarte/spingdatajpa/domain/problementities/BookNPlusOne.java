package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Profile("default")
public class BookNPlusOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorNPlusOne author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fetched_author_id")
    private AuthorNPlusOne batchFetchedAuthor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subSelected_author_id")
    private AuthorNPlusOne subSelectedAuthor;

    public BookNPlusOne(String title, AuthorNPlusOne receivedAuthor) {
        this.title = title;
        this.author = receivedAuthor;
        this.batchFetchedAuthor = receivedAuthor;
        this.subSelectedAuthor = receivedAuthor;
    }
}
