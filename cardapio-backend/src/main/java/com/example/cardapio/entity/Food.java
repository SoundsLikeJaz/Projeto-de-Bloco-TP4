package com.example.cardapio.entity;

import com.example.cardapio.dto.FoodRequestDTO;
import com.example.cardapio.dto.FoodResponseDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;


@Table(name = "foods")
@Entity(name = "foods")
@EqualsAndHashCode(of = "id")
public class Food {

    public Food() {}

    public Food(String title, String url, float price) {
        this.title = title;
        this.url = url;
        this.price = price;
    }

    public Food(FoodResponseDTO data) {
        this.title = data.title();
        this.url = data.url();
        this.price = data.price();
    }

    public Food(FoodRequestDTO data) {
        this.title = data.title();
        this.url = data.url();
        this.price = data.price();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String url;
    private float price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
