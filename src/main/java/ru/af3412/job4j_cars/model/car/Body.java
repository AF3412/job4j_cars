package ru.af3412.job4j_cars.model.car;

import javax.persistence.*;

@Entity
@Table(name = "bodies")
public class Body {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}
