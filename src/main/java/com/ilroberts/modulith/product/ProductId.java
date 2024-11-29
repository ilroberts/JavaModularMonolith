package com.ilroberts.modulith.product;

import lombok.Value;
import org.jmolecules.ddd.types.ValueObject;
import org.jmolecules.ddd.types.Identifier;

@Value(staticConstructor = "of")
public class ProductId implements Identifier, ValueObject {

    String number;

    private ProductId(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
