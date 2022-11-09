package com.tilted.Laba3.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class UserDTO {
    @JsonProperty("id")
    public int Id;
    @NotBlank
    @JsonProperty("name")
    public String Name;
    @NotBlank
    @JsonProperty("surname")
    public String Surname;
    @JsonProperty("patronymic")
    public String Patronymic;
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern="dd.MM.yyyy")
    public Date DateOfBirth;
    @Range(min = 0, max = 1)
    @JsonProperty("gender")
    public int Gender;
    @Length(max = 2)
    @JsonProperty("passport_series")
    public String PassportSeries;
    @JsonProperty("passport_number")
    public String PassportNumber;
    @JsonProperty("phone_number")
    public String PhoneNumber;
    @NotBlank
    @Email
    @JsonProperty("email")
    public String Email;
    @JsonProperty("employed")
    public int Employed;
    @JsonProperty("citizenship")
    public List<String> Citizenship;
}
