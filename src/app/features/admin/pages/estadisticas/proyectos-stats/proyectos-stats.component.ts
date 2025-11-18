import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import {
  EstadisticasService,
  ProyectoPresupuesto,
  ProyectoPorDepartamento,
  ProyectoPorCliente,
} from '../../../../../shared/services/estadisticasService.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);
Chart.defaults.color = '#e5e7eb';         
Chart.defaults.font.family = '"Inter", system-ui, sans-serif';
Chart.defaults.font.size = 12;

@Component({
  selector: 'app-proyectos-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './proyectos-stats.component.html',
  styleUrls: ['./proyectos-stats.component.scss'],
})
export class ProyectosStatsComponent implements OnInit, AfterViewInit {
  @ViewChild('presupuestoChart') presupuestoCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('porDeptoChart')    porDeptoCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('porClienteChart')  porClienteCanvas!: ElementRef<HTMLCanvasElement>;

  loading = true;
  viewReady = false;

  // data
  presupuestos: ProyectoPresupuesto[] = [];
  porDepto: ProyectoPorDepartamento[] = [];
  porCliente: ProyectoPorCliente[] = [];

  // charts
  private presupuestoChart?: Chart;
  private porDeptoChart?: Chart;
  private porClienteChart?: Chart;

  constructor(private api: EstadisticasService) {}

  ngOnInit(): void {}
  ngAfterViewInit(): void {
    this.viewReady = true;
    this.cargar();
    if (!this.loading && (this.presupuestos.length || this.porDepto.length || this.porCliente.length)) {
    this.pintar();
  }
  }

 private cargar() {
  this.loading = true;
  forkJoin({
    pres: this.api.getProyectosPresupuestos(),
    dept: this.api.getProyectosPorDepartamento(),
    cli:  this.api.getProyectosPorCliente(),
  }).subscribe({
    next: ({ pres, dept, cli }) => {
      this.presupuestos = pres ?? [];
      this.porDepto     = dept ?? [];
      this.porCliente   = cli ?? [];

      console.log('presupuestos', this.presupuestos);
      console.log('porDepto', this.porDepto);
      console.log('porCliente', this.porCliente);

      // 1) primero desactivamos el loading para que el *ngIf cree los canvas
      this.loading = false;

      // 2) dejamos que Angular renderice la vista y luego pintamos
      if (this.viewReady) {
        setTimeout(() => this.pintar(), 0);
      }
    },
    error: (e) => { console.error('ERROR proyectos', e); this.loading = false; }
  });
}

  private pintar() {
    if (this.presupuestos?.length) this.grafPresupuesto();
    if (this.porDepto?.length)     this.grafPorDepto();
    if (this.porCliente?.length)   this.grafPorCliente();
  }

  private grafPresupuesto() {
    if (this.presupuestoChart) this.presupuestoChart.destroy();
    const ctx = this.presupuestoCanvas.nativeElement.getContext('2d'); if (!ctx) return;

    const top = [...this.presupuestos]
      .sort((a,b)=>Number(b.presupuestoAprobado)-Number(a.presupuestoAprobado))
      .slice(0, 10);

    const labels = top.map(p => `${p.codigo} – ${p.nombreProyecto}`);
    const aprob = top.map(p => Number(p.presupuestoAprobado) || 0);
    const util  = top.map(p => Number(p.presupuestoUtilizado) || 0);

    this.presupuestoChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Aprobado',
            data: aprob,
            backgroundColor: 'rgba(129, 140, 248, 0.85)',  
            borderColor:     'rgba(191, 219, 254, 1)',
            borderWidth: 1,
          },
          {
            label: 'Utilizado',
            data: util,
            backgroundColor: 'rgba(236, 72, 153, 0.85)',   
            borderColor:     'rgba(251, 207, 232, 1)',
            borderWidth: 1,
          },
        ],
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Top proyectos por presupuesto aprobado' },
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                const v = Number(ctx.parsed.x ?? ctx.parsed) || 0;
                return `${ctx.dataset.label}: $${v.toLocaleString()}`;
              }
            }
          }
        },
        scales: {
          x: {
            beginAtZero: true,
            ticks: { callback: (v) => '$' + Number(v).toLocaleString() }
          }
        }
      }
    });
  }

  // 2) Proyectos por departamento: #proyectos (bar) + presupuesto utilizado (línea)
  private grafPorDepto() {
    if (this.porDeptoChart) this.porDeptoChart.destroy();
    const ctx = this.porDeptoCanvas.nativeElement.getContext('2d'); if (!ctx) return;

    const labels = this.porDepto.map(d => d.nombreDepartamento);
    const numProy = this.porDepto.map(d => Number(d.numeroProyectos) || 0);
    const utilTot = this.porDepto.map(d => Number(d.presupuestoTotalUtilizado) || 0);

    this.porDeptoChart = new Chart(ctx, {
      data: {
        labels,
        datasets: [
          {
            type: 'bar',
            label: '# Proyectos',
            data: numProy,
            yAxisID: 'y',
            backgroundColor: 'rgba(56, 189, 248, 0.9)',
            borderColor: 'rgba(250, 250, 255, 0.9)',
            borderWidth: 1
          },
          {
            type: 'line',
            label: 'Presupuesto utilizado',
            data: utilTot,
            yAxisID: 'y1',
            tension: 0.25,
            borderWidth: 2,
            pointRadius: 3,
            borderColor: 'rgba(244, 114, 182, 0.9)',
            backgroundColor: 'rgba(250, 250, 255, 0.9)',
            fill: false
          }
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Proyectos y presupuesto utilizado por departamento' },
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                const v = Number(ctx.parsed.y ?? ctx.parsed) || 0;
                return ctx.dataset.yAxisID === 'y1'
                  ? `${ctx.dataset.label}: $${v.toLocaleString()}`
                  : `${ctx.dataset.label}: ${v.toLocaleString()}`;
              }
            }
          }
        },
        scales: {
          y: { position: 'left', beginAtZero: true },
          y1: {
            position: 'right',
            grid: { drawOnChartArea: false },
            ticks: { callback: (v) => '$' + Number(v).toLocaleString() },
            beginAtZero: true
          }
        }
      }
    });
  }

  // 3) Top 10 clientes por presupuesto total aprobado
  private grafPorCliente() {
    if (this.porClienteChart) this.porClienteChart.destroy();
    const ctx = this.porClienteCanvas.nativeElement.getContext('2d'); if (!ctx) return;

    const top = [...this.porCliente]
      .sort((a,b)=>Number(b.presupuestoTotalAprobado)-Number(a.presupuestoTotalAprobado))
      .slice(0,10);

    const labels = top.map(c => c.correoCliente);
    const aprob  = top.map(c => Number(c.presupuestoTotalAprobado) || 0);

    this.porClienteChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Presupuesto total aprobado',
          data: aprob,
          backgroundColor: 'rgba(168, 85, 247, 0.75)',
          borderColor: 'rgba(233, 213, 255, 1)',
          borderWidth: 1
        }]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: { display: true, text: 'Top clientes por presupuesto aprobado' },
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: (ctx) => {
                const v = Number(ctx.parsed.x ?? ctx.parsed) || 0;
                return '$' + v.toLocaleString();
              }
            }
          }
        },
        scales: {
          x: {
            beginAtZero: true,
            ticks: { callback: (v) => '$' + Number(v).toLocaleString() }
          }
        }
      }
    });
  }

topPresupuestos(): ProyectoPresupuesto[] {
    return [...(this.presupuestos ?? [])]
}
  // helpers
  money(n: number | null | undefined) { return '$' + Number(n || 0).toLocaleString(); }
  pct(n: number | null | undefined) { return (Number(n || 0)).toFixed(1) + '%'; }
}
