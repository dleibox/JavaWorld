package com.firststep.hibernate.main;

import com.firststep.hibernate.model.Employee;
import com.firststep.hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;

/**
 * Created by daniel on 9/8/2014.
 */
public class HibernateMain {
    public static void main(String[] args) {
        Employee emp = new Employee();
        emp.setName("Xml");
        emp.setRole("CEO");
        emp.setInsertTime(new Date());

        //Get Session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(emp);
        //Commit transaction
        session.getTransaction().commit();
        System.out.println("Employee ID="+emp.getId());

        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionFactory().close();
    }
}
