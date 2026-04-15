package com.oscaris.caterers.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
@Embeddable
@Builder
public class UserInfo {

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String firstName;

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String lastName;

    @Column(nullable = false)
    private int status = 1;

    @Column(nullable = false)
    private String photoPath;

    public UserInfo() {}

    public UserInfo(String firstName, String lastName, int status, String photoPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.photoPath = photoPath;
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    @Override
    public String toString() {
        return "UserInfo{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
