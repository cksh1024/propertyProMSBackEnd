package com.lclgl;

import com.lclgl.dao.AuditMapper;
import com.lclgl.pojo.AuditInfo;
import com.lclgl.pojo.ProInfo;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class PropertyProMsApplicationTests {

    @Autowired
    FileService fileService;

    @Autowired
    AuditMapper auditMapper;

    @Test
    void contextLoads() {
//        System.out.println(fileService.delete("待审核文件"));
//        fileService.moveFile("待审核文件\\模型\\作业一.doc", "xxx宾馆设计\\作业一.doc");
        List<AuditInfo> auditInfo = auditMapper.getAuditInfo(2020302);
//        System.out.println(auditInfo);
        DateFormat df2 = DateFormat.getDateTimeInstance();
        System.out.println(df2.format(auditInfo.get(0).getCommitDate()));
        System.out.println(df2.format(new Date()));
//        StaffInfo staffInfo = new StaffInfo();
//        staffInfo.setUserId(2020302);
//        ProInfo proInfo = new ProInfo();
//        proInfo.setProId(10001);
//        AuditInfo auditInfo = new AuditInfo(staffInfo, new Date(), "2.txt", "审核中", proInfo);
//        auditMapper.addAuditInfo(auditInfo);
    }

    @Test
    public void fileTest() {
//        System.out.println(new File(""));
        String oldPath = "待审核文件\\上海汤臣一品\\模型\\1. 《企业级软件系统开发实践》任务书及评分标准.docx";
        String newPath = "项目列表\\上海汤臣一品\\模型\\1. 《企业级软件系统开发实践》任务书及评分标准.docx";
        fileService.moveFile(oldPath, newPath);
    }

}
