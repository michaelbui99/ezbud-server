package dk.michaelbui.ezbud_server.domain.exception;

import org.slf4j.Logger;

public class NotFoundException extends DomainException {
    private Class<?> entityClass;
    private String entityId;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Class<?> entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotFoundException(String message, Throwable cause, Class<?> entityClass) {
        super(message, cause);
        this.entityClass = entityClass;
    }

    public void logException(Logger log) {
        StringBuilder message = new StringBuilder("Entity not found. {");
        if (entityId != null) {
            message.append("entityId=").append(entityId).append(";");
        }

        if (entityClass != null) {
            message.append("entityClass=").append(entityClass.getSimpleName()).append(";");
        }

        if (log.isErrorEnabled()) {
            log.error(message.toString(), this);
        }
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
