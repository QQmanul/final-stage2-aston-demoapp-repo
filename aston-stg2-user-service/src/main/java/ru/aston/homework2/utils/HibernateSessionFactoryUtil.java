package ru.aston.homework2.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateSessionFactoryUtil {

    private static final Logger log = LoggerFactory.getLogger(HibernateSessionFactoryUtil.class);
    private static volatile SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateSessionFactoryUtil.class) {
                if (sessionFactory == null) {
                    try {
                        sessionFactory = new Configuration()
                                .configure()
                                .buildSessionFactory();

                    } catch (Exception e) {
                        log.error("Ошибка создания SessionFactory", e);
                        throw new ExceptionInInitializerError(e);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}