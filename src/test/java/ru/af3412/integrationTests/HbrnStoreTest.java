package ru.af3412.integrationTests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.af3412.job4j_cars.model.SaleAd;
import ru.af3412.job4j_cars.model.User;
import ru.af3412.job4j_cars.model.car.Body;
import ru.af3412.job4j_cars.model.car.Model;
import ru.af3412.job4j_cars.store.HbrnStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HbrnStoreTest {

    private HbrnStore hbrnStore;

    @Before
    public void initHbmTracker() throws SQLException {
        this.hbrnStore = new HbrnStore();
        initDatabase();
    }

    private static void initDatabase() throws SQLException {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE TABLE IF NOT EXISTS models (id INT NOT NULL PRIMARY KEY, name VARCHAR(50) NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS bodies (id INT NOT NULL PRIMARY KEY, name VARCHAR(50) NOT NULL)");
            connection.commit();
            statement.executeUpdate("INSERT INTO models(name) VALUES ('model')");
            statement.executeUpdate("INSERT INTO models(name) VALUES ('model2')");
            statement.executeUpdate("INSERT INTO bodies(name) VALUES ('body')");
            statement.executeUpdate("INSERT INTO bodies(name) VALUES ('body2')");
            connection.commit();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:mem:job4j_cars;sql.enforce_size=false", "test", "");
    }

    @After
    public void closeHbmTracker() {
        this.hbrnStore.close();
    }

    @Test
    public void whenFindAllSalesInEmptyBaseReturnEmptyList() {
        var result = hbrnStore.findAllAds();
        assertThat(result.size(), is(0));
    }

    @Test
    public void whenFindByIdAndUserNotExistsThenReturnEmptyOptional() {
        Optional<User> user = hbrnStore.findUserById(1);
        assertThat(user, is(Optional.empty()));
    }

    @Test
    public void whenSaveUserThenReturnThisUserWithId() {
        User user = User.of("email", "user", "password");
        Optional<User> result = hbrnStore.saveUser(user);
        assertTrue(result.isPresent());
        assertThat(result.get().getId(), is(1));
    }

    @Test
    public void whenAddSaleThenFindAllReturnListWithOneElement() {
        User user = User.of("email", "user", "password");
        User savedUser = hbrnStore.saveUser(user).get();
        Model model = hbrnStore.findAllModel().get(0);
        Body body = hbrnStore.findAllBody().get(0);

        SaleAd saleAd = SaleAd.of(model, body, "description", false, savedUser);
        hbrnStore.saveAd(saleAd);

        var result = hbrnStore.findAllAds();

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getId(), is(1));

    }

    @Test
    public void whenFindAllAdByUserReturnListAd() {
        User user = hbrnStore.saveUser(User.of("email", "user", "password")).get();
        Model model = hbrnStore.findAllModel().get(0);
        Body body = hbrnStore.findAllBody().get(0);

        SaleAd saleAd = SaleAd.of(model, body, "description", false, user);
        SaleAd saleAd2 = SaleAd.of(model, body, "description2", false, user);
        hbrnStore.saveAd(saleAd);
        hbrnStore.saveAd(saleAd2);

        var result = hbrnStore.findAllAdsByUser(user);

        assertThat(result.size(), is(2));
    }

    @Test
    public void whenFindAllAdByModelReturnListAd() {
        User user = hbrnStore.saveUser(User.of("email", "user", "password")).get();
        Model model = hbrnStore.findAllModel().get(0);
        Body body = hbrnStore.findAllBody().get(0);

        SaleAd saleAd = SaleAd.of(model, body, "description", false, user);
        SaleAd saleAd2 = SaleAd.of(model, body, "description2", false, user);
        hbrnStore.saveAd(saleAd);
        hbrnStore.saveAd(saleAd2);

        var result = hbrnStore.findAllAdsByModel(model);

        assertThat(result.size(), is(2));
    }

    @Test
    public void whenFindAllAdByBodyReturnListAd() {
        User user = hbrnStore.saveUser(User.of("email", "user", "password")).get();
        Model model = hbrnStore.findAllModel().get(0);
        Body body = hbrnStore.findAllBody().get(0);

        SaleAd saleAd = SaleAd.of(model, body, "description", false, user);
        SaleAd saleAd2 = SaleAd.of(model, body, "description2", false, user);
        hbrnStore.saveAd(saleAd);
        hbrnStore.saveAd(saleAd2);

        var result = hbrnStore.findAllAdsByBody(body);

        assertThat(result.size(), is(2));
    }

}
