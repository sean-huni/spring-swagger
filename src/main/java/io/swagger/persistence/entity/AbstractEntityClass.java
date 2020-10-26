package io.swagger.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Version;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@MappedSuperclass
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 100)
public abstract class AbstractEntityClass implements EntityInterface, Serializable {
    private static final long serialVersionUID = 12054834772323L;

    @ApiModelProperty(required = true, value = "Unique identifier of the organization")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @Min(value = 1, message = "Lowest value should be 1")
    private Long id;                    // (string): Unique identifier of the organization

    @Version
    @ApiModelProperty(value = "Back-System version in use")
    private String version;
    @ApiModelProperty(value = "Zoned-Date-Time of which the record was last-updated.")
    @JsonIgnore
    private ZonedDateTime lastUpdated;
    @JsonIgnore
    @ApiModelProperty(value = "Zoned-Date-Time of which the record was created.")
    private ZonedDateTime created;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = ZonedDateTime.now();
        if (created == null) {
            created = ZonedDateTime.now();
        }
    }
}
