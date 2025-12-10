import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FridgeService } from '../../services/fridge.service';
import { FridgeItemModel } from '../../models/fridge-item.model';

@Component({
  selector: 'app-fridge',
  templateUrl: './fridge.component.html',
  styleUrls: ['./fridge.component.css']
})
export class FridgeComponent implements OnInit {
  items: FridgeItemModel[] = [];

  constructor(
    private fridgeService: FridgeService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchItems();
  }

  fetchItems() {
    this.fridgeService.getItems().subscribe({
      next: (data) => {
        this.items = data;
      },
      error: (error) => {
        console.error('Error fetching fridge items', error);
      }
    });
  }



  deleteItem(id: number) {
    if (confirm('Are you sure you want to delete this item?')) {
      this.fridgeService.deleteItem(id).subscribe({
        next: () => {
          this.items = this.items.filter(item => item.id !== id);
        },
        error: (error) => {
          console.error('Error deleting fridge item', error);
        }
      });
    }
  }
}
