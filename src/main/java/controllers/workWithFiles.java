package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@Controller
public class workWithFiles {

    @Autowired
    ServletContext servlerContext;

    final String DEFAULT_FILE_NAME = "products.xlsx";
    String urlString = "https://drive.google.com/uc?export=download&id=16uGeZvBL8Ttn4YBT-vo5GmUh1MHC_eXi";

    @GetMapping("/download")
    public void downloadFile3(HttpServletResponse resonse,
                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "products.xlsx");

        BufferedInputStream inStream = new BufferedInputStream(new URL(urlString).openStream());
        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
    }
}
