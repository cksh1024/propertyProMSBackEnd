package com.lclgl.service;

import com.lclgl.dao.AuditMapper;
import com.lclgl.dao.ProInfoMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.AuditInfo;
import com.lclgl.pojo.ProInfo;
import com.lclgl.pojo.StaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.*;

/**
 * @author cksh
 * @create 2020-12-14 9:46
 */
@Service
public class FileService {
    private String rootPath = "项目列表";
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private AuditMapper auditMapper;
    @Autowired
    private StaffService staffService;

    public Map<String, Object> fileList(String path) throws IOException {
        File rootDir = new File(rootPath);
        if (!rootDir.exists()) rootDir.mkdir();
        HashMap<String, Object> map = new HashMap<>();
        String realPath = path == null ? rootPath : rootPath + File.separator + path;
        File file = new File(realPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            ArrayList<String> dirs = new ArrayList<>();
            ArrayList<String> fs = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) dirs.add(files[i].getPath());
                else fs.add(files[i].getPath());
            }
            map.put("files", fs);
            map.put("directorys", dirs);
            map.put("lastDirectory", rootPath + File.separator + path);
        }
        return map;
    }

    public Map<String, Object> download(String fileName, String filePath, HttpServletResponse response) throws UnsupportedEncodingException {
        HashMap<String, Object> map = new HashMap<>();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int read = bis.read(buff);
            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
            map.put("msg","下载失败！");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        map.put("result","下载成功！");
        return map;
    }

    public Map<String, Object> upload(MultipartFile file, String filePath, String commitWay, int proId, int staffId) {
        HashMap<String, Object> map = new HashMap<>();

        if (file == null || file.isEmpty()) {
            map.put("msg", "未选择需上传的文件！");
            return map;
        }

        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath, file.getOriginalFilename());
        try {
            file.transferTo(fileUpload);
            map.put("msg", "上传文件到服务器成功！");
            if ("audit".equals(commitWay)) {
                StaffInfo staff = staffInfoMapper.getStaff(staffId);
                ProInfo pro = proInfoMapper.getProById(proId);
                AuditInfo auditInfo = new AuditInfo(0, staff, new Date(), file.getOriginalFilename(), "待审核", pro, null);
                int i = auditMapper.addAuditInfo(auditInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "上传文件到服务器失败！");
        }

        return map;
    }

    public Map<String, Object> delete(String path) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", -1);

        File file = new File(path);
        if (file.exists()) {
            if (file.listFiles() == null || file.listFiles().length == 0) {
                file.delete();
                map.put("msg", "删除成功！");
                map.put("status", 1);
            } else {
                map.put("msg", "该文件夹下还有文件，不能删除！");
            }
        } else {
            map.put("msg", "删除失败！");
        }

        return map;
    }

    public Map<String, Object> moveFile(String oldPath, String newPath) {
        HashMap<String, Object> map = new HashMap<>();

        File file = new File(oldPath);
        if (file.exists()) {
            file.renameTo(new File(newPath));
            map.put("msg", "移动成功！");
        } else {
            map.put("msg", "请移动有效文件！");
        }

        return map;
    }

    public Map<String, Object> mkpro(MultipartFile file, String filePath) {
        HashMap<String, Object> map = new HashMap<>();

        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
            new File(filePath + File.separator + "jpg").mkdir();
            new File(filePath + File.separator + "max").mkdir();
            new File(filePath + File.separator + "max/原始模型").mkdir();
            new File(filePath + File.separator + "max/最终渲染").mkdir();
            new File(filePath + File.separator + "小样").mkdir();
            new File(filePath + File.separator + "资料").mkdir();
        }

        fileUpload = new File(filePath + File.separator + "资料", file.getOriginalFilename());
        try {
            file.transferTo(fileUpload);
            map.put("msg", "上传文件到服务器成功！");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "上传文件到服务器失败！");
        }

        return map;
    }

    public String getSavePath(String commitWay, int proId, int staffId) {
        String path = "";
        String staffType = staffService.getStaffType(staffId);
        ProInfo pro = proInfoMapper.getProById(proId);
        if ("暂无工作类型".equals(staffType)) path = "回收站/";
        else if ("audit".equals(commitWay)) path = "待审核文件/" + pro.getProName() + "/" + staffType + "/";
        else if ("submit".equals(commitWay)) path = "项目列表/" + pro.getProName() + "/" + staffType + "/";
        return new File(path).getAbsolutePath();
    }

}
