package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cksh
 * @create 2021-01-04 23:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    private int userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date gradeTime;

    private int gradeLevel;

}
