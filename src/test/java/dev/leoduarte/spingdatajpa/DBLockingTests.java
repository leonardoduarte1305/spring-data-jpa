package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy;
import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildOneToOne;
import dev.leoduarte.spingdatajpa.lazyoperations.repository.BaseEntityLazyRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class DBLockingTests {

    @Autowired
    private BaseEntityLazyRepository repository;

    @Test
    @Transactional
    void testDBLock() {
        /*
            1 - First Open MysqlWorkbench and connect to the database8
            2 - Enable the Commit and Rollback buttons
            3 - Use the query: SELECT * FROM bookdb.base_entity_lazy WHERE id_base_entity_lazy = 10 FOR UPDATE;
            4 - Run this test
            Now the Database Lock is active, you must click Commit or Rollback to free the test to finish
        */

        Long id = 10L;
        BaseEntityLazy baseEntityLazy = repository.findById(id).get();

        ChildOneToOne newOneToOne = new ChildOneToOne();
        newOneToOne.setPropertyOne("Abracadabra");
        newOneToOne.setPropertyTwo("Property2");

        baseEntityLazy.setChildOneToOne(newOneToOne);
        repository.saveAndFlush(baseEntityLazy);

        System.out.println("I updated the base entity.");
    }
}
