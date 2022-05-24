package com.example.se114_healthcareapplication.model.entity;


public class UserEntity extends BaseModelEntity{
    private String FirstName;
    private String LastName;
    private String ID;
    private int Age;
    private int Gender; // 0 is female 1 is male

    public String getName() {
        return FirstName + " " + LastName;
    }

    public String getID() {
        return ID;
    }

    public UserEntity(String firstName, String lastName, String ID, int age, int gender){
        this.FirstName =firstName;
        this.LastName = lastName;
        this.ID = ID;
        this.Age = age;
        this.Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public int getGender() {
        return Gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }
}
