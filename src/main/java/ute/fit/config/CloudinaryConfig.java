package ute.fit.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.InputStream;
import java.util.Properties;

public class CloudinaryConfig {
	private static Cloudinary cloudinary;

    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            String cloudName = System.getenv("CLOUDINARY_NAME");
            String apiKey = System.getenv("CLOUDINARY_API_KEY");
            String apiSecret = System.getenv("CLOUDINARY_API_SECRET");

            if (cloudName == null || apiKey == null || apiSecret == null) {
                try (InputStream input = CloudinaryConfig.class.getClassLoader()
                        .getResourceAsStream("application.properties")) {
                    
                    Properties prop = new Properties();
                    if (input != null) {
                        prop.load(input);
                        cloudName = prop.getProperty("cloudinary.cloud_name");
                        apiKey = prop.getProperty("cloudinary.api_key");
                        apiSecret = prop.getProperty("cloudinary.api_secret");
                    }
                } catch (Exception e) {
                    System.err.println("Không thể đọc application.properties: " + e.getMessage());
                }
            }

            if (cloudName != null && apiKey != null && apiSecret != null) {
                cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret
                ));
            } else {
                throw new RuntimeException("Cấu hình Cloudinary thiếu! Hãy kiểm tra Environment Variables hoặc application.properties.");
            }
        }
        return cloudinary;
    }
}