package dev.leoduarte.spingdatajpa.domain.version;

import dev.leoduarte.spingdatajpa.repository.VersionedEntityOptimisticLockRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class VersionedEntityOptimisticLockTest {

    @Autowired
    private VersionedEntityOptimisticLockRepository repository;

    @Test
    @Commit
    @Order(1)
    void testVersionEntity() {
        VersionedEntityOptimisticLock entity1 = new VersionedEntityOptimisticLock("Property 1 v1");
        VersionedEntityOptimisticLock savedEntity = repository.save(entity1);

        System.out.println("savedEntity.getVersion() = " + savedEntity.getVersion());
    }

    @Test
    @Commit
    @Order(2)
    void testVersioningWorksCorrectly() {
        VersionedEntityOptimisticLock foundEntity = repository.findAll().stream().findFirst().get();
        foundEntity.setPropertyOne("Was the version updated?");

        VersionedEntityOptimisticLock updatedVersionEntity = repository.save(foundEntity);
        System.out.println("updatedVersionEntity.getVersion() = " + updatedVersionEntity.getVersion());


        VersionedEntityOptimisticLock alreadyUpdatedEntity = repository.findAll().stream().findFirst().get();
        alreadyUpdatedEntity.setPropertyOne("Was version updated again??");
        VersionedEntityOptimisticLock thirdTimeUpdated = repository.save(alreadyUpdatedEntity);

        System.out.println("thirdTimeUpdated.getVersion() = " + thirdTimeUpdated.getVersion());
    }

}
