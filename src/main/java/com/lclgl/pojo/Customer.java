package com.lclgl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cksh
 * @create 2020-12-15 23:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int cusId;

    private String cusName;

    private String cusSex;

    private String cusCompany;

    private String cusLevel;

    private String cusPhone;

    private String cusEmail;

}
