package by.harlap.hostel.dto;

import by.harlap.hostel.enumerations.Role;

public class UserDto {
    private int user_id;
    private String login;
    private String password;
    private double sale;
    private boolean block;
    private int order_number;
    Role roles;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }
}
