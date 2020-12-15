package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-15 23:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProStage {

    private String stageType;

    private int teamId;

    private int proId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stageStateTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stageEndtime;

    private String stageCondition;

    private BigDecimal stagePay;

}
