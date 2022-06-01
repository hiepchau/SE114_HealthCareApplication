package com.example.se114_healthcareapplication.model.entity;


public class UserEntity extends BaseModelEntity{
    public String FirstName;
    public String LastName;
    public int Age;
    public int Gender; // 0 is female 1 is male



    public UserEntity(String firstName, String lastName, int age, int gender){
        this.FirstName =firstName;
        this.LastName = lastName;
        this.Age = age;
        this.Gender = gender;
    }

}
