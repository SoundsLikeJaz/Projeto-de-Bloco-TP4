package com.example.cardapio.service;

import com.example.cardapio.entity.Food;
import com.example.cardapio.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public void saveFood(Food food) {
        foodRepository.save(food);
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food findById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
    }

    public void updateFood(Long id, Food updatedFood) {
        Food existingFood = findById(id);

        existingFood.setTitle(updatedFood.getTitle());
        existingFood.setUrl(updatedFood.getUrl());
        existingFood.setPrice(updatedFood.getPrice());

        foodRepository.save(existingFood);
    }

    public void deleteFood(Long id) {
        Food food = findById(id);
        foodRepository.delete(food);
    }
}
