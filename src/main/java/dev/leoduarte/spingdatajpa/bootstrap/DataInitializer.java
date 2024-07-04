package dev.leoduarte.spingdatajpa.bootstrap;

import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.domain.BookNaturalKey;
import dev.leoduarte.spingdatajpa.domain.BookUUID;
import dev.leoduarte.spingdatajpa.domain.BookUUIDRFC4122;
import dev.leoduarte.spingdatajpa.domain.compositekey.BookCompositeKey;
import dev.leoduarte.spingdatajpa.domain.compositekey.CompositeKey;
import dev.leoduarte.spingdatajpa.repository.BookCompositeKeyRepository;
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

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        uuidRepository.deleteAll();
        rfc4122Repository.deleteAll();
        naturalKeyRepository.deleteAll();
        compositeKeyRepository.deleteAll();

        Book book1 = new Book(null, "Title 1", "ISBN 11", "Plublisher 21", 1L);
        Book book2 = new Book(null, "Title 2", "ISBN 12", "Plublisher 22", 2L);
        Book book3 = new Book(null, "Title 3", "ISBN 13", "Plublisher 23", 3L);
        Book book4 = new Book(null, "Title 4", "ISBN 14", "Plublisher 24", 4L);
        Book book5 = new Book(null, "Title 5", "ISBN 15", "Plublisher 25", 5L);
        Book book6 = new Book(null, "Title 6", "ISBN 16", "Plublisher 26", 6L);
        Book book7 = new Book(null, "Title 7", "ISBN 17", "Plublisher 27", 7L);
        Book book8 = new Book(null, "Title 8", "ISBN 18", "Plublisher 28", 8L);
        Book book9 = new Book(null, "Title 9", "ISBN 19", "Plublisher 29", 9L);

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
    }

}
