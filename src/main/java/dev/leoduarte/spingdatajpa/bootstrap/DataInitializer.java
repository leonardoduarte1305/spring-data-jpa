package dev.leoduarte.spingdatajpa.bootstrap;

import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.repository.BookRepository;
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

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();

        Book book1 = new Book(null, "Title 1", "ISBN 11", "Plublisher 21");
        Book book2 = new Book(null, "Title 2", "ISBN 12", "Plublisher 22");
        Book book3 = new Book(null, "Title 3", "ISBN 13", "Plublisher 23");
        Book book4 = new Book(null, "Title 4", "ISBN 14", "Plublisher 24");
        Book book5 = new Book(null, "Title 5", "ISBN 15", "Plublisher 25");
        Book book6 = new Book(null, "Title 6", "ISBN 16", "Plublisher 26");
        Book book7 = new Book(null, "Title 7", "ISBN 17", "Plublisher 27");
        Book book8 = new Book(null, "Title 8", "ISBN 18", "Plublisher 28");
        Book book9 = new Book(null, "Title 9", "ISBN 19", "Plublisher 29");

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9));

        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

}
