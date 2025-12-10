import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FridgeService } from '../../../services/fridge.service';
import { FridgeItemModel } from '../../../models/fridge-item.model';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit {
  item: Partial<FridgeItemModel> = {
    name: '',
    expirationDate: '',
    quantity: 1
  };
  isEditing = false;
  id: number | null = null;

  constructor(
    private fridgeService: FridgeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.isEditing = true;
      this.loadItem(this.id);
    } else {
      const nextWeek = new Date();
      nextWeek.setDate(nextWeek.getDate() + 7);
      this.item.expirationDate = nextWeek.toISOString().split('T')[0];
    }
  }

  loadItem(id: number) {
    this.fridgeService.getItem(id).subscribe({
      next: (item) => {
        this.item = item;
        if (this.item.expirationDate) {
          this.item.expirationDate = this.item.expirationDate.split('T')[0];
        }
      },
      error: (error) => console.error('Error loading item', error)
    });
  }

  errorMessage: string = '';

  onSubmit() {
    this.errorMessage = '';

    const observer = {
      next: () => this.router.navigate(['/fridge']),
      error: (err: any) => {
        if (err.error && err.error.errors && Array.isArray(err.error.errors)) {
          this.errorMessage = err.error.errors
            .map((e: any) => e.defaultMessage)
            .join('; ');
        } else {
          this.errorMessage = 'An unexpected error occurred.';
        }
      }
    };

    if (this.isEditing && this.id) {
      const itemToUpdate = { ...this.item, id: this.id } as FridgeItemModel;
      this.fridgeService.updateItem(itemToUpdate).subscribe(observer);
    } else {
      this.fridgeService.createItem(this.item as Omit<FridgeItemModel, 'id'>).subscribe(observer);
    }
  }
}
