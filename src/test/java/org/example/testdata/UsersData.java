package org.example.testdata;

public class UsersData {
    private String email;
    private String password;
    private String username;

    public UsersData(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UsersData() {
    }

    // setters
    public UsersData setEmail(String email) {
        this.email = email;
        return this;
    }

    public UsersData setPassword(String password) {
        this.password = password;
        return this;
    }

    public UsersData setUsername(String username) {
        this.username = username;
        return this;
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
