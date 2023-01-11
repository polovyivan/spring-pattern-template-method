package com.polovyi.ivan.tutorials.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.polovyi.ivan.tutorials.enm.ReportType;
import com.polovyi.ivan.tutorials.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class ReportTemplateMethodService<R, E> {

    @Autowired
    private AmazonS3 s3Client;

    public final String generateReport(R request) {

        List<E> dataForReport = fetchData(request);
        try {
            String fileContent = generateFileContent(dataForReport);
            String fileName = getFileName();
            String bucketName = getBucketName();
            uploadFileDirectlyToS3(bucketName, fileName, fileContent);
            URL url = generateUrl(fileName, bucketName);
            return url.toString();
        } catch (Exception e) {
            log.error("An error occurred while exporting data. ", e);
            throw new UnprocessableEntityException(getErrorMessage());
        }
    }

    abstract ReportType getReportType();

    abstract String getBucketName();

    abstract String getFileName();

    abstract String getErrorMessage();

    abstract List<E> fetchData(R request);

    abstract String generateFileContent(List<E> dataFromSource);

    public void uploadFileDirectlyToS3(String bucketName, String fileName, String fileContent) throws IOException {
        log.info("Uploading file with fileName {} to the bucket {}", fileName, bucketName);
        ObjectMetadata metadata = new ObjectMetadata();
        byte[] bytes = IOUtils.toByteArray(new ByteArrayInputStream(fileContent.getBytes()));
        metadata.setContentType(getReportType().getContentType());
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, byteArrayInputStream,
                metadata);
        s3Client.putObject(putObjectRequest);
        log.info("File uploaded successfully!");
    }

    private URL generateUrl(String fileName, String bucket) {
        log.info("Generating resigned Url...");
        Date expiration = calculateExpirationDate();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.GET).withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    private Date calculateExpirationDate() {
        log.info("Calculating url expiration time...");
        Date expirationDate = new Date();
        long expirationTTimeMil = expirationDate.getTime();
        expirationTTimeMil += 3600000L;
        expirationDate.setTime(expirationTTimeMil);
        return expirationDate;
    }
}
