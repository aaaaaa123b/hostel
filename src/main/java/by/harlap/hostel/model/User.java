package by.harlap.hostel.model;

import by.harlap.hostel.enumerations.Role;

public class User {

    private int id;
    private String login;
    private String password;
    Role roles;

    public User(){

    }

    public User(int id, String login, String password, Role roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }



}


