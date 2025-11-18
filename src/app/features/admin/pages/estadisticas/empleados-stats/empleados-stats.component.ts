// src/app/features/admin/pages/estadisticas/empleados-stats/empleados-stats.component.ts
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EstadisticasService, EmpleadoSalario, ContratoDistribucion, 
         CargoCosto} from '../../../../../shared/services/estadisticasService.service';
import { Chart, ChartConfiguration, registerables } from 'chart.js';
import { forkJoin } from 'rxjs';

Chart.register(...registerables);
Chart.defaults.color = '#e5e7eb';         
Chart.defaults.font.family = '"Inter", system-ui, sans-serif';
Chart.defaults.font.size = 12;

@Component({
  selector: 'app-empleados-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empleados-stats.component.html',
  styleUrls: ['./empleados-stats.component.scss']
})
export class EmpleadosStatsComponent implements OnInit, AfterViewInit {
  @ViewChild('topSalariosChart') topSalariosCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('distribucionContratosChart') distribucionContratosCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('costosPorCargoChart') costosPorCargoCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('nominaDepartamentoChart') nominaDepartamentoCanvas!: ElementRef<HTMLCanvasElement>;

  loading = true;
  chartsReady = false;

  private topSalariosChart?: Chart;
  private distribucionContratosChart?: Chart;
  private costosPorCargoChart?: Chart;
  private nominaDepartamentoChart?: Chart;

  constructor(private estadisticasService: EstadisticasService) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.chartsReady = true;
    setTimeout(() => this.cargarDatos(), 0);
  }

  cargarDatos(): void {
  this.loading = true;

  forkJoin({
    topSalarios: this.estadisticasService.getTopSalarios(10),
    distribucion: this.estadisticasService.getDistribucionContratos(),
    costos: this.estadisticasService.getCostosPorCargo(),
  }).subscribe({
    next: (data) => {
      this.loading = false;

      setTimeout(() => {
        this.crearGraficoTopSalarios(data.topSalarios);
        this.crearGraficoDistribucionContratos(data.distribucion);
        this.crearGraficoCostosPorCargo(data.costos);
      }, 0);
    },
    error: (error) => {
      console.error('Error cargando estadísticas:', error);
      this.loading = false;
    }
  });
}

  crearGraficoTopSalarios(data: EmpleadoSalario[]): void {
  if (this.topSalariosChart) this.topSalariosChart.destroy();

  const ctx = this.topSalariosCanvas.nativeElement.getContext('2d');
  if (!ctx) return;

  this.topSalariosChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: data.map(e => `${e.nombreCompleto} (${e.cargo})`),
      datasets: [{
        label: 'Salario',
        data: data.map(e => Number(e.salario) || 0), // <- conversión segura
        backgroundColor: 'rgba(54, 162, 235, 0.6)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      indexAxis: 'y',
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        title: {
          display: true,
          text: 'Top Empleados por Salario',
          font: { size: 16 }
        },
        tooltip: {
          callbacks: {
            label: (ctx) => {
              const v = Number(ctx.parsed.x ?? ctx.parsed) || 0; // eje X porque indexAxis='y'
              return `Salario: $${v.toLocaleString()}`;
            }
          }
        }
      },
      scales: {
        x: {
          beginAtZero: true,
          ticks: {
            callback: (value) => '$' + Number(value).toLocaleString()
          }
        }
      }
    }
  });
}


 crearGraficoDistribucionContratos(data: ContratoDistribucion[]): void {
  if (this.distribucionContratosChart) this.distribucionContratosChart.destroy();

  const ctx = this.distribucionContratosCanvas.nativeElement.getContext('2d');
  if (!ctx) return;

  // cálcula porcentajes para leyendas/tooltip consistentes
  const counts = data.map(d => Number(d.cantidad) || 0);
  const total = counts.reduce((a, b) => a + b, 0) || 1;

  this.distribucionContratosChart = new Chart(ctx, {
    type: 'pie',
    data: {
      labels: data.map(d => d.tipoContrato),
      datasets: [{
        data: counts,
        backgroundColor: [
          'rgba(255, 99, 132, 0.6)',
          'rgba(54, 162, 235, 0.6)',
          'rgba(255, 206, 86, 0.6)',
          'rgba(75, 192, 192, 0.6)',
          'rgba(153, 102, 255, 0.6)'
        ]
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'bottom' },
        title: {
          display: true,
          text: 'Distribución por Tipo de Contrato',
          font: { size: 16 }
        },
        tooltip: {
          callbacks: {
            label: (context) => {
              const label = context.label || '';
              const value = Number(context.parsed) || 0;
              const percentage = ((value / total) * 100).toFixed(1);
              return `${label}: ${value.toLocaleString()} (${percentage}%)`;
            }
          }
        }
      }
    }
  });
}


 crearGraficoCostosPorCargo(data: CargoCosto[]): void {
  if (this.costosPorCargoChart) this.costosPorCargoChart.destroy();

  const ctx = this.costosPorCargoCanvas.nativeElement.getContext('2d');
  if (!ctx) return;

  this.costosPorCargoChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: data.map(c => c.cargo),
      datasets: [
        {
          label: 'Costo Total',
          data: data.map(c => Number(c.costoTotal)), 
          backgroundColor: 'rgba(255, 99, 132, 0.6)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1,
          yAxisID: 'y'
        },
        {
          label: 'Cantidad Empleados',
          data: data.map(c => c.cantidadEmpleados),
          backgroundColor: 'rgba(54, 162, 235, 0.6)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          yAxisID: 'y1'
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        title: { display: true, text: 'Costos y Cantidad por Cargo', font: { size: 16 } },
        tooltip: {
          callbacks: {
            // opcional: también formatea los tooltips del dataset de dinero
            label: (ctx) => {
              const label = ctx.dataset.label || '';
              const v = Number(ctx.parsed.y ?? ctx.parsed) || 0;
              return ctx.dataset.yAxisID === 'y'
                ? `${label}: $${v.toLocaleString()}`
                : `${label}: ${v.toLocaleString()}`;
            }
          }
        }
      },
      scales: {
        y: {
          type: 'linear',
          display: true,
          position: 'left',
          ticks: {
            // AQUÍ va el callback para el eje de dinero
            callback: (value) => '$' + Number(value).toLocaleString()
          }
        },
        y1: {
          type: 'linear',
          display: true,
          position: 'right',
          grid: { drawOnChartArea: false }
        }
      }
    }
  });
}


  ngOnDestroy(): void {
    if (this.topSalariosChart) this.topSalariosChart.destroy();
    if (this.distribucionContratosChart) this.distribucionContratosChart.destroy();
    if (this.costosPorCargoChart) this.costosPorCargoChart.destroy();
    if (this.nominaDepartamentoChart) this.nominaDepartamentoChart.destroy();
  }
}