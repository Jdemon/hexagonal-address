package th.hexagonal.address.domain.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import th.hexagonal.address.domain.model.Address;
import th.hexagonal.address.domain.model.JsonAddress;
import th.hexagonal.address.domain.model.KeyValue;
import th.hexagonal.address.domain.port.ILoadAddressPort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    ILoadAddressPort loadAddressPort;

    @InjectMocks
    AddressService service;

    private void initDataProvince() throws JsonProcessingException {
        JsonAddress jsonProvince = new ObjectMapper().readValue("{\"no\":1,\"key\":\"10\",\"translations\":{\"th\":{\"name\":\"กรุงเทพมหานคร\"},\"en\":{\"name\":\"Bangkok\"}}}", JsonAddress.class);
        when(loadAddressPort.loadProvinces(eq("10"))).thenReturn(Collections.singletonList(jsonProvince));
    }
    private void initDataDistrict() throws JsonProcessingException {
        JsonAddress jsonDistrict = new ObjectMapper().readValue("{\"no\":1,\"key\":\"1001\",\"provinceKey\":\"10\",\"translations\":{\"th\":{\"name\":\"พระนคร\"},\"en\":{\"name\":\"Phra Nakhon\"}}}", JsonAddress.class);
        when(loadAddressPort.loadDistricts(eq("1001"))).thenReturn(Collections.singletonList(jsonDistrict));
    }

    private void initDataSubDistrict() throws JsonProcessingException {
        JsonAddress jsonSubDistrict = new ObjectMapper().readValue("{\"no\":1,\"key\":\"100101\",\"provinceKey\":\"10\",\"districtKey\":\"1001\",\"postalCodes\":[10200],\"translations\":{\"th\":{\"name\":\"พระบรมมหาราชวัง\"},\"en\":{\"name\":\"Phra Borom Maha Ratchawang\"}}}", JsonAddress.class);
        when(loadAddressPort.loadSubDistricts(eq("100101"))).thenReturn(Collections.singletonList(jsonSubDistrict));
    }

    private void initDataAddress() throws JsonProcessingException {
        initDataProvince();
        initDataDistrict();
        JsonAddress jsonSubDistrictAddress = new ObjectMapper().readValue("{\"no\":1,\"key\":\"100101\",\"provinceKey\":\"10\",\"districtKey\":\"1001\",\"postalCodes\":[10200],\"translations\":{\"th\":{\"name\":\"พระบรมมหาราชวัง\"},\"en\":{\"name\":\"Phra Borom Maha Ratchawang\"}}}", JsonAddress.class);
        when(loadAddressPort.loadSubDistricts(any(), eq(10200), any(), any())).thenReturn(Collections.singletonList(jsonSubDistrictAddress));
    }

    @Test
    public void whenGetAddress_ShouldReturnAddressesData() throws JsonProcessingException {

        // Arrange
        initDataAddress();

        // Act
        Address address = service.getAddress(10200, null, null, null, "th");

        // Assert
        assertNotNull(address);
        assertEquals(10200,address.getPostalCode());
        assertEquals("th", address.getLocalization());

        List<KeyValue> keyValueProvinceThList = address.getProvinces();

        assertNotNull(keyValueProvinceThList);
        KeyValue keyValueProvinceTh = keyValueProvinceThList.get(0);
        assertEquals("10", keyValueProvinceTh.getKey());
        assertEquals("กรุงเทพมหานคร", keyValueProvinceTh.getValue());

        List<KeyValue> keyValueDistrictThList = address.getDistricts();

        assertNotNull(keyValueDistrictThList);
        KeyValue keyValueDistrictTh = keyValueDistrictThList.get(0);
        assertEquals("1001", keyValueDistrictTh.getKey());
        assertEquals("พระนคร", keyValueDistrictTh.getValue());

        List<KeyValue> keyValueSubDistrictThList = address.getSubDistricts();

        assertNotNull(keyValueSubDistrictThList);
        KeyValue keyValueSubDistrictTh = keyValueSubDistrictThList.get(0);
        assertEquals("100101", keyValueSubDistrictTh.getKey());
        assertEquals("พระบรมมหาราชวัง", keyValueSubDistrictTh.getValue());

    }

    @Test
    public void whenGetProvinces_ShouldReturnProvincesData() throws JsonProcessingException {
        // Arrange
        initDataProvince();

        // Act
        List<KeyValue> keyValueThList = service.getProvinces("10", "th");
        List<KeyValue> keyValueEnList = service.getProvinces("10", "en");

        // Assert
        assertNotNull(keyValueThList);
        KeyValue keyValueTh = keyValueThList.get(0);
        assertEquals("10", keyValueTh.getKey());
        assertEquals("กรุงเทพมหานคร", keyValueTh.getValue());

        assertNotNull(keyValueEnList);
        KeyValue keyValueEn = keyValueEnList.get(0);
        assertEquals("10", keyValueEn.getKey());
        assertEquals("Bangkok", keyValueEn.getValue());
    }

    @Test
    public void whenGetDistricts_ShouldReturnDistrictsData() throws JsonProcessingException {
        // Arrange
        initDataDistrict();

        // Act
        List<KeyValue> keyValueThList = service.getDistricts("1001", "th");
        List<KeyValue> keyValueEnList = service.getDistricts("1001", "en");

        // Assert
        assertNotNull(keyValueThList);
        KeyValue keyValueTh = keyValueThList.get(0);
        assertEquals("1001", keyValueTh.getKey());
        assertEquals("พระนคร", keyValueTh.getValue());

        assertNotNull(keyValueEnList);
        KeyValue keyValueEn = keyValueEnList.get(0);
        assertEquals("1001", keyValueEn.getKey());
        assertEquals("Phra Nakhon", keyValueEn.getValue());
    }

    @Test
    public void whenGetSubDistricts_ShouldReturnSubDistrictsData() throws JsonProcessingException {
        // Arrange
        initDataSubDistrict();

        // Act
        List<KeyValue> keyValueThList = service.getSubDistricts("100101", "th");
        List<KeyValue> keyValueEnList = service.getSubDistricts("100101", "en");

        // Assert
        assertNotNull(keyValueThList);
        KeyValue keyValueTh = keyValueThList.get(0);
        assertEquals("100101", keyValueTh.getKey());
        assertEquals("พระบรมมหาราชวัง", keyValueTh.getValue());

        assertNotNull(keyValueEnList);
        KeyValue keyValueEn = keyValueEnList.get(0);
        assertEquals("100101", keyValueEn.getKey());
        assertEquals("Phra Borom Maha Ratchawang", keyValueEn.getValue());
    }

}
