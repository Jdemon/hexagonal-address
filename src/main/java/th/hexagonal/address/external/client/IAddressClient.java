package th.hexagonal.address.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import th.hexagonal.address.domain.model.JsonAddress;

import java.util.List;

@FeignClient(name = "address-client", url = "${external.address.url}")
public interface IAddressClient {

    @GetMapping("/provinces")
    List<JsonAddress> getProvinces(@RequestParam(value = "key", required = false) String key);

    @GetMapping("/districts")
    List<JsonAddress> getDistricts(@RequestParam(value = "key", required = false) String key
            , @RequestParam(value = "provinceKey", required = false) String provinceKey);

    @GetMapping("/subDistricts")
    List<JsonAddress> getSubDistricts(@RequestParam(value = "key", required = false) String key
            , @RequestParam(value = "provinceKey", required = false) String provinceKey
            , @RequestParam(value = "districtKey", required = false) String districtKey
            , @RequestParam(value = "postalCodes", required = false) Integer postalCode);
}
