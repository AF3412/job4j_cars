package ru.af3412.job4j_cars.model;

import ru.af3412.job4j_cars.model.car.Body;
import ru.af3412.job4j_cars.model.car.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ads")
public class SaleAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id", nullable = false)
    private Body body;
    private String description;
    private boolean sold;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SaleAd() {
    }

    public static SaleAd of(Model model, Body body, String description, boolean sold, User user) {
        final SaleAd saleAd = new SaleAd();
        saleAd.model = model;
        saleAd.body = body;
        saleAd.description = description;
        saleAd.sold = sold;
        saleAd.user = user;
        return saleAd;
    }

    public int getId() {
        return id;
    }

    public Model getModel() {
        return model;
    }

    public Body getBody() {
        return body;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSold() {
        return sold;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleAd saleAd = (SaleAd) o;

        if (id != saleAd.id) return false;
        if (sold != saleAd.sold) return false;
        if (!Objects.equals(model, saleAd.model)) return false;
        if (!Objects.equals(body, saleAd.body)) return false;
        if (!Objects.equals(description, saleAd.description)) return false;
        return Objects.equals(user, saleAd.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (sold ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SaleAd{" +
                "id=" + id +
                ", model=" + model +
                ", body=" + body +
                ", description='" + description + '\'' +
                ", sold=" + sold +
                ", user=" + user +
                '}';
    }
}
