package com.example.gkl.hibernateControllers;

import com.example.gkl.model.Customer;
import com.example.gkl.model.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.mindrot.jbcrypt.BCrypt;

public class UserHib {
    public EntityManagerFactory entityManagerFactory = null;
    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    public void updateCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Merge the updated user object into the persistence context
            entityManager.merge(customer);

            // Commit the transaction
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Rethrow the exception
        } finally {
            entityManager.close();
        }
    }


    public boolean checkIfLoginExists(String login){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.equal(root.get("login"), login));
            Query q;
            q = em.createQuery(query);
            if (q.getSingleResult() != null){
                return true;
            }
        }
        catch(NoResultException e){
            return false;
        }finally{
            if(em!=null) em.close();
        }
        return false;
    }

    public User getUserByCredentials(String login, String password){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.equal(root.get("login"), login));
            Query q;
            q = em.createQuery(query);
            User user = (User) q.getSingleResult();
            if(user!=null /*&& BCrypt.checkpw(password, user.getPassword())*/){
                return user;
            }
            else{
                return null;
            }
        }
        catch(NoResultException e){
            return null;
        }finally{
            if(em!=null) em.close();
        }
    }
    public Customer getCustomerByCredentials(String login, String password){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.equal(root.get("login"), login));
            Query q;
            q = em.createQuery(query);
            Customer customer = (Customer) q.getSingleResult();
            if(customer!=null /*&& BCrypt.checkpw(password, user.getPassword())*/){
                return customer;
            }
            else{
                return null;
            }
        }
        catch(NoResultException e){
            return null;
        }finally{
            if(em!=null) em.close();
        }
    }
    public void createUser(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}
