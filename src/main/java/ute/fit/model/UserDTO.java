package ute.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String fullName;
    private String email;
    private String role; // Thêm trường này để phân quyền
    private String phone;
}