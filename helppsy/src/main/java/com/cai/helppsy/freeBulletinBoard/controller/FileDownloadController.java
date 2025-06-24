package com.cai.helppsy.freeBulletinBoard.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class FileDownloadController {

    @ResponseBody
    @GetMapping("download/freeBulletin")
    public void download(HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {

        String downloadDir = System.getProperty("user.dir")+"/files/freeBulletin/";
        String path = downloadDir+fileName;

        File file = new File(path);
        FileInputStream fileInputStreamin = new FileInputStream(path);

        fileName = new String(fileName.getBytes("UTF-8"), "8859_1");

        response.setContentType("application/octet=stream");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        OutputStream os = response.getOutputStream();

        int length;
        byte[] bytes = new byte[(int) file.length()];
        while((length = fileInputStreamin.read(bytes)) > 0){
            os.write(bytes, 0, length);
        }

        os.flush();
        os.close();
        fileInputStreamin.close();
    }
}
