package br.restful_one.pedido.dto;

import br.restful_one.pedido.controller.PedidoController;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoCreateDTO {

  @NotNull(message = "O ID do cliente não pode ser nulo.")
  private Long clienteId;

  @NotNull(message = "O valor não pode ser nulo.")
  @Positive(message = "O valor do pedido deve ser positivo.")
  private BigDecimal valor;
}