package dev.leoduarte.spingdatajpa.dao.springdatajpa;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.AuthorSpringJPA;
import dev.leoduarte.spingdatajpa.repository.AuthorSpringJPARepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthorSpringDataJPADaoImpl implements AuthorDao<AuthorSpringJPA> {

    private final AuthorSpringJPARepository repository;

    @Override
    public AuthorSpringJPA getById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public AuthorSpringJPA getByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AuthorSpringJPA saveNewAuthor(AuthorSpringJPA author) {
        return repository.save(author);
    }

    @Override
    @Transactional
    public AuthorSpringJPA updateAuthor(Long id, AuthorSpringJPA author) {
        AuthorSpringJPA found = repository.getReferenceById(id);
        found.setFirstName(author.getFirstName());
        found.setLastName(author.getLastName());

        return repository.save(found);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
