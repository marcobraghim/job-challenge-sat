package br.restful_one.pedido.controller;

import br.restful_one.pedido.dto.PedidoCreateDTO;
import br.restful_one.pedido.model.PedidoModel;
import br.restful_one.pedido.service.PedidoService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PedidoController {

  private final PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @PostMapping
  public ResponseEntity<PedidoModel> create(@Valid @RequestBody PedidoCreateDTO dto, UriComponentsBuilder uriComponentsBuilder) {
    try {
      PedidoModel created = pedidoService.createItem(dto);

      var uri = uriComponentsBuilder.path("/pedidos/{id}").buildAndExpand(created.getId()).toUri();
      return ResponseEntity.created(uri).body(created);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoModel> getById(@PathVariable Long id) {
    try {
      PedidoModel found = pedidoService.getById(id);
      if (found == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(found);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
