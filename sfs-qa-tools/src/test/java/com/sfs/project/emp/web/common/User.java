package com.sfs.project.emp.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String pinCode;


    public User() {
        // Do nothing because
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String userName) {
        username = userName;
    }

    public void setPassword(String passWord) {
        password = passWord;
    }


    public String getUserTestName() {
        return userTestName;
    }


    private String userTestName;

    public void setUserTestName(String userTestName) {
        this.userTestName = userTestName;
    }
}
