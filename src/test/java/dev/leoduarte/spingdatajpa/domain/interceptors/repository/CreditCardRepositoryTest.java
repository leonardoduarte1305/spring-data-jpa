package dev.leoduarte.spingdatajpa.domain.interceptors.repository;

import dev.leoduarte.spingdatajpa.domain.interceptors.domain.CreditCard;
import dev.leoduarte.spingdatajpa.domain.interceptors.service.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static java.time.LocalDate.now;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    final String CREDIT_CARD_NUMBER = "1234-4567-7897-1234";

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CreditCardRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSaveAndStoreCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCvv(randomNumeric(3));
        creditCard.setCcNumber(CREDIT_CARD_NUMBER);
        creditCard.setExpirationDate(now().plusYears(5));

        System.out.println("creditCard.getCcNumber() = " + creditCard.getCcNumber());
        System.out.println("encryptionService.encrypt(creditCard.getCcNumber()) = " + encryptionService.encrypt(creditCard.getCcNumber()));

        CreditCard saved = repository.saveAndFlush(creditCard);
        System.out.println("saved.getCcNumber() = " + saved.getCcNumber());

        // Do not use this aproach without bind parameters
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id_credit_card = " + creditCard.getId());
        String dbCardNumber = (String) dbRow.get("cc_number");
        System.out.println("dbCardNumber = " + dbCardNumber);

        assertThat(saved.getCcNumber()).isNotEqualTo(dbCardNumber);
        assertThat(dbCardNumber).isEqualTo(encryptionService.encrypt(CREDIT_CARD_NUMBER));

        CreditCard fetched = repository.findById(saved.getId()).get();
        System.out.println("fetched.getCcNumber() = " + fetched.getCcNumber());

        assertThat(saved.getId()).isEqualTo(fetched.getId());
    }

    @Test
    void testEncrypting() {
        System.out.println("CREDIT_CARD_NUMBER = " + CREDIT_CARD_NUMBER);

        String encrypt = encryptionService.encrypt(CREDIT_CARD_NUMBER);
        System.out.println("encrypt = " + encrypt);

        String decrypt = encryptionService.decrypt(encrypt);
        System.out.println("decrypt = " + decrypt);
    }
}
