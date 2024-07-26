package dev.leoduarte.spingdatajpa.domain.mappedsuperclass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    WAITING("Waiting"),
    IN_PROCESS("In Process"),
    DONE("Done");

    private final String description;
}
