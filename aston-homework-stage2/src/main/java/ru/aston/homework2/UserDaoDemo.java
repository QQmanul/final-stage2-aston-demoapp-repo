package ru.aston.homework2;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.homework2.entities.User;
import ru.aston.homework2.repository.UserRepository;
import ru.aston.homework2.repository.UserRepositoryImpl;
import ru.aston.homework2.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDaoDemo {
    private static final Logger log = LoggerFactory.getLogger(UserDaoDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        UserRepository repository = new UserRepositoryImpl(sessionFactory);

        User vova = new User("Vova", "Vova@gov.de", 21);
        User nikita = new User("Nikita", "Nikita@gov.de", 21);
        User sveta = new User("Sveta", "Sveta@gov.de", 21);

        repository.create(vova);
        repository.create(nikita);
        repository.create(sveta);

        try {
            User firstUser = repository.findById(1L)
                    .orElseThrow( () -> new RuntimeException("Пользователь не найден!"));
            log.info("Первый пользователь {}", firstUser);

            List<User> userList = repository.findAll();
            log.info("Пользователей {}", userList.size());
            userList.forEach(usr -> log.info(usr.toString()));

            repository.delete(nikita.getId());
            log.info("Удален пользователь {}", nikita.getName());

            sveta.setAge(100);
            sveta.setEmail("");
            repository.update(sveta);
            log.info("Пользователь изменен {}", sveta.getName());

            userList = repository.findAll();
            log.info("Пользователей {}", userList.size());
            userList.forEach(usr -> log.info(usr.toString()));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }

        sessionFactory.close();
        HibernateSessionFactoryUtil.shutdown();
    }
}
