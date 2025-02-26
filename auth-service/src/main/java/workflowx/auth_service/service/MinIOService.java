package workflowx.auth_service.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinIOService {
    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws Exception {
        // ✅ Check if file is empty
        System.out.println("File name: " + file.getOriginalFilename());
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty! Please upload a valid file.");
        }

        // ✅ Check if bucket exists, create if necessary
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // ✅ Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // ✅ Ensure correct Content-Type
        String contentType = file.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "application/octet-stream"; // Default fallback
        }

        // ✅ Upload file to MinIO with correct headers
        try (InputStream inputStream = file.getInputStream()) {
            long fileSize = file.getSize();

            if (fileSize == 0) {
                throw new IllegalArgumentException("File size is 0 bytes! Please check your uploaded file.");
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, fileSize, -1) // ✅ Ensure correct file size
                            .contentType(contentType) // ✅ Ensures browser recognition
                            .headers(Map.of(
                                    "Content-Disposition", "inline", // ✅ Enables browser preview
                                    "Content-Type", contentType // ✅ Ensures proper file type
                            ))
                            .build()
            );
        }

        // ✅ Return a direct permanent URL
        return getPresignedUrl(fileName);
    }

    public String getPresignedUrl(String objectName) throws Exception { //Temporary URL
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET) // ✅ Set HTTP method to GET
                        .bucket(bucketName) // ✅ Specify bucket
                        .object(objectName) // ✅ Specify the object (file)
                        .expiry(3600) // ✅ Set expiration (1 hour = 3600 seconds)
                        .build()
        );
    }

}
