import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { EstadisticasService, NominaDepartamento } from '../../../../../shared/services/estadisticasService.service';

@Component({
  selector: 'app-departamentos-stats',
  imports: [CommonModule],
  templateUrl: './departamentos-stats.component.html',
  styleUrl: './departamentos-stats.component.scss'
})
export class DepartamentosStatsComponent  implements OnInit{

  loading = true;
  data: NominaDepartamento[] = [];
  constructor(private stats: EstadisticasService) {}

  ngOnInit(): void {
    this.cargar();
  }

  private cargar() {
    this.loading = true;
    this.stats.getNominaDepartamento().subscribe({
      next: (rows) => {
        this.data = [...rows].sort((a, b) => Number(b.nominaTotal) - Number(a.nominaTotal));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando estadísticas de departamentos', err);
        this.loading = false;
      }
    });
  }

  pct(nom: number, pres: number) {
    if (!pres) return '0%';
    return ((nom / pres) * 100).toFixed(1) + '%';
  }
}
