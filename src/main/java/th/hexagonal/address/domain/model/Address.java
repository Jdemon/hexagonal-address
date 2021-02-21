package th.hexagonal.address.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Address implements Serializable {
    private String localization;
    private int postalCode;
    private List<KeyValue> subDistricts= new ArrayList<>();
    private List<KeyValue> districts = new ArrayList<>();
    private List<KeyValue> provinces = new ArrayList<>();

    public void addDistricts(List<KeyValue> districts) {
        this.districts.addAll(districts);
    }

    public void addProvinces(List<KeyValue> provinces) {
        this.provinces.addAll(provinces);
    }
}
