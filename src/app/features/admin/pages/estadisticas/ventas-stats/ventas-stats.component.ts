import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { Chart, registerables } from 'chart.js';
import {
  EstadisticasService,
  VentasMensuales,
  ClienteTopVentas,
  ProductoMasVendido
} from '../../../../../shared/services/estadisticasService.service';

Chart.register(...registerables);
Chart.defaults.color = '#e5e7eb';         
Chart.defaults.font.family = '"Inter", system-ui, sans-serif';
Chart.defaults.font.size = 12;

@Component({
  selector: 'app-ventas-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ventas-stats.component.html',
  styleUrls: ['./ventas-stats.component.scss']
})
export class VentasStatsComponent implements OnInit, AfterViewInit {

  @ViewChild('ventasMensualesChart') ventasMensualesRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('topClientesChart')     topClientesRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('productosChart')       productosRef!: ElementRef<HTMLCanvasElement>;

  loading = true;

  ventasMensuales: VentasMensuales[] = [];
  topClientes:    ClienteTopVentas[] = [];
  productos:      ProductoMasVendido[] = [];

  private ventasMensualesChart?: Chart;
  private topClientesChart?: Chart;
  private productosChart?: Chart;

  constructor(private api: EstadisticasService) {}

  ngOnInit(): void {}
  ngAfterViewInit(): void { this.cargar(); }

  private cargar() {
    this.loading = true;
    forkJoin({
      mensuales: this.api.getVentasMensuales(),
      clientes:  this.api.getTopClientesVentas(),
      productos: this.api.getProductosMasVendidos()
    }).subscribe({
      next: ({ mensuales, clientes, productos }) => {
        // ordena por fecha
        this.ventasMensuales = [...mensuales].sort((a,b) =>
          a.anio === b.anio ? a.mes - b.mes : a.anio - b.anio
        );

        // top-10 por total comprado
        this.topClientes = [...clientes]
          .sort((a,b) => Number(b.totalComprado) - Number(a.totalComprado))
          .slice(0, 10);

        // top-10 por cantidad vendida
        this.productos = [...productos]
          .sort((a,b) => Number(b.cantidadVendida) - Number(a.cantidadVendida))
          .slice(0, 10);

        this.pintar();
        this.loading = false;
      },
      error: e => { console.error('Ventas stats error:', e); this.loading = false; }
    });
  }

  private pintar() {
    this.grafVentasMensuales();
    this.grafTopClientes();
    this.grafProductos();
  }

  // -------- Charts ----------
  private grafVentasMensuales() {
    this.destruir(this.ventasMensualesChart);
    const ctx = this.ventasMensualesRef.nativeElement.getContext('2d'); if (!ctx) return;

    const labels = this.ventasMensuales.map(v => `${v.anio}-${String(v.mes).padStart(2,'0')}`);
    const totales = this.ventasMensuales.map(v => Number(v.totalVentas) || 0);
    const facturas = this.ventasMensuales.map(v => Number(v.numeroFacturas) || 0);

    this.ventasMensualesChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            type: 'bar',
            label: 'Total ventas ($)',
            data: totales,
            yAxisID: 'y',
            backgroundColor: 'rgba(99, 102, 241, 0.45)',
            borderColor: 'rgba(99, 102, 241, 1)',
            borderWidth: 1
          },
          {
            type: 'line',
            label: '# Facturas',
            data: facturas,
            yAxisID: 'y1',
            fill: false,
            tension: 0.25,
            borderColor: 'rgba(234, 179, 8, 1)',
            pointRadius: 3
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Ventas mensuales' },
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                if (ctx.dataset.yAxisID === 'y') {
                  const v = Number(ctx.parsed.y) || 0;
                  return `Total: $${v.toLocaleString()}`;
                }
                const v = Number(ctx.parsed.y) || 0;
                return `# Facturas: ${v.toLocaleString()}`;
              }
            }
          }
        },
        scales: {
          y: {
            position: 'left',
            beginAtZero: true,
            ticks: { callback: v => '$' + Number(v).toLocaleString() }
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

  private grafTopClientes() {
    this.destruir(this.topClientesChart);
    const ctx = this.topClientesRef.nativeElement.getContext('2d'); if (!ctx) return;

    const labels = this.topClientes.map(c =>
      `${c.correo || c.telefono || c.ciudad || 'Cliente'}`
    );
    const totales = this.topClientes.map(c => Number(c.totalComprado) || 0);

    this.topClientesChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Total comprado ($)',
          data: totales,
          backgroundColor: 'rgba(54, 162, 235, 0.6)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          maxBarThickness: 14,
          barThickness: 12
        }]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Top 10 clientes por compras' },
          legend: { display: false },
          tooltip: {
            callbacks: { label: (ctx) => '$' + (Number(ctx.parsed.x)||0).toLocaleString() }
          }
        },
        scales: {
          x: { beginAtZero: true, ticks: { callback: v => '$' + Number(v).toLocaleString() } }
        }
      }
    });
  }

  private grafProductos() {
    this.destruir(this.productosChart);
    const ctx = this.productosRef.nativeElement.getContext('2d'); if (!ctx) return;

    const labels = this.productos.map(p => p.nombreProducto);
    const cantidades = this.productos.map(p => Number(p.cantidadVendida) || 0);
    const totales = this.productos.map(p => Number(p.totalVendido) || 0);

    this.productosChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Cantidad vendida',
            data: cantidades,
            yAxisID: 'y',
            backgroundColor: 'rgba(75, 192, 192, 0.6)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          },
          {
            label: 'Total vendido ($)',
            data: totales,
            yAxisID: 'y1',
            backgroundColor: 'rgba(153, 102, 255, 0.45)',
            borderColor: 'rgba(153, 102, 255, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Productos más vendidos' },
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: (ctx) => ctx.dataset.yAxisID === 'y'
                ? `Cantidad: ${Number(ctx.parsed.y||0).toLocaleString()}`
                : `Total: $${Number(ctx.parsed.y||0).toLocaleString()}`
            }
          }
        },
        scales: {
          y:  { position: 'left', beginAtZero: true },
          y1: { position: 'right', beginAtZero: true, grid: { drawOnChartArea: false },
                ticks: { callback: v => '$' + Number(v).toLocaleString() } }
        }
      }
    });
  }

  private destruir(c?: Chart) { if (c) c.destroy(); }

  money(n: number | null | undefined) { return '$' + Number(n || 0).toLocaleString(); }
}
