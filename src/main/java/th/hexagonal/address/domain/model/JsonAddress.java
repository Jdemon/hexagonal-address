package th.hexagonal.address.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonAddress implements Serializable {
    private Integer no;
    private String key;
    private String provinceKey;
    private String districtKey;
    private ObjectNode translations;
    private Integer[] postalCodes;
}
