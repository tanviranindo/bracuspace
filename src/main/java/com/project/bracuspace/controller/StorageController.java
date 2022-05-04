package com.project.bracuspace.controller;

import com.amazonaws.util.IOUtils;
import com.project.bracuspace.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Controller
@RequestMapping("/user")
public class StorageController {
    private final StorageService service;

    @Autowired
    public StorageController(StorageService service) {
        this.service = service;
    }

    @PostMapping("/upload/remote")
    public String remoteUpload(@ModelAttribute(value = "url") String url, Model model) throws IOException {
//        System.out.println(url);
        URL link = new URL(url);
//        System.out.println(FilenameUtils.getName(link.getPath()));
        URLConnection urlConnection = link.openConnection();
        urlConnection.connect();
        MultipartFile file = new MockMultipartFile(FilenameUtils.getName(link.getPath()), FilenameUtils.getName(link.getPath()), "remote/undefined", IOUtils.toByteArray(urlConnection.getInputStream()));
        model.addAttribute("fileSucceed", service.uploadFile(file));
        return "redirect:/user/space";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam(value = "uploadFile") MultipartFile file, Model model) {
        model.addAttribute("fileSucceed", service.uploadFile(file));
        return "redirect:/user/space";
    }

    @GetMapping(value = "/download/file/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        System.out.println(filename);
        ByteArrayOutputStream downloadInputStream = service.downloadFile(filename);
        return ResponseEntity.ok().contentType(contentType(filename)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(downloadInputStream.toByteArray());
    }


    @GetMapping(value = "/delete/file/{fileName}")
    public String deleteFile(@PathVariable(name = "fileName") String fileName) {
        service.deleteFile(fileName);
        return "redirect:/user/space";
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "txt":
            case "log":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.parseMediaType(fileExtension);
        }
    }
}
