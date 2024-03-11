package com.example.gkl.hibernateControllers;

import com.example.gkl.model.Cart;
import com.example.gkl.model.Product;
import com.example.gkl.model.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

public class CartHib {
    public EntityManagerFactory entityManagerFactory;
    public EntityManager em;
    public CartHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    public void addToCart(int cartId, Product product){
        EntityManager em = getEntityManager();
        Cart cart = em.find(Cart.class, cartId);
        product.setCart(cart);
        cart.getItemsInCart().add(product);
    }
    public Cart getCartByUser(User user){
        try{
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);
            query.select(root).where(cb.equal(root.get("user"), user.getId()));
            Query q;
            q = em.createQuery(query);
            return (Cart) q.getSingleResult();
        }catch (Exception e){
            return null;
        }
        finally{
            if(em!=null){
                em.close();
            }
        }
    }

}
