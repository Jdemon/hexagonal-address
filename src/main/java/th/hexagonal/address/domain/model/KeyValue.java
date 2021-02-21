package th.hexagonal.address.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
public class KeyValue implements Serializable {
    private String key;
    private String value;
}
