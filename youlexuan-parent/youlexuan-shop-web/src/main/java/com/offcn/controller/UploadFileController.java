package com.offcn.controller;

import com.offcn.common.FastDFSClient;
import com.offcn.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadFileController {
    @RequestMapping("/upload")
    public Result fileUpload(MultipartFile file){

        try {
            // 服务器ip
            String SERVER_IP = "http://192.168.188.130/";
            // 获取文件后缀名
            String oldFileName = file.getOriginalFilename();
            String substring = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fdfs.conf");
            String oldPath = fastDFSClient.uploadFile(file.getBytes(), substring);
            String path = SERVER_IP + oldPath;
            return new Result(true,path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
}
