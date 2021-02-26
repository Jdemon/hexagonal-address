package th.hexagonal.address.app.adapter;

import org.springframework.web.bind.annotation.*;
import th.hexagonal.address.domain.model.Address;
import th.hexagonal.address.domain.model.KeyValue;
import th.hexagonal.address.domain.port.IRetrieveAddressPort;

import java.util.List;

@RestController
public class AddressAdapterController {
    private final IRetrieveAddressPort retrieveAddressPort;

    public AddressAdapterController(IRetrieveAddressPort retrieveAddressPort) {
        this.retrieveAddressPort = retrieveAddressPort;
    }

    @GetMapping("address/{localization}")
    public Address getAddress(@RequestParam Integer postalCode,
                              @RequestParam(value = "subDistrictKey",required = false) String subDistrictKey,
                              @RequestParam(value = "districtKey",required = false) String districtKey,
                              @RequestParam(value = "provinceKey",required = false) String provinceKey,
                              @PathVariable String localization) {
        return retrieveAddressPort.getAddress(postalCode, subDistrictKey, districtKey, provinceKey, localization);
    }

    @GetMapping("provinces/{localization}/{key}")
    public List<KeyValue> getProvinces(@PathVariable String key, @PathVariable String localization) {
        return retrieveAddressPort.getProvinces(key, localization);
    }

    @GetMapping("districts/{localization}/{key}")
    public List<KeyValue> getDistrict(@PathVariable String key, @PathVariable String localization) {
        return retrieveAddressPort.getDistricts(key, localization);
    }

    @GetMapping("sub-districts/{localization}/{key}")
    public List<KeyValue> getSubDistricts(@PathVariable String key, @PathVariable String localization) {
        return retrieveAddressPort.getSubDistricts(key, localization);
    }

}
