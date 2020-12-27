package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-15 22:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date salaryTime;

    private int userId;

    private int salaryDays;

    private double salaryBonus;

    private double salaryAll;

    private double salaryTax;

    private double salaryPersonal;

    private double salaryPractical;

}
