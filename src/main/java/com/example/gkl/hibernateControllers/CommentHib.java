package com.example.gkl.hibernateControllers;

import com.example.gkl.model.Comment;
import com.example.gkl.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CommentHib {
    public EntityManagerFactory entityManagerFactory;
    public EntityManager em;
    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public List<Comment> getAllCommentsByUser(User user){
        EntityManager em = null;
        List<Comment> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            Root<Comment> root = query.from(Comment.class);
            query.select(root).where(cb.equal(root.get("user"), user));
            Query q = em.createQuery(query);
            result = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
    public void deleteComment(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, id);
            User user = comment.getUser();
//            comment.setProduct(null);
//            comment.getUser().getPurchaseList().remove(purchase);
            user.getCommentList().remove(comment);
            comment.setUser(null);
            em.remove(comment);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}
