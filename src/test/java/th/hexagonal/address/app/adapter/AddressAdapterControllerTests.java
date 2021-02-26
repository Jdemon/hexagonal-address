package th.hexagonal.address.app.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import th.hexagonal.address.domain.model.Address;
import th.hexagonal.address.domain.model.KeyValue;
import th.hexagonal.address.domain.port.IRetrieveAddressPort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressAdapterControllerTests {

    private static List<KeyValue> provincesEn = Arrays.asList(new KeyValue("10", "Bangkok"));
    private static List<KeyValue> districtsEn = Arrays.asList(new KeyValue("1021", "Bang Khun Thian"));
    private static List<KeyValue> subDistrictsEn = Arrays.asList(new KeyValue("102105", "Tha Kham"));
    private static List<KeyValue> provincesTh = Arrays.asList(new KeyValue("10", "กรุงเทพมหานคร"));
    private static List<KeyValue> districtsTh = Arrays.asList(new KeyValue("1021", "บางขุนเทียน"));
    private static List<KeyValue> subDistrictsTh = Arrays.asList(new KeyValue("102105", "ท่าข้าม"));
    private static final String EN = "en";
    private static final String TH = "th";

    @Mock
    private IRetrieveAddressPort retrieveAddressPort;


    @InjectMocks
    private AddressAdapterController controller;


    @Test
    public void whenRequestAddress_shouldReturnAddress() {

        // Arrange
        Address address = new Address();
        address.setLocalization(EN);
        address.setPostalCode(10150);
        address.setProvinces(provincesEn);
        address.setDistricts(districtsEn);
        address.setSubDistricts(subDistrictsEn);
        when(retrieveAddressPort.getAddress(eq(10150), anyString(), anyString(), anyString(), eq(EN))).thenReturn(address);

        // Act
        Address response = controller.getAddress(10150, "102105", "1021", "10", EN);


        //Assert
        assertEquals(10150, response.getPostalCode());
        assertEquals(EN, response.getLocalization());
        assertNotNull(response.getProvinces());
        assertNotNull(response.getDistricts());
        assertNotNull(response.getSubDistricts());
        assertFirstList(response.getProvinces(), "10", "Bangkok");
        assertFirstList(response.getDistricts(), "1021", "Bang Khun Thian");
        assertFirstList(response.getSubDistricts(), "102105", "Tha Kham");
    }

    private void assertFirstList(List<KeyValue> data, String key, String value) {
        KeyValue keyVal = data.get(0);
        assertEquals(key, keyVal.getKey());
        assertEquals(value, keyVal.getValue());
    }

    @Test
    public void whenGetProvinceEn_shouldReturnProvinceEn() {
        // Arrange
        when(retrieveAddressPort.getProvinces(anyString(), eq(EN))).thenReturn(provincesEn);
        // Act & Assert
        assertFirstList(controller.getProvinces("10", EN), "10", "Bangkok");;
    }

    @Test
    public void whenGetProvinceTh_shouldReturnProvinceTh() {
        // Arrange
        when(retrieveAddressPort.getProvinces(anyString(), eq(TH))).thenReturn(provincesTh);
        // Act & Assert
        assertFirstList(controller.getProvinces("10", TH), "10", "กรุงเทพมหานคร");;
    }

    @Test
    public void whenGetDistrictEn_shouldReturnDistrictEn() {
        // Arrange
        when(retrieveAddressPort.getDistricts(anyString(), eq(EN))).thenReturn(districtsEn);
        // Act & Assert
        assertFirstList(controller.getDistrict("1021", EN), "1021", "Bang Khun Thian");;
    }

    @Test
    public void whenGetDistrictTh_shouldReturnDistrictTh() {
        // Arrange
        when(retrieveAddressPort.getDistricts(anyString(), eq(TH))).thenReturn(districtsTh);
        // Act & Assert
        assertFirstList(controller.getDistrict("1021", TH), "1021", "บางขุนเทียน");;
    }

    @Test
    public void whenGetSubDistrictEn_shouldReturnSubDistrictEn() {
        // Arrange
        when(retrieveAddressPort.getSubDistricts(anyString(), eq(EN))).thenReturn(subDistrictsEn);
        // Act & Assert
        assertFirstList(controller.getSubDistricts("102105", EN), "102105", "Tha Kham");;
    }

    @Test
    public void whenGetSubDistrictTh_shouldReturnSubDistrictTh() {
        // Arrange
        when(retrieveAddressPort.getSubDistricts(anyString(), eq(TH))).thenReturn(subDistrictsTh);
        // Act & Assert
        assertFirstList(controller.getSubDistricts("102105", TH), "102105", "ท่าข้าม");;
    }
}
