package ru.af3412.job4j_cars.model;

import ru.af3412.job4j_cars.model.car.Body;
import ru.af3412.job4j_cars.model.car.Model;

import javax.persistence.*;

@Entity
@Table(name = "ads")
public class SaleAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Model model;
    @ManyToOne
    private Body body;
    private String description;
    private boolean sold;

    public SaleAd() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }
}
