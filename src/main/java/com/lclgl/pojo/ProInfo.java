package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-15 23:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProInfo {

    private String proName;

    private int proId;

    private int cusId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date proCreate;

    private String proCondition;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date proEndtime;

}
