package com.example.todolist.user.forms;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@NoArgsConstructor
public class RegisterForm {
    @NotEmpty
    @Size(min=2, max=50, message = "First name must be between 2 and 50 characters long")
    private String firstName;

    @NotEmpty
    @Size(min=2, max=50, message = "Last name must be between 2 and 50 characters long")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty
    @Size(min=8, max=100, message = "Password must be between 8 and 100 characters long")
    private String password;

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
}
