package dev.leoduarte.spingdatajpa.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
    ASC("ASC"),
    DESC("DESC");

    private final String order;
}
