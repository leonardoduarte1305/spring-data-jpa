package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.OrderHeader;
import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.OrderLine;
import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.repository.OrderHeaderRepository;
import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.repository.OrderLineRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository repository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Test
    @Disabled("Devido ao id null")
    void testSaveOrderLine() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("Customer");
        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(10);

        orderHeader.setOrderLines(Set.of(orderLine));
        orderLine.setOrderHeader(orderHeader);
        OrderHeader savedOrder = repository.save(orderHeader);


        assertThat(repository.findById(savedOrder.getId())).isNotNull();
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(1, savedOrder.getOrderLines().size());
    }

    @Test
    @Disabled("Devido ao id null")
    void testSaveOrderHeader() {
        OrderHeader orderHeader = new OrderHeader();
        String customerName = "New Customer";
        orderHeader.setCustomer(customerName);
        OrderHeader savedOrder = repository.save(orderHeader);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();

        OrderHeader referenceById = repository.getReferenceById(savedOrder.getId());

        assertThat(referenceById).isNotNull();
        assertThat(referenceById.getId()).isNotNull();
        assertThat(referenceById.getCustomer()).isEqualTo(customerName);
    }

}
