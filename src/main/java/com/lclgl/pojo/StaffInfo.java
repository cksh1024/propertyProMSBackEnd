package com.lclgl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private int staffQq;

    private String staffEmail;

    private String staffIdnum;

    private String staffNaplace;

    private String staffNation;

    private String staffBirplace;

    private String staffResidence;

    private String staffEdu;

    private String staffJob;

}
