package com.firststep.hhjs;

import com.firststep.hhjs.dao.PersonDaoImpl;
import com.firststep.hhjs.model.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by daniel on 9/9/2014.
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PersonDaoImpl dao = (PersonDaoImpl) context.getBean("personDao");

        Person peter = new Person("Peter", "Sagan");
        Person nasta = new Person("Nasta", "Kuzminova");

        dao.save(peter);
        dao.save(nasta);

        List<Person> persons = dao.getAll();
//        for (Person person : persons) {
//            System.out.println(person);
//        }
//        persons.forEach(person ->{
//            System.out.println(person);
//        });
        persons.forEach(System.out::print);
        context.close();
    }
}
