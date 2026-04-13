package ute.fit.service;

import ute.fit.model.UserDTO;

public interface IAuthService {
    // Thay đổi kiểu trả về thành UserDTO
    UserDTO login(String identifier, String password, String roleStr);
}
