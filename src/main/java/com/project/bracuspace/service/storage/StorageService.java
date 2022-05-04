package com.project.bracuspace.service.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.model.File;
import com.project.bracuspace.repository.FileRepository;
import com.project.bracuspace.service.user.UserDTOService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
@Slf4j
public class StorageService {

    @Value("${storage.bucket}")
    private String bucketName;
    private final AmazonS3 s3Client;
    private final UserDTOService userDTOService;
    private final FileRepository fileRepository;
    private final Logger logger = LoggerFactory.getLogger(StorageService.class);

    public StorageService(AmazonS3 s3Client, FileRepository fileRepository, UserDTOService userDTOService) {
        this.s3Client = s3Client;
        this.fileRepository = fileRepository;
        this.userDTOService = userDTOService;
    }

    public Iterable<File> getFiles(String userName) {

        UserDTO uuD = userDTOService.findByUsername(userName);

        return fileRepository.findFileByUserId(uuD.getId());
    }


    public Boolean uploadFile(MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDTO uuD = userDTOService.findByUsername(authentication.getName());

            java.io.File fileObj = convertMultiPartFileToFile(file);
            s3Client.putObject(new PutObjectRequest(bucketName, uuD.getUsername() + "/" + fileObj.getName(), fileObj));
//            String fileName = fileObj.getName().replaceFirst("[.][^.]+$", "");

            String mimeType = new MimetypesFileTypeMap().getContentType(fileObj);

            java.sql.Date lastModifiedDate = new java.sql.Date(fileObj.lastModified());

            File objectKey = new File(fileObj.getName(), mimeType, fileObj.length(), lastModifiedDate, fileObj.isDirectory(), fileObj.isFile(), uuD.getId());

            fileRepository.save(objectKey);


            return fileObj.delete();
        }
        return false;
    }


    public ByteArrayOutputStream downloadFile(String fileName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserDTO uuD = userDTOService.findByUsername(authentication.getName());
                S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, uuD.getUsername() + "/" + fileName));

                InputStream is = s3object.getObjectContent();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[4096];
                while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                return outputStream;
            }
        } catch (IOException ioException) {
            logger.error("IOException: " + ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException Message:    " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
        return null;
    }


    public void deleteFile(String fileName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserDTO uuD = userDTOService.findByUsername(authentication.getName());
                s3Client.deleteObject(bucketName, uuD.getUsername() + "/" + fileName);
                fileRepository.deleteByFileName(fileName);
            }
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException Message:    " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
    }


    private java.io.File convertMultiPartFileToFile(MultipartFile file) {
        java.io.File convertedFile = new java.io.File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }


//    public void shareFile(String fileName) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            UserDTO uuD = userDTOService.findByUsername(authentication.getName());
//            String keyDir = uuD.getUsername() + "/" + fileName;
//            S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, keyDir));
//            PutObjectRequest request = new PutObjectRequest(bucketName, keyDir, s3object.getObjectContent(), s3object.getObjectMetadata());
//            request.setCannedAcl(CannedAccessControlList.PublicRead);
//            System.out.println("SUCCESS");
//
//        }
//    }

    //            s3Client.generatePresignedUrl(bucketName, uuD.getUsername() + "/" + fileObj.getName(), new Date());

}
