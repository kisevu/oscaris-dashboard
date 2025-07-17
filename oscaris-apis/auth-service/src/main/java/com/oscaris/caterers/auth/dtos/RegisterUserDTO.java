package com.oscaris.caterers.auth.dtos;

import lombok.Builder;

/**
 * Author: kev.Ameda
 */

@Builder
public class RegisterUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String photoPath;

    public RegisterUserDTO(String email,
                           String password,
                           String firstName,
                           String lastName,
                           String photoPath) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoPath = photoPath;
    }

    public RegisterUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "RegisterUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
