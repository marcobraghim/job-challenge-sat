package br.restful_one.pedido.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

  private static final Logger logger = LoggerFactory.getLogger(NotificacaoService.class);

  public void enviarEmail(Long clienteId, String mensagem) {
    logger.info("Simulando envio de e-mail para o cliente {}: {}", clienteId, mensagem);
  }
}