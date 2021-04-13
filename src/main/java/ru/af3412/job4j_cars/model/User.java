package ru.af3412.job4j_cars.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String name;
    private String password;
    @OneToMany(mappedBy = "user")
    private final List<SaleAd> saleAdList = new ArrayList<>();

    public User() {
    }

    private User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User of(String email, String name, String password) {
        return new User(email, name, password);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<SaleAd> getSaleAdList() {
        return saleAdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(saleAdList, user.saleAdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, password, saleAdList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", saleAdList=" + saleAdList +
                '}';
    }
}
