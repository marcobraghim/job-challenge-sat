package br.restful_one.fridge.controller;

import br.restful_one.fridge.dto.FridgeCreateDto;
import br.restful_one.fridge.dto.FridgeUpdateDto;
import br.restful_one.fridge.model.FridgeModel;
import br.restful_one.fridge.service.FridgeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FridgeControllerTest {

  @Mock
  private FridgeService fridgeService;

  @InjectMocks
  private FridgeController fridgeController;

  @Test
  void getAll_shouldReturn200OK_withFridgeList() {
    List<FridgeModel> fridges = Collections.singletonList(new FridgeModel());
    when(fridgeService.getAll()).thenReturn(fridges);

    ResponseEntity<List<FridgeModel>> response = fridgeController.getAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(fridges, response.getBody());
  }

  @Test
  void getAll_shouldReturn400BadRequest_whenException() {
    when(fridgeService.getAll()).thenThrow(new RuntimeException());

    ResponseEntity<List<FridgeModel>> response = fridgeController.getAll();

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void getById_shouldReturn200OK_whenIdExists() {
    FridgeModel found = new FridgeModel();
    found.setId(1L);
    when(fridgeService.getById(1L)).thenReturn(found);

    ResponseEntity<FridgeModel> response = fridgeController.getMethodName(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(found, response.getBody());
  }

  @Test
  void getById_shouldReturn404NotFound_whenIdDoesNotExist() {
    when(fridgeService.getById(1L)).thenReturn(null);

    ResponseEntity<FridgeModel> response = fridgeController.getMethodName(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getById_shouldReturn400BadRequest_whenException() {
    when(fridgeService.getById(1L)).thenThrow(new RuntimeException());

    ResponseEntity<FridgeModel> response = fridgeController.getMethodName(1L);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void create_shouldReturn201Created_whenValidDTO() {
    FridgeCreateDto dto = new FridgeCreateDto();
    FridgeModel created = new FridgeModel();
    created.setId(1L);
    when(fridgeService.createItem(any(FridgeCreateDto.class))).thenReturn(created);

    ResponseEntity<FridgeModel> response = fridgeController.create(dto, UriComponentsBuilder.newInstance());

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(created, response.getBody());
  }

  @Test
  void create_shouldReturn400BadRequest_whenInvalidDTO() {
    FridgeCreateDto dto = new FridgeCreateDto();
    when(fridgeService.createItem(any(FridgeCreateDto.class))).thenThrow(new RuntimeException());

    ResponseEntity<FridgeModel> response = fridgeController.create(dto, UriComponentsBuilder.newInstance());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void update_shouldReturn200OK_whenValidUpdate() {
    FridgeUpdateDto dto = new FridgeUpdateDto();
    FridgeModel updated = new FridgeModel();
    updated.setId(1L);
    when(fridgeService.update(eq(1L), any(FridgeUpdateDto.class))).thenReturn(updated);

    ResponseEntity<FridgeModel> response = fridgeController.update(1L, dto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updated, response.getBody());
  }

  @Test
  void update_shouldReturn404NotFound_whenIdDoesNotExist() {
    FridgeUpdateDto dto = new FridgeUpdateDto();
    when(fridgeService.update(eq(1L), any(FridgeUpdateDto.class))).thenThrow(new RuntimeException());

    ResponseEntity<FridgeModel> response = fridgeController.update(1L, dto);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void delete_shouldReturn204NoContent_whenIdExists() {
    FridgeModel found = new FridgeModel();
    found.setId(1L);
    when(fridgeService.getById(1L)).thenReturn(found);
    doNothing().when(fridgeService).deleteById(1L);

    ResponseEntity<Object> response = fridgeController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(fridgeService, times(1)).deleteById(1L);
  }

  @Test
  void delete_shouldReturn404NotFound_whenIdDoesNotExist() {
    when(fridgeService.getById(1L)).thenReturn(null);

    ResponseEntity<Object> response = fridgeController.delete(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(fridgeService, never()).deleteById(1L);
  }

  @Test
  void delete_shouldReturn404NotFound_whenDeletionThrowsException() {
    FridgeModel found = new FridgeModel();
    found.setId(1L);
    when(fridgeService.getById(1L)).thenReturn(found);
    doThrow(new RuntimeException()).when(fridgeService).deleteById(1L);

    ResponseEntity<Object> response = fridgeController.delete(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
