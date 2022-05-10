package com.example.se114_healthcareapplication.model.entity;

public class UserEntity extends BaseModelEntity{
    private String Name;
    private String ID;

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    UserEntity(String Name, String ID){
        this.Name =Name;
        this.ID = ID;
    }
}
