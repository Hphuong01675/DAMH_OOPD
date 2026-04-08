package ute.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeverageDTO {
	private Integer productID;
    private String name;
    private double basePrice;
    private String imgUrl;
    private Boolean sellable;
}
