package br.restful_one.pedido.service;

import br.restful_one.pedido.dto.PedidoCreateDTO;
import br.restful_one.pedido.model.PedidoModel;
import br.restful_one.pedido.repository.PedidoRepository;
import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;
  private final NotificacaoService notificacaoService;

  public PedidoService(PedidoRepository pedidoRepository, NotificacaoService notificacaoService) {
    this.pedidoRepository = pedidoRepository;
    this.notificacaoService = notificacaoService;
  }

  public PedidoModel getById(@NonNull Long id) {
    return pedidoRepository.findById(id).orElse(null);
  }

  @Transactional
  public PedidoModel createItem(PedidoCreateDTO dto) {
    PedidoModel pedido = new PedidoModel();
    pedido.setClienteId(dto.getClienteId());
    pedido.setValor(dto.getValor());
    pedido.setDataCriacao(LocalDateTime.now());
    
    PedidoModel pedidoSalvo = pedidoRepository.save(pedido);
    
    notificacaoService.enviarEmail(pedidoSalvo.getClienteId(), "Pedido criado!");
    
    return pedidoSalvo;
  }
}
