import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { EstadisticasService, ProductoUtilidad, CategoriaUtilidad } from '../../../../../shared/services/estadisticasService.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);
Chart.defaults.color = '#f9fbffff';         
Chart.defaults.font.family = '"Inter", system-ui, sans-serif';
Chart.defaults.font.size = 12;
@Component({
  selector: 'app-productos-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productos-stats.component.html',
  styleUrl: './productos-stats.component.scss'
})
export class ProductosStatsComponent implements OnInit, AfterViewInit {

  @ViewChild('topUtilidadRealChart') topUtilidadRealCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('margenRealPotChart')   margenRealPotCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('categoriasChart')       categoriasCanvas!: ElementRef<HTMLCanvasElement>;

  loading = true;
  viewReady = false; 
  
  productos: ProductoUtilidad[] = [];
  categorias: CategoriaUtilidad[] = [];

  private topUtilidadRealChart?: Chart;
  private margenRealPotChart?: Chart;
  private categoriasChart?: Chart;

  constructor(private stats: EstadisticasService) {}

  ngOnInit(): void { this.cargar(); }
  ngAfterViewInit(): void {
    this.viewReady = true;
    this.cargar(); 
  }

  private cargar() {
    this.loading = true;
    forkJoin({
      productos: this.stats.getUtilidadProductos(),
      categorias: this.stats.getUtilidadPorCategoria()
    }).subscribe({
      next: ({ productos, categorias }) => {
        this.productos = productos
        this.categorias = categorias;

        if (this.viewReady &&
            this.topUtilidadRealCanvas?.nativeElement &&
            this.margenRealPotCanvas?.nativeElement &&
            this.categoriasCanvas?.nativeElement) {
          this.pintar();
        }
        this.loading = false;
      },
      error: (e) => {
        console.error('Error cargando stats de productos', e);
        this.loading = false;
      }
    });
  }

  private pintar() {
    this.grafTopUtilidadReal();
    this.grafMargenRealVsPotencial();
    this.grafCategorias();
  }

  private grafTopUtilidadReal() {
    if (this.topUtilidadRealChart) this.topUtilidadRealChart.destroy();
    const ctx = this.topUtilidadRealCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    const top = this.productos.slice(0, 10);
    const labels = top.map(p => `${p.nombre} (${p.sku})`);
    const utiles = top.map(p => Number(p.utilidadReal) || 0);

    this.topUtilidadRealChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Utilidad real',
          data: utiles,
          backgroundColor: 'rgba(217, 54, 235, 0.6)',
          borderColor: 'rgba(242, 212, 255, 1)',
          borderWidth: 1
        }]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Top productos por utilidad real ($)' },
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                const v = Number(ctx.parsed.x ?? ctx.parsed) || 0;
                return ' $' + v.toLocaleString();
              }
            }
          }
        },
        scales: {
          x: {
            ticks: { callback: (v) => '$' + Number(v).toLocaleString() },
            beginAtZero: true
          }
        }
      }
    });
  }

  private grafMargenRealVsPotencial() {
    if (this.margenRealPotChart) this.margenRealPotChart.destroy();
    const ctx = this.margenRealPotCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    const top = this.productos.slice(0, 10);
    const labels = top.map(p => p.sku);
    const real = top.map(p => Number(p.utilidadRealPorcentaje) || 0);
    const potencial = top.map(p => Number(p.utilidadmPotencialPorcentaje) || 0);

    this.margenRealPotChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Margen real %',
            data: real,
            backgroundColor: 'rgba(217, 54, 235, 0.6)',
            borderColor: 'rgba(242, 212, 255, 1)',
            borderWidth: 1
          },
          {
            label: 'Margen potencial %',
            data: potencial,
            backgroundColor: 'rgba(54, 162, 235, 0.35)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Margen real vs. potencial (Top 10 por utilidad)' },
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: (ctx) => `${ctx.dataset.label}: ${(Number(ctx.parsed.y)||0).toFixed(1)}%`
            }
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: { callback: (v) => Number(v).toFixed(0) + '%' }
          }
        }
      }
    });
  }

  // 3) Utilidad promedio % por categoría + cantidad de productos
  private grafCategorias() {
    if (this.categoriasChart) this.categoriasChart.destroy();
    const ctx = this.categoriasCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    const labels = this.categorias.map(c => c.categoria);
    const margenPct = this.categorias.map(c => Number(c.utilidadPromedioPorcentaje) || 0);
    const cant = this.categorias.map(c => Number(c.cantidadProductos) || 0);

    this.categoriasChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Margen promedio %',
            data: margenPct,
            yAxisID: 'y',
            backgroundColor: 'rgba(153, 102, 255, 0.6)',
            borderColor: 'rgba(153, 102, 255, 1)',
            borderWidth: 1
          },
          {
            label: 'Cantidad de productos',
            data: cant,
            yAxisID: 'y1',
            backgroundColor: 'rgba(54, 162, 235, 0.35)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Categorías: margen promedio y cantidad' },
          legend: { position: 'bottom' }
        },
        scales: {
          y: {
            position: 'left',
            ticks: { callback: (v) => Number(v).toFixed(0) + '%' },
            beginAtZero: true
          },
          y1: {
            position: 'right',
            grid: { drawOnChartArea: false },
            beginAtZero: true
          }
        }
      }
    });
  }

  // ========== Tabla helpers ==========
  money(n: number | null | undefined) { return '$' + Number(n || 0).toLocaleString(); }
  pct(n: number | null | undefined) { return (Number(n || 0)).toFixed(1) + '%'; }


}
