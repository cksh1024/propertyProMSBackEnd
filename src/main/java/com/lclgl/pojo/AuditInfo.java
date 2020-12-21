package com.lclgl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cksh
 * @create 2020-12-16 22:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditInfo {

    private int auditId;

    private StaffInfo staffInfo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commitDate;

    private String auditFile;

    private String auditStatus;

    private ProInfo pro;

    private String suggestion;

}
