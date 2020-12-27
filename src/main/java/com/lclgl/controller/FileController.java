package com.lclgl.controller;

import com.lclgl.service.FileService;
import com.lclgl.service.ProService;
import com.lclgl.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lclgl")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProService proService;

    @PostMapping("/getFileList")
    public Map<String, Object> fileList(String path, HttpSession session) throws IOException {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return fileService.fileList(path, (int) session.getAttribute("staffId"));
    }

    @GetMapping("/download")
    public void download(String fileName, String filePath, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return;
        }
        fileService.download(fileName, filePath, response);
    }

    @GetMapping("/download/{fileName}/{auditStatus}/{staffId}/{proId}")
    public void download(@PathVariable("fileName") String fileName,
                         @PathVariable("auditStatus") String auditStatus,
                         @PathVariable("staffId") String staffId,
                         @PathVariable("pr4oId") String proId,
                         HttpServletResponse response,
                         HttpSession session) throws UnsupportedEncodingException {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return;
        }
        String path = null;
        String staffType = staffService.getStaffType(Integer.parseInt(staffId));
        String proName = proService.getProName(Integer.parseInt(proId));
        if ("审核通过".equals(auditStatus)) path = "项目列表/" + proName + "/" + staffType + "/";
        else if ("待审核".equals(auditStatus) || "审核中".equals(auditStatus)) path = "待审核文件/" + proName + "/" + staffType + "/";
        else path = "回收站/" + proName + "/" + staffType + "/";
        path += fileName;
        fileService.download(fileName, path, response);
    }

    @PostMapping("/upload/{commitWay}/{proId}")
    public Map<String, Object> upload(MultipartFile file, @PathVariable("commitWay") String commitWay, @PathVariable("proId") String proId, HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        String path = fileService.getSavePath(commitWay, Integer.parseInt(proId), (int) staffId);
        return fileService.upload(file, path, commitWay, Integer.parseInt(proId), (int) staffId);
    }

    @PostMapping("/delFile")
    public Map<String, Object> delFile(String path, HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return fileService.delete("项目列表\\" + path);
    }

    @PostMapping("/mkpro/{proName}")
    public Map<String, Object> mkpro(MultipartFile file, @PathVariable("proName") String proName, HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return fileService.mkpro(file, new File("项目列表/" + proName + "/").getAbsolutePath());
    }

}
