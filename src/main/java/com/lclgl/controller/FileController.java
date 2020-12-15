package com.lclgl.controller;

import com.lclgl.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lclgl")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/getFileList")
    public Map<String, Object> fileList(String path) throws IOException {
        return fileService.fileList(path);
    }

    @GetMapping("/downLoad")
    public void downLoad(String fileName, String filePath, HttpServletResponse response) throws UnsupportedEncodingException {
        fileService.downLoad(fileName, filePath, response);
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file) {
        return fileService.upload(file, new File("待审核文件/xxx宾馆设计/模型/").getAbsolutePath());
    }

    @PostMapping("/delFile")
    public Map<String, Object> delFile(String path) {
        return fileService.delete("项目列表\\" + path);
    }

    @PostMapping("/mkpro/{proName}")
    public Map<String, Object> mkpro(MultipartFile file, @PathVariable("proName") String proName) {
        return fileService.mkpro(file, new File("项目列表/" + proName + "/").getAbsolutePath());
    }

}
