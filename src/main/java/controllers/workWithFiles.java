package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.MediaTypeUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;

@Controller
public class workWithFiles {

    @Autowired
    ServletContext servlerContext;

    final String DIRECTORY = "src/main/webapp/resources/files";
    final String DEFAULT_FILE_NAME = "products.xlsx";

    @RequestMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName
    ) throws Exception{
        MediaType mediaType = MediaTypeUtils.getMediType(this.servlerContext, DEFAULT_FILE_NAME);
        File file = new File(DIRECTORY + "/" + DEFAULT_FILE_NAME);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        System.out.println(resource.toString());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
