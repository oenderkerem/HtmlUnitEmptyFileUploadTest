package com.example.htmlunitemptyfileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping()
    public String index(){
        return "index";
    }

    @PostMapping("/file")
    public ModelAndView postFile(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return new ModelAndView("success", Map.of("content","No File was uploaded"));
        }
        byte[] bytes = file.getInputStream().readAllBytes();
        return new ModelAndView("success", Map.of("content",new String(bytes)));
    }
}
