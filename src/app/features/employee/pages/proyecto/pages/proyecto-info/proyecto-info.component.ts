import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs';
import { ProyectoService } from '../../../../../../shared/services/proyecto.service';

@Component({
  selector: 'app-proyecto-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './proyecto-info.component.html'
})
export class ProyectoInfoComponent implements OnInit {
  data: any;
  loading = false;
  error: string | null = null;

  constructor(private route: ActivatedRoute, private api: ProyectoService) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.paramMap
      .pipe(
        map(p => Number(p.get('id'))),
        switchMap(id => this.api.getById(id))
      )
      .subscribe({
        next: d => { this.data = d; this.loading = false; },
        error: e => { this.error = 'No se pudo cargar el proyecto'; this.loading = false; console.error(e); }
      });
  }
}
