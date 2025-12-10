package br.restful_one.fridge.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.restful_one.fridge.dto.FridgeCreateDto;
import br.restful_one.fridge.dto.FridgeUpdateDto;
import br.restful_one.fridge.model.FridgeModel;
import br.restful_one.fridge.repository.FridgeRepository;

@Service
public class FridgeService {
  private final FridgeRepository foodRepository;

  public FridgeService(FridgeRepository foodRepository) {
    this.foodRepository = foodRepository;
  }

  public List<FridgeModel> getAll() {
    return foodRepository.findAll();
  }

  public FridgeModel getById(@NonNull Long id) {
    return foodRepository.findById(id).orElse(null);
  }

  @Transactional
  public FridgeModel createItem(FridgeCreateDto dto) {
    var entity = new FridgeModel();
    entity.setName(dto.getName());
    entity.setExpirationDate(dto.getExpirationDate());
    entity.setQuantity(dto.getQuantity());

    return this.save(entity);
  }

  public FridgeModel save(@NonNull FridgeModel food) {
    return foodRepository.save(food);
  }

  public FridgeModel update(@NonNull Long id, FridgeUpdateDto dto) {
    var found = foodRepository.findById(id).orElse(null);
    if (found == null) {
      return null;
    }
    found.applyUpdate(dto);
    return foodRepository.save(found);
  }

  public void deleteById(@NonNull Long id) {
    foodRepository.deleteById(id);
  }
}
