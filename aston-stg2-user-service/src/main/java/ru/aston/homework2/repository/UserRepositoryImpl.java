package ru.aston.homework2.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.homework2.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.info("User создан: {}", user.getName());
        } catch (Exception e) {
            log.error("Ошибка создания User", e);
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("User обновлён: {}", user.getName());
        } catch (Exception e) {
            log.error("Ошибка обновления User", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                log.info("User с id {} удалён", id);
            } else {
                log.warn("User с id {} не найден", id);
            }

            transaction.commit();
        } catch (Exception e) {
            log.error("Ошибка удаления User", e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            log.info("Найдено {} пользователей", users.size());
            return users;
        } catch (Exception e) {
            log.error("Ошибка получения списка пользователей", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.error("Ошибка поиска User по ID", e);
            return Optional.empty();
        }
    }
}