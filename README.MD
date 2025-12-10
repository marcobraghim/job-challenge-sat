# Tech Challenge SAT

### Backend

Java API project with some few endpoints and tests

### Frontend

Web Angular 15 project with some few pages connecting to the API 

### Refactoring controller PedidoController

There's some problems I would point on this controller:

1. The non negative value is validated on the controller;
1. Population of the values and repository save request could be done on the service layer;
1. Notification service call;
1. The return isn't a valid JSON and does not return the created object;
1. The HTTP status code is not CREATED;

I left a refactored version of this controller on `backend/src/main/java/br/restful_one/pedido/controller/`

```java
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
  @Autowired
  private PedidoRepository pedidoRepository;
  
  @PostMapping
  public ResponseEntity<String> criarPedido(@RequestBody PedidoDTO dto) {
    if (dto.getValor() <= 0) {
      return ResponseEntity.badRequest().body("Valor inválido");
    }
    Pedido pedido = new Pedido();
    pedido.setClienteId(dto.getClienteId());
    pedido.setValor(dto.getValor());
    pedido.setDataCriacao(LocalDateTime.now());
    pedidoRepository.save(pedido);

    // Envia notificação
    NotificacaoService.enviarEmail(dto.getClienteId(), "Pedido criado!");
    return ResponseEntity.ok("Pedido criado com sucesso");
  }
}
```

