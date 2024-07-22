package com.driveit.driveit.cityzipcode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="zip_code")
public class ZipCode {

    @Id
    private String code;

    public ZipCode() {

    }

    public ZipCode(String code){this.code = code; }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
