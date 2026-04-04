package ute.fit.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Part;
import ute.fit.config.CloudinaryConfig;
import ute.fit.dao.IBeverageDAO;
import ute.fit.dao.impl.BeverageDAOImpl;
import ute.fit.entity.BeverageEntity;
import ute.fit.service.IBeverageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BeverageServiceImpl implements IBeverageService {
	private IBeverageDAO dao = new BeverageDAOImpl();
	private Cloudinary cloudinary = CloudinaryConfig.getCloudinary();
	
	@Override
    public List<BeverageEntity> getAllBeverages(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return dao.searchByName(keyword);
        }
        return dao.findAll();
    }

	@Override
	public List<BeverageEntity> getAll() {
		return dao.findAll();
	}

	@Override
	public BeverageEntity getById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(BeverageEntity bev, Part filePart) throws Exception {
		// Xử lý upload ảnh
		if (filePart != null && filePart.getSize() > 0) {
			File tempFile = File.createTempFile("upload_", "_" + filePart.getSubmittedFileName());
			try (InputStream is = filePart.getInputStream(); FileOutputStream fos = new FileOutputStream(tempFile)) {
				is.transferTo(fos);
			}
			Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
			bev.setImgUrl((String) uploadResult.get("url"));
			tempFile.delete();
		}

		if (bev.getProductID() > 0) {
			// Edit: Nếu không up ảnh mới, lấy lại url cũ
			if (bev.getImgUrl() == null) {
				bev.setImgUrl(dao.findById(bev.getProductID()).getImgUrl());
			}
			dao.update(bev);
		} else {
			dao.insert(bev);
		}
	}

	@Override
	public void delete(int id) {
		dao.delete(id);
	}
}