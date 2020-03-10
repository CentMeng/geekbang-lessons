package com.msj.di;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2018 孟少杰 All Rights Reserved
 * @desc
 */
public class Address {

    private String province;

    private String city;

    public Address(){
        this.province = "内蒙古";
        this.city = "包头";
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
