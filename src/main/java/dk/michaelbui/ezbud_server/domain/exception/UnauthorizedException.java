package dk.michaelbui.ezbud_server.domain.exception;

import org.slf4j.Logger;

public class UnauthorizedException extends DomainException {
    private Class<?> resourceClass;
    private String resourceId;

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message, Throwable cause, Class<?> resourceClass, String resourceId) {
        super(message, cause);
        this.resourceClass = resourceClass;
        this.resourceId = resourceId;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(Class<?> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void logException(Logger log) {
        StringBuilder message = new StringBuilder("Unauthorized access. {");
        if (resourceId != null) {
            message.append("resourceId=").append(resourceId).append(";");
        }

        if (resourceClass != null) {
            message.append("resourceClass=").append(resourceClass.getSimpleName()).append(";");
        }

        if (log.isErrorEnabled()) {
            log.error(message.toString(), this);
        }
    }
}
