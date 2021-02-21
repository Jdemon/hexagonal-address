package th.hexagonal.address.domain.port;

import th.hexagonal.address.domain.model.Address;
import th.hexagonal.address.domain.model.KeyValue;

import java.util.List;

public interface IRetrieveAddressPort {

    Address getAddress(Integer postalCode, String subDistrictKey, String districtKey, String provinceKey, String localization);

    List<KeyValue> getProvinces(String key, String localization);

    List<KeyValue> getDistricts(String key, String localization);

    List<KeyValue> getSubDistricts(String key, String localization);
}
