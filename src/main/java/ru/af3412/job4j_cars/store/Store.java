package ru.af3412.job4j_cars.store;

import ru.af3412.job4j_cars.model.SaleAd;
import ru.af3412.job4j_cars.model.User;
import ru.af3412.job4j_cars.model.car.Body;
import ru.af3412.job4j_cars.model.car.Model;

import java.util.List;
import java.util.Optional;

public interface Store extends AutoCloseable{
    List<SaleAd> findAllAds();

    List<SaleAd> findAllAdsByUser(User user);

    List<SaleAd> findAllAdsByModel(Model model);

    List<SaleAd> findAllAdsByBody(Body body);

    Optional<SaleAd> saveAd(SaleAd saleAd);

    Optional<SaleAd> findSaleAdById(int id);

    List<Model> findAllModel();

    List<Body> findAllBody();

    Optional<User> findUserById(int id);

    Optional<User> saveUser(User user);
}
