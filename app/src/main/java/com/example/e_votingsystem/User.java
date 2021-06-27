package com.example.e_votingsystem;

public class User {
    public String email,name,identity,typeOfAccount;
    public User(){

    }
    public User(String email,String name, String identity, String typeOfAccount){
        this.email=email;
        this.name=name;
        this.identity=identity;
        this.typeOfAccount=typeOfAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }
}
