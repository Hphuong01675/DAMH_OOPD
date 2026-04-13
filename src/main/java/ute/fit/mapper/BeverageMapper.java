package ute.fit.mapper;

import ute.fit.entity.BeverageEntity;
import ute.fit.model.BeverageDTO;

public class BeverageMapper {
	public static BeverageDTO toDTO(BeverageEntity e) {
        BeverageDTO dto = new BeverageDTO();
        dto.setProductID(e.getProductID());
        dto.setName(e.getName());
        dto.setBasePrice(e.getBasePrice());
        dto.setImgUrl(e.getImgUrl());
        dto.setSellable(e.isSellable());
        return dto;
    }

    public static BeverageEntity toEntity(BeverageDTO dto) {
        BeverageEntity e = new BeverageEntity();
        e.setProductID(dto.getProductID());
        e.setName(dto.getName());
        e.setBasePrice(dto.getBasePrice());
        e.setImgUrl(dto.getImgUrl());
        e.setSellable(dto.getSellable());
        return e;
    }
}
