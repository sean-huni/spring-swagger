package io.swagger.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntityClass implements EntityInterface, Serializable {
    private static final long serialVersionUID = 12054834772323L;

    @Version
    private Integer version;
    private LocalDateTime dtUpdated;
    private LocalDateTime dtCreated;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        dtUpdated = LocalDateTime.now();
        if (dtCreated == null) {
            dtCreated = LocalDateTime.now();
        }
    }
}
