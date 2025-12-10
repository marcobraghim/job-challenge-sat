package br.restful_one.fridge.model;

import java.time.LocalDateTime;

import br.restful_one.fridge.dto.FridgeUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fridge_food")
public class FridgeModel {

  public FridgeModel() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  
  private LocalDateTime expirationDate;
  
  private Integer quantity;

  public void applyUpdate(FridgeUpdateDto dto) {
    var name = dto.getName();
    if (name != null && !name.isEmpty()) {
      this.setName(name);
    }

    if (dto.getExpirationDate() != null) {
      this.setExpirationDate(dto.getExpirationDate());
    }

    if (dto.getQuantity() != null) {
      this.setQuantity(dto.getQuantity());
    }
  }
}
