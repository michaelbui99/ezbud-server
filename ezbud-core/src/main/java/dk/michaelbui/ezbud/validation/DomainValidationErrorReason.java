package dk.michaelbui.ezbud.validation;

public class DomainValidationErrorReason {
    private DomainValidationErrorReason() {
    }

    public static final String FIELD_IS_NULL = "Field is null";
    public static final String FIELD_IS_EMPTY = "Field is empty";
    public static final String FIELD_EXCEEDS_MAXIMUM = "Field exceeds maximum";
}
