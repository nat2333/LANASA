import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import {
  EstadisticasService,
  ProveedorCalificacion,
  ProveedorPuntualidad,
  ProveedorProducto,
  ProveedorRelacionProducto
} from '../../../../../shared/services/estadisticasService.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);
Chart.defaults.color = '#e5e7eb';         
Chart.defaults.font.family = '"Inter", system-ui, sans-serif';
Chart.defaults.font.size = 12;


@Component({
  selector: 'app-proveedores-stats',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './proveedores-stats.component.html',
  styleUrls: ['./proveedores-stats.component.scss']
})
export class ProveedoresStatsComponent implements OnInit, AfterViewInit {

  @ViewChild('topCalifChart')     topCalifCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('puntualidadChart')  puntualidadCanvas!: ElementRef<HTMLCanvasElement>;

  loading = true;
  viewReady = false;

  // data
  topCalif: ProveedorCalificacion[] = [];
  puntualidad: ProveedorPuntualidad[] = [];
  mejoresPorProducto: ProveedorProducto[] = [];
  relPorProducto: ProveedorRelacionProducto[] = [];

  // filtro
  idProducto?: number;

  // charts
  private topCalifChart?: Chart;
  private puntualidadChart?: Chart;

  constructor(private api: EstadisticasService) {}

  ngOnInit(): void {}
  ngAfterViewInit(): void {
    this.viewReady = true;
    this.cargarBase();
  }

  private cargarBase() {
    this.loading = true;
    forkJoin({
      calif: this.api.getProvTopCalificacion(),
      punt:  this.api.getProvPuntualidad()
    }).subscribe({
      next: ({ calif, punt }) => {
        this.topCalif = calif;
        this.puntualidad = punt;
        if (this.viewReady) this.pintarBase();
        this.loading = false;
      },
      error: e => { console.error(e); this.loading = false; }
    });
  }

  private pintarBase() {
    this.grafTopCalificacion();
    this.grafPuntualidad();
  }

  // === acciones ===
  buscarPorProducto() {
    if (!this.idProducto) { this.mejoresPorProducto = []; this.relPorProducto = []; return; }
    forkJoin({
      mejores:  this.api.getProvMejoresPreciosProducto(this.idProducto),
      relacion: this.api.getProvRelacionProducto(this.idProducto)
    }).subscribe({
      next: ({ mejores, relacion }) => {
        this.mejoresPorProducto = mejores;
        this.relPorProducto    = relacion;
      },
      error: e => console.error(e)
    });
  }

  // === helpers ===
  private stars(n: number) {
    const v = Math.max(0, Math.min(5, Math.round(n)));
    return '★★★★★'.slice(0, v) + '☆☆☆☆☆'.slice(0, 5 - v);
  }

  money(n: number | null | undefined) { return '$' + Number(n || 0).toLocaleString(); }
  toNumber(v: any): number { return Number(v || 0); }

  private destruir(c?: Chart) { if (c) c.destroy(); }

  // === charts ===
  private grafTopCalificacion() {
    this.destruir(this.topCalifChart);
    const ctx = this.topCalifCanvas.nativeElement.getContext('2d'); if (!ctx) return;

    const top = this.topCalif

    this.topCalifChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: top.map(x => x.nombreComercial),
        datasets: [{
          label: 'Calificación',
          data: top.map(x => Number(x.calificacion) || 0),
          backgroundColor: 'rgba(222, 188, 0, 0.73)',
          borderColor: 'rgba(222, 188, 0, 0.73)',
          borderWidth: 1,
          maxBarThickness: 10,
          barThickness: 8,
          categoryPercentage: 0.7,
          barPercentage: 0.6
        }]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Top proveedores por calificación' },
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                const v = Number(ctx.parsed.x || 0);
                return ` ${v.toFixed(1)} / 5 ${this.stars(v)}`;
              }
            }
          }
        },
        scales: {
          x: { beginAtZero: true, suggestedMax: 5 }
        }
      }
    });
  }

  private grafPuntualidad() {
    this.destruir(this.puntualidadChart);
    const ctx = this.puntualidadCanvas.nativeElement.getContext('2d'); if (!ctx) return;

    const top = [...this.puntualidad]
      .sort((a,b)=>Number(b.porcentajeOrdenesATiempo)-Number(a.porcentajeOrdenesATiempo))
      .slice(0,5);

    this.puntualidadChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: top.map(x => x.nombreComercial),
        datasets: [
          {
            label: '% órdenes a tiempo',
            data: top.map(x => Number(x.porcentajeOrdenesATiempo) || 0),
            yAxisID: 'y',
            backgroundColor: 'rgba(203, 95, 7, 0.72)',
            borderColor: 'rgba(203, 95, 7, 0.72)',
            borderWidth: 1,
            maxBarThickness: 10,
            barThickness: 8,
            categoryPercentage: 0.7,
            barPercentage: 0.6
          },
          {
            label: 'Días de retraso (prom.)',
            data: top.map(x => Number(x.promedioDiasRetraso) || 0),
            yAxisID: 'y1',
            backgroundColor: 'rgba(255,206,86,0.6)',
            borderColor: 'rgba(255,206,86,1)',
            borderWidth: 1,
            maxBarThickness: 10,
            barThickness: 8
          }
        ]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Puntualidad de proveedores' },
          legend: { position: 'bottom' }
        },
        scales: {
          y: {
            position: 'left',
            beginAtZero: true,
            ticks: { callback: v => Number(v).toFixed(0) + '%' }
          },
          y1: {
            position: 'right',
            beginAtZero: true,
            grid: { drawOnChartArea: false }
          }
        }
      }
    });
  }
}
