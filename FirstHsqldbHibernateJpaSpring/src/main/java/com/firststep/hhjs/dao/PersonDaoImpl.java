package com.firststep.hhjs.dao;

import com.firststep.hhjs.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by daniel on 9/9/2014.
 */
@Transactional
public class PersonDaoImpl {

    @PersistenceContext
    private EntityManager em;

    public Long save(Person person) {
        em.persist(person);
        return person.getId();
    }

    public List<Person> getAll() {
        return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }
}
