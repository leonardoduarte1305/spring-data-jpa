package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy;
import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildManyToMany;
import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildManyToOne;
import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildOneToMany;
import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildOneToOne;
import dev.leoduarte.spingdatajpa.lazyoperations.repository.BaseEntityLazyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class LazyLoadingTests {

    int tamanhoString = 0;
    int numRegistros = 0;

    @Autowired
    private BaseEntityLazyRepository repository;

    @BeforeEach
    void setUp() {
        tamanhoString = parseInt(valueOf(round(random() * 10 + 5)));
        numRegistros = parseInt(valueOf(round(random() * 10 + 5)));
    }

    @RepeatedTest(100)
    @Order(1)
    @Commit
    void carregaDadosNoBancoDeDados() {
        BaseEntityLazy baseEntityLazy = BaseEntityLazy
                .builder()
                .propertyOne("Name With Characters")
                .propertyTwo("Another Name With Characters")
                .childOneToOne(getChildOneToOne())
                .childManyToOne(getChildManyToOne())
                .childOneToMany(getChildOneToManies(numRegistros))
                .childManyToMany(getChildManyToManies(numRegistros))
                .build();

        repository.save(baseEntityLazy);
    }

    private Set<ChildManyToMany> getChildManyToManies(int numRegistros) {
        HashSet<ChildManyToMany> setOfChildren = new HashSet<>();

        for (int i = 0; i < numRegistros; i++) {
            setOfChildren.add(ChildManyToMany
                    .builder()
                    .propertyOne(randomAlphanumeric(tamanhoString))
                    .propertyTwo(randomAlphanumeric(tamanhoString))
                    .build());
        }

        return setOfChildren;
    }

    private ChildOneToOne getChildOneToOne() {
        return ChildOneToOne
                .builder()
                .propertyOne(randomAlphanumeric(tamanhoString))
                .propertyTwo(randomAlphanumeric(tamanhoString))
                .build();
    }

    private ChildManyToOne getChildManyToOne() {
        return ChildManyToOne
                .builder()
                .propertyOne(randomAlphanumeric(tamanhoString))
                .propertyTwo(randomAlphanumeric(tamanhoString))
                .build();
    }

    private Set<ChildOneToMany> getChildOneToManies(int numRegistros) {
        HashSet<ChildOneToMany> setOfChildren = new HashSet<>();

        for (int i = 0; i < numRegistros; i++) {
            setOfChildren.add(ChildOneToMany
                    .builder()
                    .propertyOne(randomAlphanumeric(tamanhoString))
                    .propertyTwo(randomAlphanumeric(tamanhoString))
                    .build());
        }

        return setOfChildren;
    }

    @Test
    @Order(2)
    void testaLazyLoading() {
        Pageable page = Pageable.ofSize(10);
        Page<BaseEntityLazy> recebidos = repository.findAll(page);

        recebidos.forEach(recebido -> {
            System.out.println("recebido.getId() = " + recebido.getId());
            System.out.println("recebido.getPropertyOne() = " + recebido.getPropertyOne());
            System.out.println("recebido.getPropertyTwo() = " + recebido.getPropertyTwo());

            System.out.println("recebido.getChildOneToOne().getId() = " + recebido.getChildOneToOne().getId());
            System.out.println("recebido.getChildOneToOne().getPropertyOne() = " + recebido.getChildOneToOne().getPropertyOne());
            System.out.println("recebido.getChildOneToOne().getPropertyTwo() = " + recebido.getChildOneToOne().getPropertyTwo());

            System.out.println("recebido.getChildManyToOne().getId() = " + recebido.getChildManyToOne().getId());
            System.out.println("recebido.getChildManyToOne().getPropertyOne() = " + recebido.getChildManyToOne().getPropertyOne());
            System.out.println("recebido.getChildManyToOne().getPropertyTwo() = " + recebido.getChildManyToOne().getPropertyTwo());

            recebido.getChildOneToMany()
                    .forEach(oneToMany -> {
                        System.out.println("oneToMany.getId() = " + oneToMany.getId());
                        System.out.println("oneToMany.getPropertyOne() = " + oneToMany.getPropertyOne());
                        System.out.println("oneToMany.getPropertyTwo() = " + oneToMany.getPropertyTwo());
                    });

            recebido.getChildManyToMany()
                    .forEach(manyToMany -> {
                        System.out.println("manyToMany.getId() = " + manyToMany.getId());
                        System.out.println("manyToMany.getPropertyOne() = " + manyToMany.getPropertyOne());
                        System.out.println("manyToMany.getPropertyTwo() = " + manyToMany.getPropertyTwo());
                    });
        });
    }

    @Test
    void testQueryLimit() {
        Pageable page = Pageable.ofSize(10);
        Page<BaseEntityLazy> encontrados = repository.findWithLimit(page);

        assertThat(encontrados.getContent().size()).isEqualTo(page.getPageSize());
    }
}
