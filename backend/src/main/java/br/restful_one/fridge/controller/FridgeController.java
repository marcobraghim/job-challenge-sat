package br.restful_one.fridge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.restful_one.fridge.dto.FridgeCreateDto;
import br.restful_one.fridge.dto.FridgeUpdateDto;
import br.restful_one.fridge.model.FridgeModel;
import br.restful_one.fridge.service.FridgeService;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/fridge")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FridgeController {

  private final FridgeService foodService;

  public FridgeController(FridgeService foodService) {
    this.foodService = foodService;
  }

  @GetMapping
  public ResponseEntity<List<FridgeModel>> getAll() {
    try {
      return ResponseEntity.ok(foodService.getAll());
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<FridgeModel> getMethodName(@NonNull @PathVariable Long id) {
    try {
      var found = foodService.getById(id);
      if (found == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(found);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<FridgeModel> create(@Valid @RequestBody FridgeCreateDto dto, UriComponentsBuilder uriComponentsBuilder) {
    try {
      FridgeModel created = foodService.createItem(dto);

      var uri = uriComponentsBuilder.path("/pedidos/{id}").buildAndExpand(created.getId()).toUri();
      return ResponseEntity.created(uri).body(created);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<FridgeModel> update(
      @NonNull @PathVariable Long id,
      @Valid @RequestBody FridgeUpdateDto dto) {
    try {
      var updated = foodService.update(id, dto);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@NonNull @PathVariable Long id) {
    try {
      var found = foodService.getById(id);
      if (found == null) {
        return ResponseEntity.notFound().build();
      }
      foodService.deleteById(id);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }
}
