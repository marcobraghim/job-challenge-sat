package br.restful_one.fridge.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FridgeUpdateDto {

  @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
  private String name;

  @Future(message = "A data de validade deve ser uma data futura.")
  private LocalDateTime expirationDate;

  @Positive(message = "A quantidade deve ser um número positivo.")
  private Integer quantity;
}
