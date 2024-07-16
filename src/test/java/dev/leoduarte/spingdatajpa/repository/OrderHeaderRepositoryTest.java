package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.OrderHeader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository repository;

    @Test
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
