package th.hexagonal.address.external.adapter;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import th.hexagonal.address.domain.model.JsonAddress;
import th.hexagonal.address.domain.port.ILoadAddressPort;
import th.hexagonal.address.external.client.IAddressClient;

import java.util.List;

@Component
public class AddressExternalService implements ILoadAddressPort {

    private final IAddressClient addressClient;

    public AddressExternalService(IAddressClient addressClient) {
        this.addressClient = addressClient;
    }

    @Override
    @Cacheable(value = "provinces", key = "#key", unless = "#result==null")
    public List<JsonAddress> loadProvinces(String key) {
        return addressClient.getProvinces(key);
    }

    @Override
    @Cacheable(value = "districts", key = "#key", unless = "#result==null")
    public List<JsonAddress> loadDistricts(String key) {
        return addressClient.getDistricts(key, null);
    }

    @Override
    @Cacheable(value = "sub-districts", key = "#key", unless = "#result==null")
    public List<JsonAddress> loadSubDistricts(String key) {
        return addressClient.getSubDistricts(key, null, null, null);
    }

    @Override
    @Cacheable(value = "sub-districts-multi", key = "#key + '_' + #postalCode + '_' + #districtKey + '_' + #provinceKey", unless = "#result==null")
    public List<JsonAddress> loadSubDistricts(String key, Integer postalCode, String districtKey, String provinceKey) {
        return addressClient.getSubDistricts(key, provinceKey, districtKey, postalCode);
    }
}
