package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    NEW("New"),
    IN_STOCK("In stock"),
    DISCONTINUED("Discontinued");

    private final String name;

}
