package dev.leoduarte.spingdatajpa.bootstrap;

import dev.leoduarte.spingdatajpa.domain.version.VersionedEntityOptimisticLock;
import dev.leoduarte.spingdatajpa.domain.version.repository.VersionedEntityOptimisticLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OptimisticLockTest implements CommandLineRunner {

    @Autowired
    private VersionedEntityOptimisticLockRepository repository;

    @Override
    public void run(String... args) {
        try {
            VersionedEntityOptimisticLock savedEntity = repository.save(new VersionedEntityOptimisticLock("Original property"));
            savedEntity.setPropertyOne("Test version");
            log.error("savedEntity.getVersion() = {}", savedEntity.getVersion());

            savedEntity.setPropertyOne("Set second description");
            repository.save(savedEntity);
            log.error("savedEntity.getVersion() = {}", savedEntity.getVersion());

            savedEntity.setPropertyOne("Set third description");
            repository.save(savedEntity);
            log.error("savedEntity.getVersion() = {}", savedEntity.getVersion());
        } catch (StaleObjectStateException | ObjectOptimisticLockingFailureException e) {
            log.error("The StaleObjectStateException was thrown because every repository call run inside an implicit transaction and");
            log.error("the next repository call didn't use the updated entity, with the updated version, so that is how optimistic lock works.");
        }

        VersionedEntityOptimisticLock workingSavedEntity = repository.save(new VersionedEntityOptimisticLock("Original comment"));
        workingSavedEntity.setPropertyOne("Test version");
        log.error("workingSavedEntity.getVersion() = {}", workingSavedEntity.getVersion());

        workingSavedEntity.setPropertyOne("Set second description");
        VersionedEntityOptimisticLock updated = repository.save(workingSavedEntity);
        log.error("updated.getVersion() = {}", updated.getVersion());

        updated.setPropertyOne("Set third description");
        VersionedEntityOptimisticLock updatedAgain = repository.save(updated);
        log.error("updatedAgain.getVersion() = {}", updatedAgain.getVersion());

        log.error("This time the versioning worked fine.");
    }
}
