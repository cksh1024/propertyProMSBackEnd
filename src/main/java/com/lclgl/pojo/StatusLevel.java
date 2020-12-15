package com.lclgl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author cksh
 * @create 2020-12-15 22:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusLevel {

    private int statusId;

    private String statusType;

    private BigDecimal statusSalary;

}
