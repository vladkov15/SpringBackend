package com.tilted.Laba3.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int Id;

    public String Name;
    public String Surname;
    public String Patronymic;
    public Date DateOfBirth;
    public int Gender;
    public String PassportSeries;
    public String PassportNumber;
    public String PhoneNumber;
    public String Email;
    public int Employed;
    public String Citizenship;
}
