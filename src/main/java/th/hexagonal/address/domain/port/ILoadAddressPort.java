package th.hexagonal.address.domain.port;

import th.hexagonal.address.domain.model.JsonAddress;

import java.util.List;

public interface ILoadAddressPort {

    List<JsonAddress> loadProvinces(String key);

    List<JsonAddress> loadDistricts(String key);

    List<JsonAddress> loadSubDistricts(String key);

    List<JsonAddress> loadSubDistricts(String key, Integer postalCode, String districtKey, String provinceKey);

}
