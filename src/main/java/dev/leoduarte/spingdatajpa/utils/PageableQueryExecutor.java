package dev.leoduarte.spingdatajpa.utils;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PageableQueryExecutor {

    private final EntityManager entityManager;

    public <T> Page<T> execute(QueryBuilder builder, Class<T> type, Pageable pageable) {
        return execute(builder, type, pageable, "");
    }

    public <T> Page<T> execute(QueryBuilder builder, Class<T> type, Pageable pageable, String entidade) {
        List<T> result = builder.build(entityManager, type, pageable).getResultList();

        long total = result.size();
        if (pageable.isPaged() && total >= pageable.getPageSize()) {
            total = getTotal(builder, entidade);
        }

        return new PageImpl<>(result, pageable, total);
    }

    private long getTotal(QueryBuilder builder, String entidade) {
        QueryBuilder countBuilder = new QueryBuilder();
        countBuilder.append("SELECT COUNT(*) ");

        countBuilder.append(extractFromWhere(builder.getQuery(), entidade));

        builder.getParams().forEach((param, value) -> {
            if (countBuilder.getQuery().contains(String.format(":%s", param))) {
                countBuilder.putParam(param, value);
            }
        });

        return countBuilder.build(entityManager, Long.class).getSingleResult();
    }

    private String extractFromWhere(String query, String entidade) {
        int fromIndex = query.toUpperCase().indexOf("FROM " + entidade);
        int groupByIndex = query.toUpperCase().indexOf("GROUP BY");
        int orderByIndex = query.toUpperCase().indexOf("ORDER BY");

        if (groupByIndex > 0) {
            return query.substring(fromIndex, groupByIndex);
        }

        if (orderByIndex > 0) {
            return query.substring(fromIndex, orderByIndex);
        }

        return query.substring(fromIndex);
    }
}
