package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-15 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffInfo {

    private int userId;

    private int statusId;

    private int teamId;

    private String staffPic;

    private String staffName;

    private String staffSex;

    private String staffPhone;

    private String staffQq;

    private String staffEmail;

    private String staffIdnum;

    private String staffNaplace;

    private String staffNation;

    private String staffBirplace;

    private int staffAge;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date staffBirthday;

    private String staffResidence;

    private String staffEdu;

    private String staffJob;

}
