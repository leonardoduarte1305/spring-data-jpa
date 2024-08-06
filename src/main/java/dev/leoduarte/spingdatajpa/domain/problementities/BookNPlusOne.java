package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "BOOK_NPLUSONE")
@NoArgsConstructor
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
    @JoinColumn(name = "sub_selected_author_id")
    private AuthorNPlusOne subSelectedAuthor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_com_join_fetch")
    private AuthorNPlusOne authorComJoinFetch;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_com_join_fetch_2")
    private AuthorNPlusOne authorComJoinFetch2;

    public BookNPlusOne(String title, AuthorNPlusOne receivedAuthor) {
        this.title = title;
        this.author = receivedAuthor;
        this.batchFetchedAuthor = receivedAuthor;
        this.subSelectedAuthor = receivedAuthor;
    }
}
