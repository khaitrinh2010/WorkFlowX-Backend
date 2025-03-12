package workflowx.auth_service.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinIOService {
    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioEndpoint;

    public String uploadFile(MultipartFile file) throws Exception {
        System.out.println("File name: " + file.getOriginalFilename());
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty! Please upload a valid file.");
        }

        // Check if bucket exists, create if necessary
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Ensure correct Content-Type
        String contentType = file.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "application/octet-stream"; // Default fallback
        }

        // Upload file to MinIO
        try (InputStream inputStream = file.getInputStream()) {
            long fileSize = file.getSize();
            if (fileSize == 0) {
                throw new IllegalArgumentException("File size is 0 bytes! Please check your uploaded file.");
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, fileSize, -1)
                            .contentType(contentType)
                            .build()
            );
        }

        // ✅ Return a direct **persistent URL**
        return getPersistentUrl(fileName);
    }

    /**
     * Constructs a **permanent** URL using the MinIO endpoint.
     */
    public String getPersistentUrl(String fileName) {
        return String.format("%s/%s/%s", minioEndpoint, bucketName, fileName);
    }
}
