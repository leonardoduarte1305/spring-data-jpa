package dev.leoduarte.spingdatajpa.lazyoperations.repository;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy;
import dev.leoduarte.spingdatajpa.lazyoperations.filter.BaseEntityLazyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseEntityLazyCustomRepository {

    List<BaseEntityLazy> findByFiltro(BaseEntityLazyDto filtro);

    List<BaseEntityLazy> findByFiltroPaged(BaseEntityLazyDto filtro, Pageable pageable);
}
