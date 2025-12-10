package br.restful_one.pedido.controller;

import br.restful_one.pedido.dto.PedidoCreateDTO;
import br.restful_one.pedido.model.PedidoModel;
import br.restful_one.pedido.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

  @Mock
  private PedidoService pedidoService;

  @InjectMocks
  private PedidoController pedidoController;

  @Test
  void create_shouldReturn201Created_whenValidDTO() {
    PedidoCreateDTO dto = new PedidoCreateDTO();
    dto.setClienteId(5L);
    dto.setValor(BigDecimal.valueOf(158.50));

    PedidoModel created = new PedidoModel();
    created.setId(5L);
    created.setValor(BigDecimal.valueOf(158.50));
    when(pedidoService.createItem(any(PedidoCreateDTO.class))).thenReturn(created);

    ResponseEntity<PedidoModel> response = pedidoController.create(dto, UriComponentsBuilder.newInstance());

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(created, response.getBody());
    assertNotNull(response.getBody());
    assertEquals(dto.getClienteId(), response.getBody().getId());
    assertEquals(dto.getValor(), response.getBody().getValor());
  }

  @Test
  void create_shouldReturn400BadRequest_whenInvalidDTO() {
    PedidoCreateDTO dto = new PedidoCreateDTO();
    when(pedidoService.createItem(any(PedidoCreateDTO.class))).thenThrow(new RuntimeException());

    ResponseEntity<PedidoModel> response = pedidoController.create(dto, UriComponentsBuilder.newInstance());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void getById_shouldReturn200OK_whenIdExists() {
    PedidoModel found = new PedidoModel();
    found.setId(1L);
    when(pedidoService.getById(1L)).thenReturn(found);

    ResponseEntity<PedidoModel> response = pedidoController.getById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(found, response.getBody());
  }

  @Test
  void getById_shouldReturn404NotFound_whenIdDoesNotExist() {
    when(pedidoService.getById(1L)).thenReturn(null);

    ResponseEntity<PedidoModel> response = pedidoController.getById(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getById_shouldReturn400BadRequest_whenException() {
    when(pedidoService.getById(1L)).thenThrow(new RuntimeException());

    ResponseEntity<PedidoModel> response = pedidoController.getById(1L);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
