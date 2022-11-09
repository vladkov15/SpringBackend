package com.tilted.Laba3.Interfaces;

import com.tilted.Laba3.Models.UserDTO;

import java.util.List;

public interface IUsersService {
    UserDTO Create(UserDTO user);
    List<UserDTO> GetAll();
    UserDTO GetById(int id);
    void Update(UserDTO user);
    boolean DeleteById(int id);
}
