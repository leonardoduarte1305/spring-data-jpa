package dev.leoduarte.spingdatajpa.lazyoperations.repository;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy;
import dev.leoduarte.spingdatajpa.lazyoperations.filter.BaseEntityLazyDto;
import dev.leoduarte.spingdatajpa.utils.QueryBuilder;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BaseEntityLazyCustomRepositoryImpl implements BaseEntityLazyCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<BaseEntityLazy> findByFiltro(BaseEntityLazyDto filtro) {
        return findByFiltroPaged(filtro, Pageable.unpaged());
    }

    @Override
    public List<BaseEntityLazy> findByFiltroPaged(BaseEntityLazyDto filtro, Pageable pageable) {
        QueryBuilder builder = new QueryBuilder();

        builder
                .append("SELECT b FROM ")
                .append(BaseEntityLazy.class.getSimpleName())
                .append(" b ")
                .append(" JOIN b.childOneToOne coto ")
                .append(" JOIN b.childOneToMany cotm ")
                .append(" JOIN b.childManyToOne cmto ")
                .append(" JOIN b.childManyToMany cmtm ")
                .append("WHERE 1 = 1 ")
                .addObjectNotNull(filtro.getId(), "AND b.id = :id ", "id")
                .addStringLikeNotEmpty(filtro.getPropertyOne(), "AND b.propertyOne LIKE :propertyOne ", "propertyOne")
                .addStringLikeNotEmpty(filtro.getPropertyTwo(), "AND b.propertyTwo LIKE :propertyTwo ", "propertyTwo")
                .addObjectNotNull(filtro.getChildOneToOne(), "AND b.childOneToOne LIKE :childOneToOne ", "childOneToOne")
                .addObjectNotNull(filtro.getChildManyToOne(), "AND b.childManyToOne LIKE :childManyToOne ", "childManyToOne")
                .addCollectionNotEmpty(filtro.getChildOneToMany(), "AND b.childOneToMany IN :childOneToMany ", "childOneToMany")
                .addCollectionNotEmpty(filtro.getChildManyToMany(), "AND b.childManyToMany IN :childManyToMany ", "childManyToMany")
        ;

        return builder.build(entityManager, BaseEntityLazy.class, pageable).getResultList();
    }
}
