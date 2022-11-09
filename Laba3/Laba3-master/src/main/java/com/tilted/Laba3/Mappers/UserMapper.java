package com.tilted.Laba3.Mappers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tilted.Laba3.Models.User;
import com.tilted.Laba3.Models.UserDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;

public final class UserMapper {
    public static UserDTO ToDTO(User user) {
        var userDto = new UserDTO();
        userDto.Id = user.getId();
        userDto.Name = user.getName();
        userDto.Surname = user.getSurname();
        userDto.Patronymic = user.getPatronymic();
        userDto.DateOfBirth = user.getDateOfBirth();
        userDto.Gender = user.getGender();
        userDto.PassportSeries = user.getPassportSeries();
        userDto.PassportNumber = user.getPassportNumber();
        userDto.PhoneNumber = user.getPhoneNumber();
        userDto.Email = user.getEmail();
        userDto.Employed = user.getEmployed();

        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        userDto.Citizenship = (new Gson().fromJson(user.getCitizenship(), listType));

        return userDto;
    }

    public static User ToModel(UserDTO userDTO) {
        var user = new User();
        user.setId(userDTO.Id);
        user.setName(userDTO.Name);
        user.setSurname(userDTO.Surname);
        user.setPatronymic(userDTO.Patronymic);
        user.setDateOfBirth(userDTO.DateOfBirth);
        user.setGender(userDTO.Gender);
        user.setPassportSeries(userDTO.PassportSeries);
        user.setPassportNumber(userDTO.PassportNumber);
        user.setPhoneNumber(userDTO.PhoneNumber);
        user.setEmail(userDTO.Email);
        user.setEmployed(userDTO.Employed);

        user.setCitizenship(new Gson().toJson(userDTO.Citizenship));

        return user;
    }
}
