package com.example.htmlunitemptyfileupload;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HtmlUnitEmptyFileUploadApplicationTests {

    @LocalServerPort
    int localServerPort;

    private File getFile() throws IOException {
        Resource resource = new ClassPathResource("test.txt");
        return resource.getFile();
    }

    @Test
    void uploadExistingFileTest() throws IOException {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage("http://localhost:" + localServerPort + "/");
        HtmlForm form = page.getHtmlElementById("upload_form");
        HtmlFileInput fileInput = form.getInputByName("file");

        File file = getFile();
        String fileContent = new String(new FileInputStream(file).readAllBytes());

        fileInput.setFiles(file);

        HtmlSubmitInput submitbutton = form.getInputByName("submit");
        page = submitbutton.click();

        DomElement paragraph = page.getElementById("content");
        Assertions.assertEquals(fileContent,paragraph.getVisibleText());
    }

    @Test
    void submitNoFileTest() throws IOException {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage("http://localhost:" + localServerPort + "/");
        HtmlForm form = page.getHtmlElementById("upload_form");

        HtmlSubmitInput submitbutton = form.getInputByName("submit");
        page = submitbutton.click();

        DomElement paragraph = page.getElementById("content");
        Assertions.assertEquals("No File was uploaded",paragraph.getVisibleText());
    }

}
