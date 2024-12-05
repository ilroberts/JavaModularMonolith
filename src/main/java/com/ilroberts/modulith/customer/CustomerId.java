package com.ilroberts.modulith.customer;

import org.jmolecules.ddd.types.Identifier;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record CustomerId(Long id) implements Identifier, Serializable {

    public static CustomerId of(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("CustomerId must be a positive non-null value.");
        }
        return new CustomerId(id);
    }
}