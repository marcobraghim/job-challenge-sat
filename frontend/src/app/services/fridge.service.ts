import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from 'src/core/constants';
import { FridgeItemModel } from '../models/fridge-item.model';

@Injectable({
  providedIn: 'root'
})
export class FridgeService {

  constructor(private http: HttpClient) { }

  getItems(): Observable<FridgeItemModel[]> {
    return this.http.get<FridgeItemModel[]>(`${API_BASE_URL}/fridge`);
  }

  getItem(id: number): Observable<FridgeItemModel> {
    return this.http.get<FridgeItemModel>(`${API_BASE_URL}/fridge/${id}`);
  }

  deleteItem(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/fridge/${id}`);
  }

  updateItem(item: FridgeItemModel): Observable<FridgeItemModel> {
    return this.http.put<FridgeItemModel>(`${API_BASE_URL}/fridge/${item.id}`, {
      name: item.name,
      expirationDate: `${item.expirationDate}T00:00:00`,
      quantity: item.quantity,
    });
  }

  createItem(item: Omit<FridgeItemModel, 'id'>): Observable<FridgeItemModel> {
    return this.http.post<FridgeItemModel>(`${API_BASE_URL}/fridge`, {
      name: item.name,
      expirationDate: `${item.expirationDate}T00:00:00`,
      quantity: item.quantity,
    });
  }
}
