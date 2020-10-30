package io.swagger.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)
 */
@Data
@ApiModel(description = "Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-08T11:42:54.708Z")

public class Error {
    @ApiModelProperty(required = true, value = "Application relevant detail, defined in the API or a common list.")
    @NotNull
    @JsonProperty("code")
    private String code;

    @ApiModelProperty(required = true, value = "Explanation of the reason for the error which can be shown to a client user.")
    @NotNull
    @JsonProperty("reason")
    private String reason;

    @ApiModelProperty(value = "More details and corrective actions related to the error which can be shown to a client user.")
    @JsonProperty("message")
    private String message;

    @ApiModelProperty(value = "HTTP Error code extension")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(value = "URI of documentation describing the error.")
    @JsonProperty("referenceError")
    private String referenceError;

    @ApiModelProperty(value = "When sub-classing, this defines the super-class.")
    @JsonProperty("@baseType")
    private String baseType;

    @ApiModelProperty(value = "A URI to a JSON-Schema file that defines additional attributes and relationships")
    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @ApiModelProperty(value = "When sub-classing, this defines the sub-class entity name.")
    @JsonProperty("@type")
    private String type;

    public Error code(String code) {
        this.code = code;
        return this;
    }

    public Error reason(String reason) {
        this.reason = reason;
        return this;
    }

    public Error message(String message) {
        this.message = message;
        return this;
    }

    public Error status(String status) {
        this.status = status;
        return this;
    }

    public Error referenceError(String referenceError) {
        this.referenceError = referenceError;
        return this;
    }

    public Error baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    public Error schemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
    }

    public Error type(String type) {
        this.type = type;
        return this;
    }


    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

