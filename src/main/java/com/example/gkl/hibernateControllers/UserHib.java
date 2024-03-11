package com.example.gkl.hibernateControllers;

import com.example.gkl.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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
            if(user!=null && BCrypt.checkpw(password, user.getPassword())){
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
}
