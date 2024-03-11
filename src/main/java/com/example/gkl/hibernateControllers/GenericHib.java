package com.example.gkl.hibernateControllers;

import com.example.gkl.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericHib {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public GenericHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public Cart getCartByUser(User user){
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);
            query.select(root).where(cb.equal(root.get("user_id"), user.getId()));
            Query q;
            q = em.createQuery(query);
            return (Cart) q.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public <T> void create(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public <T> void update(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity); // For detached entities
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
    public void deleteProduct(int id){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var product = em.find(Product.class, id);
            Warehouse warehouse = product.getWarehouse();
            if(warehouse!=null){
                warehouse.getInStockProducts().remove(product);
                em.merge(product);
            }
            Purchase purchase = product.getPurchase();
            if(purchase!=null){
                purchase.getItemsInPurchase().remove(product);
                em.merge(product);
            }

            em.remove(product);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null) em.close();
        }
    }
    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            var object = em.find(entityClass, id);
            em.remove(object);
            em.getTransaction().commit();
            System.out.println("ISEJO!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
    public <T> T getEntityById(Class<T> entityClass, int id) {
        T result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public <T> List<T> getAllRecords(Class<T> entityClass) {
        EntityManager em = null;
        List<T> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = em.createQuery(query);
            result = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}