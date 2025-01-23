package com.studere.studerejava.culinare.repositories;

import com.studere.studerejava.culinare.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
