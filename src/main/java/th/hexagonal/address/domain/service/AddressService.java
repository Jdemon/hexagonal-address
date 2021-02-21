package th.hexagonal.address.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import th.hexagonal.address.domain.model.Address;
import th.hexagonal.address.domain.model.JsonAddress;
import th.hexagonal.address.domain.model.KeyValue;
import th.hexagonal.address.domain.port.ILoadAddressPort;
import th.hexagonal.address.domain.port.IRetrieveAddressPort;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AddressService implements IRetrieveAddressPort {

    private final ILoadAddressPort loadAddressPort;

    public AddressService(ILoadAddressPort loadAddressPort) {
        this.loadAddressPort = loadAddressPort;
    }

    public Address getAddress(Integer postalCode, String subDistrictKey, String districtKey, String provinceKey, String localization) {
        Address address = new Address();
        address.setLocalization(localization);
        address.setPostalCode(postalCode);
        List<JsonAddress> subDistricts = loadAddressPort.loadSubDistricts(subDistrictKey, postalCode, districtKey, provinceKey);
        address.setSubDistricts(convert(subDistricts, localization));
        List<String> districtKeys = new ArrayList<>();
        List<String> provincesKeys = new ArrayList<>();
        for (JsonAddress subDistrict : subDistricts) {
            //add districts
            if (!districtKeys.contains(subDistrict.getDistrictKey())) {
                List<JsonAddress> districts = loadAddressPort.loadDistricts(subDistrict.getDistrictKey());
                address.addDistricts(convert(districts, localization));
                districtKeys.add(subDistrict.getDistrictKey());
            }
            //add provinces
            if (!provincesKeys.contains(subDistrict.getProvinceKey())) {
                List<JsonAddress> provinces = loadAddressPort.loadProvinces(subDistrict.getProvinceKey());
                address.addProvinces(convert(provinces, localization));
                provincesKeys.add(subDistrict.getProvinceKey());
            }
        }

        return address;
    }

    public List<KeyValue> getProvinces(String key, String localization) {
        return convert(loadAddressPort.loadProvinces(key), localization);
    }

    public List<KeyValue> getDistricts(String key, String localization) {
        return convert(loadAddressPort.loadDistricts(key), localization);
    }

    public List<KeyValue> getSubDistricts(String key, String localization) {
        return convert(loadAddressPort.loadSubDistricts(key), localization);
    }

    private List<KeyValue> convert(List<JsonAddress> dataList, String localization) {
        List<KeyValue> results = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            for (JsonAddress data : dataList) {
                if(!data.getKey().isEmpty()) results.add(new KeyValue(data.getKey(), data.getTranslations().get(localization).get("name").asText()));
            }
        }
        return results;
    }

}
