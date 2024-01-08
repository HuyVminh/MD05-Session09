package com.ra.service;

import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;

public interface IUserService {
    User register(User user);
    UserResponseDTO login(UserRequestDTO userRequestDTO);
}
