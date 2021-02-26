package th.hexagonal.address.external.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import th.hexagonal.address.domain.model.JsonAddress;
import th.hexagonal.address.external.client.IAddressClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressExternalServiceTests {

    @Mock
    IAddressClient addressClient;

    @InjectMocks
    AddressExternalService addressExternalService;

    private void initDataProvince() throws JsonProcessingException {
        JsonAddress jsonProvince = new ObjectMapper().readValue("{\"no\":1,\"key\":\"10\",\"translations\":{\"th\":{\"name\":\"กรุงเทพมหานคร\"},\"en\":{\"name\":\"Bangkok\"}}}", JsonAddress.class);
        when(addressClient.getProvinces(eq("10"))).thenReturn(Collections.singletonList(jsonProvince));
    }

    private void initDataDistrict() throws JsonProcessingException {
        JsonAddress jsonDistrict = new ObjectMapper().readValue("{\"no\":1,\"key\":\"1001\",\"provinceKey\":\"10\",\"translations\":{\"th\":{\"name\":\"พระนคร\"},\"en\":{\"name\":\"Phra Nakhon\"}}}", JsonAddress.class);
        when(addressClient.getDistricts(eq("1001"), any())).thenReturn(Collections.singletonList(jsonDistrict));
    }

    private void initDataSubDistrict() throws JsonProcessingException {
        JsonAddress jsonSubDistrict = new ObjectMapper().readValue("{\"no\":1,\"key\":\"100101\",\"provinceKey\":\"10\",\"districtKey\":\"1001\",\"postalCodes\":[10200],\"translations\":{\"th\":{\"name\":\"พระบรมมหาราชวัง\"},\"en\":{\"name\":\"Phra Borom Maha Ratchawang\"}}}", JsonAddress.class);
        JsonAddress jsonSubDistrictCriteria = new ObjectMapper().readValue("{\"no\":2,\"key\":\"100102\",\"provinceKey\":\"10\",\"districtKey\":\"1001\",\"postalCodes\":[10200],\"translations\":{\"th\":{\"name\":\"วังบูรพาภิรมย์\"},\"en\":{\"name\":\"Wang Burapha Phirom\"}}}", JsonAddress.class);
        when(addressClient.getSubDistricts(eq("100101"), any(), any(), any())).thenReturn(Collections.singletonList(jsonSubDistrict));
        when(addressClient.getSubDistricts(eq("100102"), eq("10"), eq("1001"), eq(10200))).thenReturn(Collections.singletonList(jsonSubDistrictCriteria));
    }

    @Test
    public void whenCallGetProvincesByKey_shouldReceiveArrayProvince() throws JsonProcessingException {
        // Arrange
        initDataProvince();

        // Act
        List<JsonAddress> results = addressExternalService.loadProvinces("10");

        // Assert
        assertNotNull(results);
        JsonAddress jsonAddress = results.get(0);
        assertEquals("10", jsonAddress.getKey());
        assertEquals("กรุงเทพมหานคร", jsonAddress.getTranslations().get("th").get("name").asText());
        assertEquals("Bangkok", jsonAddress.getTranslations().get("en").get("name").asText());
        assertEquals(1, jsonAddress.getNo());

    }

    @Test
    public void whenCallGetDistrictsByKey_shouldReceiveArrayDistricts() throws JsonProcessingException {
        // Arrange
        initDataDistrict();

        // Act
        List<JsonAddress> results = addressExternalService.loadDistricts("1001");

        // Assert
        assertNotNull(results);
        JsonAddress jsonAddress = results.get(0);
        assertEquals("1001", jsonAddress.getKey());
        assertEquals("10", jsonAddress.getProvinceKey());
        assertEquals("พระนคร", jsonAddress.getTranslations().get("th").get("name").asText());
        assertEquals("Phra Nakhon", jsonAddress.getTranslations().get("en").get("name").asText());
        assertEquals(1, jsonAddress.getNo());
    }

    @Test
    public void whenCallGetSubDistrictsByKey_shouldReceiveArraySubDistricts() throws JsonProcessingException {
        // Arrange
        initDataSubDistrict();

        // Act
        List<JsonAddress> resultsByKey = addressExternalService.loadSubDistricts("100101");
        List<JsonAddress> resultsByCriteria = addressExternalService.loadSubDistricts("100102", 10200, "1001", "10");

        // Assert
        assertNotNull(resultsByKey);
        JsonAddress jsonAddress = resultsByKey.get(0);
        assertEquals("100101", jsonAddress.getKey());
        assertEquals("1001", jsonAddress.getDistrictKey());
        assertEquals("10", jsonAddress.getProvinceKey());
        assertEquals("พระบรมมหาราชวัง", jsonAddress.getTranslations().get("th").get("name").asText());
        assertEquals("Phra Borom Maha Ratchawang", jsonAddress.getTranslations().get("en").get("name").asText());
        assertEquals(10200, jsonAddress.getPostalCodes()[0]);
        assertEquals(1, jsonAddress.getNo());

        assertNotNull(resultsByCriteria);
        JsonAddress jsonAddressCriteria = resultsByCriteria.get(0);
        assertEquals("100102", jsonAddressCriteria.getKey());
        assertEquals("1001", jsonAddressCriteria.getDistrictKey());
        assertEquals("10", jsonAddressCriteria.getProvinceKey());
        assertEquals("วังบูรพาภิรมย์", jsonAddressCriteria.getTranslations().get("th").get("name").asText());
        assertEquals("Wang Burapha Phirom", jsonAddressCriteria.getTranslations().get("en").get("name").asText());
        assertEquals(10200, jsonAddress.getPostalCodes()[0]);
        assertEquals(2, jsonAddressCriteria.getNo());
    }

}
