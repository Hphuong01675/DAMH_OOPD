package ute.fit.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.InputStream;
import java.util.Properties;

public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            try (InputStream input = CloudinaryConfig.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                Properties prop = new Properties();
                if (input == null) {
                    throw new RuntimeException("Không tìm thấy file application.properties");
                }
                prop.load(input);
                cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", prop.getProperty("cloudinary.cloud_name"),
                    "api_key", prop.getProperty("cloudinary.api_key"),
                    "api_secret", prop.getProperty("cloudinary.api_secret")
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cloudinary;
    }
}