package ca.home.novacom.restfull.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Product> products;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer")
    private User user;
    private LocalDateTime dateCreate;

    public Basket(){}

    public Basket(User user, LocalDateTime dateCreate, List<Product> products){
        this.user = user;
        this.dateCreate = dateCreate;
        this.products.addAll(products);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket = (Basket) o;

        if (id != null ? !id.equals(basket.id) : basket.id != null) return false;
        if (products != null ? !products.equals(basket.products) : basket.products != null) return false;
        if (user != null ? !user.equals(basket.user) : basket.user != null) return false;
        return dateCreate != null ? dateCreate.equals(basket.dateCreate) : basket.dateCreate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        return result;
    }
}
