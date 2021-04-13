package ru.af3412.job4j_cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.af3412.job4j_cars.model.SaleAd;
import ru.af3412.job4j_cars.model.User;
import ru.af3412.job4j_cars.model.car.Body;
import ru.af3412.job4j_cars.model.car.Model;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HbrnStore implements Store {

    /*private HbrnStore() {
    }

    private static final class Lazy {
        private static final HbrnStore INST = new HbrnStore();
    }

    public static HbrnStore instOf() {
        return Lazy.INST;
    }*/

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public List<SaleAd> findAllAds() {
        return (List<SaleAd>) this.tx(session -> session.createQuery("from SaleAd").list());
    }

    @Override
    public List<SaleAd> findAllAdsByUser(User user) {
        return (List<SaleAd>) this.tx(session ->
                session.createQuery("from SaleAd where user.id = :id")
                        .setParameter("id", user.getId())
                        .list());
    }

    @Override
    public List<SaleAd> findAllAdsByModel(Model model) {
        return (List<SaleAd>) this.tx(session ->
                session.createQuery("from SaleAd where model.id = :id")
                        .setParameter("id", model.getId())
                        .list());
    }

    @Override
    public List<SaleAd> findAllAdsByBody(Body body) {
        return (List<SaleAd>) this.tx(session ->
                session.createQuery("from SaleAd where body.id = :id")
                        .setParameter("id", body.getId())
                        .list());
    }

    @Override
    public Optional<SaleAd> saveAd(SaleAd saleAd) {
        int saleAdId = this.tx(session -> (int) session.save(saleAd));
        return findSaleAdById(saleAdId);
    }

    @Override
    public Optional<SaleAd> findSaleAdById(int id) {
        try {
            return Optional.of(
                    this.tx(session -> {
                        Query query = session.createQuery("select s from SaleAd s join fetch Model m on s.model.id = m.id join fetch Body b on s.body.id = b.id join fetch User u on s.user.id = u.id where s.id = :id");
                        query.setParameter("id", id);
                        return (SaleAd) query.getSingleResult();
                    })
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Model> findAllModel() {
        return (List<Model>) this.tx(session -> session.createQuery("from Model").list());
    }

    @Override
    public List<Body> findAllBody() {
        return (List<Body>) this.tx(session -> session.createQuery("from Body").list());
    }

    @Override
    public Optional<User> findUserById(int id) {
        try {
            return Optional.of(
                    this.tx(session -> {
                        Query query = session.createQuery("select u from User u left join fetch u.saleAdList where u.id = :id");
                        query.setParameter("id", id);
                        return (User) query.getSingleResult();
                    }));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> saveUser(User user) {
        int userId = this.tx(session -> (int) session.save(user));
        return findUserById(userId);
    }

    private <T> T tx(final Function<Session, T> command) {
        try (Session session = sf.openSession()) {
            try {
                Transaction tx = session.beginTransaction();
                T rsl = command.apply(session);
                tx.commit();
                return rsl;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

}
