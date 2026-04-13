package ute.fit.service.impl;

import ute.fit.entity.BeverageEntity;
import ute.fit.service.IBeverageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.servlet.http.Part;
import ute.fit.config.CloudinaryConfig;
import ute.fit.dao.IBeverageDAO;
import ute.fit.dao.impl.BeverageDAOImpl;
import ute.fit.model.BeverageBuilder;
import ute.fit.model.BeverageDTO;
import ute.fit.mapper.BeverageMapper;
//import ute.fit.model.OrderItemDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeverageServiceImpl implements IBeverageService {
	private IBeverageDAO dao = new BeverageDAOImpl();
	private Cloudinary cloudinary;

	private Cloudinary getCloudinary() {
	    if (cloudinary == null) {
	        cloudinary = CloudinaryConfig.getCloudinary();
	    }
	    return cloudinary;
	}
	
    @Override
    public List<BeverageDTO> getAllBeverages(String keyword) {
        List<BeverageEntity> list =
                (keyword != null && !keyword.isEmpty())
                        ? dao.searchByName(keyword)
                        : dao.findAll();

        return list.stream().map(BeverageMapper::toDTO).collect(Collectors.toList());
    }
    
    @Override
    public List<BeverageDTO> findAll() {
    	return dao.findAll()
    			.stream()
    			.map(BeverageMapper::toDTO)
    			.collect(Collectors.toList());
    }

	@Override
    public BeverageDTO getById(int id) {
        return BeverageMapper.toDTO(dao.findById(id));
    }

	@Override
    public void save(BeverageDTO dto, Part filePart) throws Exception {
        BeverageEntity bev;

        if (dto.getProductID() != null && dto.getProductID() > 0) {
            bev = dao.findById(dto.getProductID()); 
        } else {
            bev = new BeverageEntity();
        }

        bev.setName(dto.getName());
        bev.setBasePrice(dto.getBasePrice());

        // upload ảnh
        if (filePart != null && filePart.getSize() > 0) {
            File tempFile = File.createTempFile("upload_", "_" + filePart.getSubmittedFileName());
            try (InputStream is = filePart.getInputStream();
                 FileOutputStream fos = new FileOutputStream(tempFile)) {
                is.transferTo(fos);
            }

            Map uploadResult = getCloudinary().uploader().upload(tempFile, ObjectUtils.emptyMap());
            bev.setImgUrl((String) uploadResult.get("url"));
            tempFile.delete();
        }

        // giữ trạng thái cũ nếu edit
        if (dto.getProductID() != null && dto.getProductID() > 0) {
            dao.update(bev);
        } else {
            dao.insert(bev);
        }
    }

	@Override
    public void toggleStatus(int id) {
        dao.toggleSellable(id);
    }
	
	@Override
	public BeverageBuilder getBeverageBuilder(BeverageDTO beverageDTO) {
	    return new BeverageBuilder(beverageDTO); // Builder đã có sẵn name và basePrice từ DB
	}
}