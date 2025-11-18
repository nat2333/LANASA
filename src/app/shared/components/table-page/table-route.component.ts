import { Component } from '@angular/core';
import { TablePageComponent } from './table-page.component';

@Component({
  selector: 'app-table-route',
  standalone: true,
  imports: [TablePageComponent],
  template: `<app-table-page></app-table-page>`
})
export class TableRouteComponent {}
