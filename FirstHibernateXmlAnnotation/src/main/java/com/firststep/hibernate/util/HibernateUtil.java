package com.firststep.hibernate.util;

import com.firststep.hibernate.model.Employee1;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * Created by daniel on 9/8/2014.
 */
public class HibernateUtil {
    //XML based configuration
    private static SessionFactory sessionFactory;

    //Annotation based configuration
    private static SessionFactory sessionAnnotationFactory;

    //Property based configuration
    private static SessionFactory sessionJavaConfigFactory;

    //private static SessionFactory buildSessionFactory (Callable<Configuration> fn, Runnable prt){
    private static SessionFactory buildSessionFactory (Callable<Configuration> fn, Runnable prt){
        try {
            Configuration configuration;
            if(fn != null){
                configuration = fn.call();
            }
            else {
                // Create the SessionFactory from hibernate.cfg.xml
                configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                System.out.println("Hibernate Configuration loaded");
            }

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            if(prt != null && fn != null)
                prt.run();
            else
                System.out.println("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionFactory() {
        return buildSessionFactory(null, null);
    }

    private static SessionFactory buildSessionAnnotationFactory() {
        return buildSessionFactory(() -> {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate-annotation.cfg.xml");
            System.out.println("Hibernate Annotation Configuration loaded");
            return configuration;
        }, () -> {
            System.out.println("Hibernate Annotation serviceRegistry created");
        });
    }

    private static SessionFactory buildSessionJavaConfigFactory() {
        return buildSessionFactory(() -> {
            Configuration configuration = new Configuration();

            //Create Properties, can be read from property files too
            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
            props.put("hibernate.connection.url", "jdbc:jtds:sqlserver://localhost:1433");// or /javadb
            props.put("hibernate.connection.username", "java");
            props.put("hibernate.connection.password", "password");
            props.put("hibernate.current_session_context_class", "thread");

            configuration.setProperties(props);

            //we can set mapping file or class with annotation
            //addClass(Employee1.class) will look for resource
            // com/firststep/hibernate/model/Employee1.hbm.xml (not good)
            configuration.addAnnotatedClass(Employee1.class);
            return configuration;
        }, () -> {
            System.out.println("Hibernate Java Config serviceRegistry created");
        });
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionAnnotationFactory() {
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }

    public static SessionFactory getSessionJavaConfigFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }
}
