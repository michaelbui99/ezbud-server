package dk.michaelbui.ezbud.validation;

public class DomainValidationError<T> {
    private Class<T> validatedClass;
    private String field;
    private String reason;

    public DomainValidationError() {
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Class<T> getValidatedClass() {
        return validatedClass;
    }

    public void setValidatedClass(Class<T> validatedClass) {
        this.validatedClass = validatedClass;
    }

    @Override
    public String toString() {
        return "DomainValidationError{" +
                "validatedClass=" + validatedClass +
                ", field='" + field + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
