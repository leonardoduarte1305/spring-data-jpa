package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_one_to_one_lazy_relation1")
    private AuthorNPlusOne authorOneToOneLazyRelation1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_one_to_one_lazy_relation2")
    private AuthorNPlusOne authorOneToOneLazyRelation2;

    public BookNPlusOne(String title, AuthorNPlusOne receivedAuthor) {
        this.title = title;
        this.author = receivedAuthor;
        this.batchFetchedAuthor = receivedAuthor;
        this.subSelectedAuthor = receivedAuthor;
    }

    public BookNPlusOne(String title,
                        AuthorNPlusOne author,
                        AuthorNPlusOne batchFetchedAuthor,
                        AuthorNPlusOne subSelectedAuthor,
                        AuthorNPlusOne authorComJoinFetch,
                        AuthorNPlusOne authorComJoinFetch2,
                        AuthorNPlusOne authorOneToOneLazyRelation1,
                        AuthorNPlusOne authorOneToOneLazyRelation2) {
        this.title = title;
        this.author = author;
        this.batchFetchedAuthor = batchFetchedAuthor;
        this.subSelectedAuthor = subSelectedAuthor;
        this.authorComJoinFetch = authorComJoinFetch;
        this.authorComJoinFetch2 = authorComJoinFetch2;
        this.authorOneToOneLazyRelation1 = authorOneToOneLazyRelation1;
        this.authorOneToOneLazyRelation2 = authorOneToOneLazyRelation2;
    }
}
