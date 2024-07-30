package dev.leoduarte.spingdatajpa.bootstrap;

import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.domain.BookNaturalKey;
import dev.leoduarte.spingdatajpa.domain.BookUUID;
import dev.leoduarte.spingdatajpa.domain.BookUUIDRFC4122;
import dev.leoduarte.spingdatajpa.domain.compositekey.BookCompositeKey;
import dev.leoduarte.spingdatajpa.domain.compositekey.CompositeKey;
import dev.leoduarte.spingdatajpa.domain.embeddedkey.BookEmbeddedKey;
import dev.leoduarte.spingdatajpa.domain.embeddedkey.CompositeEmbeddedKey;
import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOne;
import dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOneRepository;
import dev.leoduarte.spingdatajpa.domain.problementities.BookNPlusOne;
import dev.leoduarte.spingdatajpa.domain.problementities.BookNPlusOneRepository;
import dev.leoduarte.spingdatajpa.repository.AuthorHibernateRepository;
import dev.leoduarte.spingdatajpa.repository.AuthorRepository;
import dev.leoduarte.spingdatajpa.repository.BookCompositeKeyRepository;
import dev.leoduarte.spingdatajpa.repository.BookEmbeddedKeyRepository;
import dev.leoduarte.spingdatajpa.repository.BookHibernateRepository;
import dev.leoduarte.spingdatajpa.repository.BookNaturalKeyRepository;
import dev.leoduarte.spingdatajpa.repository.BookRepository;
import dev.leoduarte.spingdatajpa.repository.BookUuidRFC4122Repository;
import dev.leoduarte.spingdatajpa.repository.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile({"local", "default"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final BookUuidRFC4122Repository rfc4122Repository;
    private final BookUuidRepository uuidRepository;
    private final BookNaturalKeyRepository naturalKeyRepository;
    private final BookCompositeKeyRepository compositeKeyRepository;
    private final BookEmbeddedKeyRepository embeddedKeyRepository;
    private final BookHibernateRepository bookHibernateRepository;
    private final BookNPlusOneRepository bookNPlusOneRepository;
    private final AuthorNPlusOneRepository authorNPlusOneRepository;

    private final AuthorRepository authorRepository;
    private final AuthorHibernateRepository authorHibernateRepository;

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        uuidRepository.deleteAll();
        rfc4122Repository.deleteAll();
        naturalKeyRepository.deleteAll();
        compositeKeyRepository.deleteAll();
        embeddedKeyRepository.deleteAll();
        bookHibernateRepository.deleteAll();
        bookNPlusOneRepository.deleteAll();
        authorNPlusOneRepository.deleteAll();

        authorRepository.deleteAll();
        authorHibernateRepository.deleteAll();
        Author author1 = new Author("Leoduarte1", "Silva1");
        Author author2 = new Author("Leoduarte2", "Silva2");
        Author author3 = new Author("Leoduarte3", "Silva3");
        Author author4 = new Author("Leoduarte4", "Silva4");
        Author author5 = new Author("Leoduarte5", "Silva5");
        Author author6 = new Author("Leoduarte6", "Silva6");
        Author author7 = new Author("Leoduarte7", "Silva7");
        Author author8 = new Author("Leoduarte8", "Silva8");
        Author author9 = new Author("Leoduarte9", "Silva9");
        authorRepository.saveAll(List.of(author1, author2, author3, author4, author5, author6, author7));


        Book book1 = new Book("Title 1", "ISBN 11", "Plublisher 21", author1);
        Book book2 = new Book("Title 2", "ISBN 12", "Plublisher 22", author2);
        Book book3 = new Book("Title 3", "ISBN 13", "Plublisher 23", author3);
        Book book4 = new Book("Title 4", "ISBN 14", "Plublisher 24", author4);
        Book book5 = new Book("Title 5", "ISBN 15", "Plublisher 25", author5);
        Book book6 = new Book("Title 6", "ISBN 16", "Plublisher 26", author6);
        Book book7 = new Book("Title 7", "ISBN 17", "Plublisher 27", author7);
        Book book8 = new Book("Title 8", "ISBN 18", "Plublisher 28", author8);
        Book book9 = new Book("Title 9", "ISBN 19", "Plublisher 29", author9);

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9));

        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);

        BookUUIDRFC4122 uuidrfc4122 = new BookUUIDRFC4122("BookUUIDRFC4122 1", "ISBN BookUUIDRFC4122", "Publisher: BookUUIDRFC4122", 1L);
        BookUUIDRFC4122 savedBookUUIDRFC4122 = rfc4122Repository.save(uuidrfc4122);
        System.out.println("savedBookUUIDRFC4122 = " + savedBookUUIDRFC4122);

        BookUUID bookUUID = new BookUUID("BookUUIDRFC4122 1", "ISBN BookUUIDRFC4122", "Publisher: BookUUIDRFC4122", 1L);
        BookUUID savedBookUUID = uuidRepository.save(bookUUID);
        System.out.println("savedBookUUID = " + savedBookUUID);

        BookNaturalKey bookNatualKey = new BookNaturalKey("BookNaturalKey 1", "ISBN BookNaturalKey", "Publisher: BookNaturalKey", 1L);
        BookNaturalKey savedBookNaturalKey = naturalKeyRepository.save(bookNatualKey);
        System.out.println("savedBookNaturalKey = " + savedBookNaturalKey);

        CompositeKey compositeKey = new CompositeKey("BookCompositeKey 1", "ISBN BookCompositeKey");
        BookCompositeKey bookCompositeKey = new BookCompositeKey("BookCompositeKey 1", "ISBN BookCompositeKey", "Publisher: BookCompositeKey", 1L);
        BookCompositeKey savedBookCompositeKey = compositeKeyRepository.save(bookCompositeKey);
        System.out.println("savedBookCompositeKey = " + savedBookCompositeKey);

        CompositeEmbeddedKey embeddedKey = new CompositeEmbeddedKey("BookEmbeddedKey 1", "ISBN BookEmbeddedKey");
        BookEmbeddedKey bookEmbeddedKey = new BookEmbeddedKey(embeddedKey, "Publisher: BookCompositeKey", 1L);
        BookEmbeddedKey savedBookEmbeddedKey = embeddedKeyRepository.save(bookEmbeddedKey);
        System.out.println("savedBookEmbeddedKey = " + savedBookEmbeddedKey);

        AuthorHibernate authorHibernate1 = new AuthorHibernate("Leoduarte1", "Silva1");
        AuthorHibernate authorHibernate2 = new AuthorHibernate("Leoduarte2", "Silva2");
        AuthorHibernate authorHibernate3 = new AuthorHibernate("Leoduarte3", "Silva3");
        AuthorHibernate authorHibernate4 = new AuthorHibernate("Leoduarte4", "Silva4");
        AuthorHibernate authorHibernate5 = new AuthorHibernate("Leoduarte5", "Silva5");
        AuthorHibernate authorHibernate6 = new AuthorHibernate("Leoduarte6", "Silva6");
        AuthorHibernate authorHibernate7 = new AuthorHibernate("Leoduarte7", "Silva7");
        AuthorHibernate authorHibernate8 = new AuthorHibernate("Leoduarte8", "Silva8");
        AuthorHibernate authorHibernate9 = new AuthorHibernate("Leoduarte9", "Silva9");
        authorHibernateRepository.saveAll(List.of(authorHibernate1, authorHibernate2, authorHibernate3, authorHibernate4, authorHibernate5, authorHibernate6, authorHibernate7));


        BookHibernate bookHibernate1 = new BookHibernate("Title 1", "ISBN 11", "Plublisher 21", authorHibernate1);
        BookHibernate bookHibernate2 = new BookHibernate("Title 2", "ISBN 12", "Plublisher 22", authorHibernate2);
        BookHibernate bookHibernate3 = new BookHibernate("Title 3", "ISBN 13", "Plublisher 23", authorHibernate3);
        BookHibernate bookHibernate4 = new BookHibernate("Title 4", "ISBN 14", "Plublisher 24", authorHibernate4);
        BookHibernate bookHibernate5 = new BookHibernate("Title 5", "ISBN 15", "Plublisher 25", authorHibernate5);
        BookHibernate bookHibernate6 = new BookHibernate("Title 6", "ISBN 16", "Plublisher 26", authorHibernate6);
        BookHibernate bookHibernate7 = new BookHibernate("Title 7", "ISBN 17", "Plublisher 27", authorHibernate7);
        BookHibernate bookHibernate8 = new BookHibernate("Title 8", "ISBN 18", "Plublisher 28", authorHibernate8);
        BookHibernate bookHibernate9 = new BookHibernate("Title 9", "ISBN 19", "Plublisher 29", authorHibernate9);
        bookHibernateRepository.saveAll(Arrays.asList(bookHibernate1, bookHibernate2, bookHibernate3, bookHibernate4, bookHibernate5, bookHibernate6, bookHibernate7, bookHibernate8, bookHibernate9));


        AuthorNPlusOne authorNPlusOne1 = new AuthorNPlusOne("Author1");
        BookNPlusOne bookNPlusOne1 = new BookNPlusOne("book1", authorNPlusOne1);
        BookNPlusOne bookNPlusOne2 = new BookNPlusOne("book12", authorNPlusOne1);
        authorNPlusOne1.setAllBooks(List.of(bookNPlusOne1, bookNPlusOne2));


        AuthorNPlusOne authorNPlusOne2 = new AuthorNPlusOne("Author2");
        BookNPlusOne bookNPlusOne3 = new BookNPlusOne("book13", authorNPlusOne2);
        BookNPlusOne bookNPlusOne4 = new BookNPlusOne("book14", authorNPlusOne2);
        BookNPlusOne bookNPlusOne5 = new BookNPlusOne("book15", authorNPlusOne2);
        authorNPlusOne2.setAllBooks(List.of(bookNPlusOne3, bookNPlusOne4, bookNPlusOne5));

        AuthorNPlusOne authorNPlusOne3 = new AuthorNPlusOne("Author3");
        BookNPlusOne bookNPlusOne6 = new BookNPlusOne("book23", authorNPlusOne3);
        BookNPlusOne bookNPlusOne7 = new BookNPlusOne("book24", authorNPlusOne3);
        BookNPlusOne bookNPlusOne8 = new BookNPlusOne("book25", authorNPlusOne3);
        authorNPlusOne3.setAllBooks(List.of(bookNPlusOne6, bookNPlusOne7, bookNPlusOne8));

        authorNPlusOneRepository.saveAll(List.of(authorNPlusOne1, authorNPlusOne2, authorNPlusOne3));

    }

}
