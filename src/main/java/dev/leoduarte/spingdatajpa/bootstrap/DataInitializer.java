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
import dev.leoduarte.spingdatajpa.repository.AuthorRepository;
import dev.leoduarte.spingdatajpa.repository.BookCompositeKeyRepository;
import dev.leoduarte.spingdatajpa.repository.BookEmbeddedKeyRepository;
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

    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        uuidRepository.deleteAll();
        rfc4122Repository.deleteAll();
        naturalKeyRepository.deleteAll();
        compositeKeyRepository.deleteAll();
        embeddedKeyRepository.deleteAll();

        authorRepository.deleteAll();
        Author author1 = new Author(null, "Leoduarte1", "Silva1");
        Author author2 = new Author(null, "Leoduarte2", "Silva2");
        Author author3 = new Author(null, "Leoduarte3", "Silva3");
        Author author4 = new Author(null, "Leoduarte4", "Silva4");
        Author author5 = new Author(null, "Leoduarte5", "Silva5");
        Author author6 = new Author(null, "Leoduarte6", "Silva6");
        Author author7 = new Author(null, "Leoduarte7", "Silva7");
        Author author8 = new Author(null, "Leoduarte8", "Silva8");
        Author author9 = new Author(null, "Leoduarte9", "Silva9");
        authorRepository.saveAll(List.of(author1, author2, author3, author4, author5, author6, author7));


        Book book1 = new Book(null, "Title 1", "ISBN 11", "Plublisher 21", author1);
        Book book2 = new Book(null, "Title 2", "ISBN 12", "Plublisher 22", author2);
        Book book3 = new Book(null, "Title 3", "ISBN 13", "Plublisher 23", author3);
        Book book4 = new Book(null, "Title 4", "ISBN 14", "Plublisher 24", author4);
        Book book5 = new Book(null, "Title 5", "ISBN 15", "Plublisher 25", author5);
        Book book6 = new Book(null, "Title 6", "ISBN 16", "Plublisher 26", author6);
        Book book7 = new Book(null, "Title 7", "ISBN 17", "Plublisher 27", author7);
        Book book8 = new Book(null, "Title 8", "ISBN 18", "Plublisher 28", author8);
        Book book9 = new Book(null, "Title 9", "ISBN 19", "Plublisher 29", author9);

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9));

        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);

        BookUUIDRFC4122 uuidrfc4122 = new BookUUIDRFC4122(null, "BookUUIDRFC4122 1", "ISBN BookUUIDRFC4122", "Publisher: BookUUIDRFC4122", 1L);
        BookUUIDRFC4122 savedBookUUIDRFC4122 = rfc4122Repository.save(uuidrfc4122);
        System.out.println("savedBookUUIDRFC4122 = " + savedBookUUIDRFC4122);

        BookUUID bookUUID = new BookUUID(null, "BookUUIDRFC4122 1", "ISBN BookUUIDRFC4122", "Publisher: BookUUIDRFC4122", 1L);
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

    }

}
