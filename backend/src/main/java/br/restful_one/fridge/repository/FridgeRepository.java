package br.restful_one.fridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.restful_one.fridge.model.FridgeModel;

public interface FridgeRepository extends JpaRepository<FridgeModel, Long> {
  
}
