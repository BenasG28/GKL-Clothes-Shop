package com.example.demo1.hibernateControllers;

import com.example.demo1.model.Cart;
import com.example.demo1.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ProductHib {
    public EntityManagerFactory entityManagerFactory;
    public EntityManager em;
    public ProductHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public List<Product> getAllProductWithNoCart(){
            EntityManager em = null;
            List<Product> result = new ArrayList<>();
            try {
                em = getEntityManager();
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery query = em.getCriteriaBuilder().createQuery();
                Root<Product> root = query.from(Product.class);
                query.select(root).where(cb.and(cb.isNull(root.get("cart")),cb.isNull(root.get("purchase"))));
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
