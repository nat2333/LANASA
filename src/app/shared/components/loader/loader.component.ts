import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderService } from '../../services/loader.service';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule],
  template: `<div class="loader" *ngIf="loader.loading$ | async">Cargando…</div>`,
  styles: [`.loader{position:fixed;inset:0;display:flex;align-items:center;justify-content:center;background:#0003;backdrop-filter:blur(2px);font-weight:600}`]
})
export class LoaderComponent {
  constructor(public loader: LoaderService) {}
}
