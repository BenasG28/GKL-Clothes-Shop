package com.example.demo1.hibernateControllers;

import com.example.demo1.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PurchaseHib extends GenericHib {
    public EntityManagerFactory entityManagerFactory;
    public EntityManager em;

    public PurchaseHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public List<Purchase> getAllPurchaseByUser(User user) {
        EntityManager em = null;
        List<Purchase> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            Root<Purchase> root = query.from(Purchase.class);
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

    public void createPurchase(Cart userCart) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Purchase purchase = new Purchase();
            User user = userCart.getUser();
            purchase.setUser(user);
            purchase.setPurchaseAmount(userCart.getAmount());
            purchase.setDateCreated(LocalDate.now());
            purchase.setStatus(PurchaseStatus.PENDING);
            List<Product> productList = userCart.getItemsInCart();
            for (Product p : productList) {
                p.setCart(null);
                p.setPurchase(purchase);
                purchase.getItemsInPurchase().add(p);
            }
            user.getPurchaseList().add(purchase);
            em.persist(purchase);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public void deletePurchase(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var purchase = em.find(Purchase.class, id);
            for (Product product : purchase.getItemsInPurchase()) {
                product.setPurchase(null);
            }
            purchase.getUser().getPurchaseList().remove(purchase);
            purchase.setUser(null);
            em.remove(purchase);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
    public List<Purchase> filterByDate(LocalDate start, LocalDate end){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Purchase> query = cb.createQuery(Purchase.class);
            Root<Purchase> root = query.from(Purchase.class);
            query.select(root).where(cb.between(root.get("dateCreated"), start, end));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null) em.close();
        }
        return new ArrayList<>();
    }
    public List<Purchase> filterByStatus(PurchaseStatus status){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Purchase> query = cb.createQuery(Purchase.class);
            Root<Purchase> root = query.from(Purchase.class);
            query.select(root).where(cb.equal(root.get("status"), status));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null) em.close();
        }
        return new ArrayList<>();
    }
    public List<Purchase> filterByCustomer(int id){
        EntityManager em = null;
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Purchase> query = cb.createQuery(Purchase.class);
            Root<Purchase> root = query.from(Purchase.class);
            query.select(root).where(cb.equal(root.get("user"), id));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null) em.close();
        }
        return new ArrayList<>();


    }

}

