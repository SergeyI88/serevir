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
public class TemplateController {

    String urlString1 = "https://drive.google.com/uc?export=download&id=1j-ipCS2FqvnpZ5ERUgKxaM1IXnpmosgL";
    String urlString2 = "https://drive.google.com/uc?export=download&id=1vOTBoyoVRYnt1BtBNta0jS8dmBepTFGv";

    @GetMapping("/download")
    public void downloadFile3(HttpServletResponse resonse) throws IOException {
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "goods.xlsx");

        BufferedInputStream inStream = new BufferedInputStream(new URL(urlString1).openStream());
        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
        outStream.close();
    }

    @GetMapping("/manual")
    public void manual(HttpServletResponse resonse) throws IOException {
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "manual.doc");

        BufferedInputStream inStream = new BufferedInputStream(new URL(urlString2).openStream());
        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
        outStream.close();
    }
}
