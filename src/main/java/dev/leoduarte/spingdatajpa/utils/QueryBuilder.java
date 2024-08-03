package dev.leoduarte.spingdatajpa.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryBuilder {

    private static final String ORDER_BY = "ORDER BY ";
    private final StringBuilder query = new StringBuilder();
    @Getter
    private final Map<String, Object> params = new HashMap<>();

    public String getQuery() {
        return query.toString();
    }

    public void putParam(String key, Object value) {
        params.put(key, value);
    }


    // STRINGS
    public QueryBuilder addString(String value, String hql, String paramName) {
        appendQuery(value, hql, paramName);
        return this;
    }

    public QueryBuilder addStringNotEmpty(String value, String hql, String paramName) {
        if (StringUtils.isNotEmpty(value)) {
            appendQuery(value, hql, paramName);
        }
        return this;
    }

    public QueryBuilder addStringLike(String value, String hql, String paramName) {
        value = "%" + value + "%";
        appendQuery(value, hql, paramName);

        return this;
    }

    public QueryBuilder addStringLikeNotEmpty(String value, String hql, String paramName) {
        if (StringUtils.isNotEmpty(value)) {
            value = "%" + value + "%";
            appendQuery(value, hql, paramName);
        }
        return this;
    }


    // OBJECTS
    public QueryBuilder addObject(Object value, String hql, String paramName) {
        appendQuery(value, hql, paramName);

        return this;
    }

    public QueryBuilder addObjectNotNull(Object value, String hql, String paramName) {
        if (Objects.nonNull(value)) {
            appendQuery(value, hql, paramName);
        }
        return this;
    }


    // COLLECTIONS
    public QueryBuilder addCollectionNotEmpty(Collection collection, String hql, String paramName) {
        if (CollectionUtils.isNotEmpty(collection)) {
            appendQuery(collection, hql, paramName);
        }
        return this;
    }


    // APPEND
    public QueryBuilder append(String hql) {
        query.append(hql);
        return this;
    }

    public QueryBuilder append(QueryBuilder queryBuilder) {
        query.append(queryBuilder.getQuery());
        params.putAll(queryBuilder.getParams());

        return this;
    }

    private void appendQuery(Object value, String hql, String paramName) {
        query.append(hql);
        params.put(paramName, value);
    }


    // BUILD
    public <T> TypedQuery<T> build(EntityManager entityManager, Class<T> type) {
        TypedQuery<T> result = entityManager.createQuery(getQuery(), type);
        bindParameters(result);

        return result;
    }

    public <T> TypedQuery<T> build(EntityManager entityManager, Class<T> type, Pageable page) {
        this.orderBy(page.getSort());

        TypedQuery<T> result = build(entityManager, type);

        if (page.isPaged()) {
            result.setFirstResult((int) page.getOffset());
            result.setMaxResults(page.getPageSize());
        }

        return result;
    }

    public Query buildNative(EntityManager entityManager, String type) {
        Query result = entityManager.createNativeQuery(getQuery(), type);
        bindParameters(result);
        return result;
    }

    private void bindParameters(Query jpaQuery) {
        params.forEach(jpaQuery::setParameter);
    }


    // ORDER BY
    public QueryBuilder orderBy(Sort sort) {
        if (sort.isSorted()) {
            this.query.append(ORDER_BY);
            sort.forEach(order -> this.query
                    .append(order.isIgnoreCase() ? "UPPER(" : "(")
                    .append(order.getProperty())
                    .append(") ")
                    .append(order.getDirection())
                    .append(", "));
            this.query.deleteCharAt(this.query.length() - 2);
        }
        return this;
    }

    public QueryBuilder orderBy(String[] columns, boolean isDesc) {
        String order = " " + (isDesc ? "DESC" : "ASC");
        query.append(ORDER_BY).append(String.join(order + ",", columns)).append(order);

        return this;
    }

    public QueryBuilder orderByNotBlank(String column, OrderEnum orderEnum) {
        if (StringUtils.isNotBlank(column)) {
            this.orderBy(column, orderEnum);
        }

        return this;
    }

    public QueryBuilder orderBy(String column, OrderEnum orderEnum) {
        query.append(ORDER_BY).append(column).append(" ").append(orderEnum.getOrder());

        return this;
    }
}
