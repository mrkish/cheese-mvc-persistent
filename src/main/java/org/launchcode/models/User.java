package org.launchcode.models;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String username;

    @NotNull
    @Size(min=3, max=15)
    private String password;

    @OneToMany
    @JoinTable(name = "user_id")
    private List<Cheese> cheeses = new ArrayList<>();

//    @OneToMany
//    @JoinTable(name = "user_id")
//    private List<Menu> menus = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

//    public List<Menu> getMenus() {
//        return menus;
//    }

//    public void setMenus(List<Menu> menus) {
//        this.menus = menus;
//    }
}
