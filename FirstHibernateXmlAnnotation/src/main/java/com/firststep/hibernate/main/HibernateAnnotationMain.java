package com.firststep.hibernate.main;

import com.firststep.hibernate.model.Employee1;
import com.firststep.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;

/**
 * Created by daniel on 9/8/2014.
 */
public class HibernateAnnotationMain {
    public static void main(String[] args) {
        Employee1 emp = new Employee1();
        emp.setName("Java Annotation");
        emp.setRole("Developer");
        emp.setInsertTime(new Date());

        //Get Session
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(emp);
        //Commit transaction
        session.getTransaction().commit();
        System.out.println("Employee ID="+emp.getId());

        //terminate session factory, otherwise program won't end
        sessionFactory.close();
    }
}
