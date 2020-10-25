package io.swagger.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Version;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractEntityClass implements EntityInterface, Serializable {
    private static final long serialVersionUID = 12054834772323L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                    // (string): Unique identifier of the organization

    @Version
    private Integer version;
    private LocalDateTime lastUpdated;
    private LocalDateTime created;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = LocalDateTime.now();
        if (created == null) {
            created = LocalDateTime.now();
        }
    }
}
