package dev.leoduarte.spingdatajpa.lazyoperations.domain;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import dev.leoduarte.spingdatajpa.lazyoperations.repository.EntityWithDataAnnotationMessRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.apache.commons.text.StringEscapeUtils.unescapeJava;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class EntityWithDataAnnotationMessTest {

    private final Logger hibernateLogger = (Logger) LoggerFactory.getLogger("org.hibernate.SQL");
    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @Autowired
    private EntityWithDataAnnotationMessRepository repository;

    @BeforeEach
    void setUp() {
        hibernateLogger.setLevel(Level.DEBUG);
        hibernateLogger.addAppender(listAppender);
        listAppender.start();
    }

    @AfterEach
    void tearDown() {
        hibernateLogger.detachAppender(listAppender);
    }

    @Test
    @Order(1)
    @Commit
    void testSavingAndCommitingAnEntity() {
        ChildOneToOne childOneToOne = ChildOneToOne.builder().propertyOne("Prop 01").propertyTwo("Prop 02").build();
        ChildManyToOne childManyToOne = ChildManyToOne.builder().propertyOne("Prop 01").propertyTwo("Prop 02").build();
        EntityWithDataAnnotationMess toSave = new EntityWithDataAnnotationMess(null, "Property One Example", "Property Two Example", childOneToOne, childManyToOne);
        repository.save(toSave);
    }

    @Test
    @Order(2)
    void testsTheManyCallsToDatabaseWhenPrintingAnElement() {
        EntityWithDataAnnotationMess found = repository.findAll().stream().findAny().get();

        System.out.println("Breakpoint this line and uncomment the next one.");
        System.out.println("You can change from @Data to @Getter and @Setter to see the ifference as well.");
//        System.out.println("found = " + found);

        assertThat(listAppender.list)
                .extracting(event -> unescapeJava(event.getFormattedMessage()).replaceAll("\\s+", " ").trim())
                .containsExactly(message01(), message02(), message03());
    }

    private String message01() {
        return "select ewdam1_0.id_entity_with_data_annotation_mess, ewdam1_0.mess_child_many_to_one, ewdam1_0.mess_child_one_to_one, ewdam1_0.property_one, ewdam1_0.property_two from entity_with_data_annotation_mess ewdam1_0";
    }

    private String message02() {
        return "select coto1_0.id_child_one_to_one_table, coto1_0.property_one, coto1_0.property_two from child_one_to_one_table coto1_0 where coto1_0.id_child_one_to_one_table=?";
    }

    private String message03() {
        return "select cmto1_0.id_child_many_to_one_table, cmto1_0.property_one, cmto1_0.property_two from child_many_to_one_table cmto1_0 where cmto1_0.id_child_many_to_one_table=?";
    }
}
