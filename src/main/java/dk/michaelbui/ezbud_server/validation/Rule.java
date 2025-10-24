package dk.michaelbui.ezbud_server.validation;

import dk.michaelbui.ezbud_server.validation.fieldvalidators.FieldValidator;

import java.util.function.Function;
import java.util.function.Supplier;

public class Rule<TEntity, TField> {
    private final String fieldName;
    private final Function<TEntity, TField> field;
    private final FieldValidator fieldValidator;

    public Rule(String fieldName, Function<TEntity, TField> field, FieldValidator fieldValidator) {
        this.fieldName = fieldName;
        this.field = field;
        this.fieldValidator = fieldValidator;
    }

    public Function<TEntity, TField> getField() {
        return field;
    }

    public FieldValidator getFieldValidator() {
        return fieldValidator;
    }

    public String getFieldName() {
        return fieldName;
    }
}


