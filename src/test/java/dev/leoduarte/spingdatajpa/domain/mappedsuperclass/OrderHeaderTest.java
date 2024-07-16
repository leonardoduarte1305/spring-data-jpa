package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(1L);

        Assertions.assertThat(orderHeader1).isEqualTo(orderHeader2);
    }

    @Test
    void testNotEquals() {
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(2L);

        Assertions.assertThat(orderHeader1).isNotEqualTo(orderHeader2);
    }

}
