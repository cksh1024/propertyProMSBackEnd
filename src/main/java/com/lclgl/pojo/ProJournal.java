package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-15 23:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProJournal {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jourTime;

    private int porId;

    private String jourType;

    private int userId;

    private String jourRemark;

    private String fileName;

}
