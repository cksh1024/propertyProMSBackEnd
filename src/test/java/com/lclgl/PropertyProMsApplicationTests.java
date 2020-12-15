package com.lclgl;

import com.lclgl.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertyProMsApplicationTests {

    @Autowired
    FileService fileService;

    @Test
    void contextLoads() {
        System.out.println(fileService.delete("待审核文件"));
//        fileService.moveFile("待审核文件\\模型\\作业一.doc", "xxx宾馆设计\\作业一.doc");
    }

}
