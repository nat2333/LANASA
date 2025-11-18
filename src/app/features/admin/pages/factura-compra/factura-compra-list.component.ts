import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

import {
  FacturaCompraService,
  FacturaCompra,
  CrearFacturaCompraRequest,
  ActualizarFacturaCompraRequest
} from '../../../../shared/services/factura-compra.service';

import {
  OrdenCompraService,
  OrdenCompra
} from '../../../../shared/services/orden-compra.service';

import {
  EstadoFacturaService,
  EstadoFactura
} from '../../../../shared/services/estado-factura.service'; // 👈 crea este servicio si aún no existe

@Component({
  selector: 'app-admin-factura-compra-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './factura-compra-list.component.html',
  styleUrls: ['./factura-compra-list.component.scss']
})
export class FacturaCompraListComponent implements OnInit {

  private api   = inject(FacturaCompraService);
  private fb    = inject(FormBuilder);
  private ordenApi = inject(OrdenCompraService);
  private estadoFacturaApi = inject(EstadoFacturaService);

  form!: FormGroup;

  facturas: FacturaCompra[] = [];
  ordenes: OrdenCompra[] = [];
  estadosFactura: EstadoFactura[] = [];
  ordenSeleccionada: OrdenCompra | null = null;

  loading = false;
  saving  = false;
  error: string | null = null;

  editMode = false;
  editingId: number | null = null;

  ngOnInit(): void {
    this.buildForm();
    this.cargarOrdenes();
    this.cargarEstadosFactura();
    this.cargarFacturas();

    // cuando cambia la orden seleccionada, actualizamos referencia
    this.form.get('idOrdenCompra')?.valueChanges.subscribe(id => {
      const valor = Number(id);
      this.ordenSeleccionada =
        this.ordenes.find(o => o.idOrdenCompra === valor) ?? null;
    });
  }

  private buildForm() {
    this.form = this.fb.group({
      idOrdenCompra: [null, [Validators.required, Validators.min(1)]],
      montoTotal: [null, [Validators.required, Validators.min(0.01)]],
      idEstadoFactura: [null, [Validators.required, Validators.min(1)]]
    });
  }

  // --- Cargas iniciales ------------------------------------------------------

  private cargarFacturas() {
    this.loading = true;
    this.error = null;

    this.api.listar().subscribe({
      next: (lista) => {
        this.facturas = lista ?? [];
        this.loading = false;
      },
      error: (e) => {
        console.error('[FacturaCompra] error listar:', e);
        this.error = 'No se pudieron cargar las facturas de compra.';
        this.facturas = [];
        this.loading = false;
      }
    });
  }

  private cargarOrdenes() {
    this.ordenApi.getAll().subscribe({
      next: (data) => {
        this.ordenes = data ?? [];
      },
      error: (e) => {
        console.error('[FacturaCompra] error cargar ordenes:', e);
      }
    });
  }

  private cargarEstadosFactura() {
    this.estadoFacturaApi.getAll().subscribe({
      next: (data) => {
        this.estadosFactura = data ?? [];
      },
      error: (e) => {
        console.error('[FacturaCompra] error cargar estados factura:', e);
      }
    });
  }

  // --- Crear / actualizar ----------------------------------------------------

  onSubmit() {
    const idEstado = this.form.get('idEstadoFactura')?.value;

    if (this.editMode) {
      // En modo edición solo actualizamos el idEstadoFactura
      if (idEstado == null) {
        this.form.get('idEstadoFactura')?.markAsTouched();
        return;
      }

      const payload: ActualizarFacturaCompraRequest = {
        idEstadoFactura: Number(idEstado)
      };

      if (!this.editingId) return;

      this.saving = true;
      this.api.actualizar(this.editingId, payload).subscribe({
        next: (facturaActualizada) => {
          const idx = this.facturas.findIndex(f => f.idFacturaCompra === facturaActualizada.idFacturaCompra);
          if (idx !== -1) this.facturas[idx] = facturaActualizada;
          this.saving = false;
          this.resetForm();
        },
        error: (e) => {
          console.error('[FacturaCompra] error actualizar:', e);
          this.error = 'No se pudo actualizar la factura.';
          this.saving = false;
        }
      });

    } else {
      // Crear
      if (this.form.invalid) {
        this.form.markAllAsTouched();
        return;
      }

      const payload: CrearFacturaCompraRequest = {
        idOrdenCompra: Number(this.form.get('idOrdenCompra')?.value),
        montoTotal: Number(this.form.get('montoTotal')?.value),
        idEstadoFactura: Number(this.form.get('idEstadoFactura')?.value)
      };

      this.saving = true;
      this.api.crear(payload).subscribe({
        next: (nueva) => {
          this.facturas.unshift(nueva);
          this.saving = false;
          this.resetForm();
        },
        error: (e) => {
          console.error('[FacturaCompra] error crear:', e);
          this.error = 'No se pudo crear la factura de compra.';
          this.saving = false;
        }
      });
    }
  }

  onEdit(f: FacturaCompra) {
    this.editMode = true;
    this.editingId = f.idFacturaCompra;

    // buscamos la orden por el número que trae el DTO
    const orden = this.ordenes.find(o => o.numero === f.numeroOrden) ?? null;
    this.ordenSeleccionada = orden;

    // buscamos el id del estado a partir del nombre
    const estado = this.estadosFactura.find(
      e => e.nombre === f.nombreEstadoFactura
    ) ?? null;

    this.form.reset({
      idOrdenCompra: orden ? orden.idOrdenCompra : null,
      montoTotal: f.montoTotal,
      idEstadoFactura: estado ? estado.idEstadoFactura : null
    });

    // solo permitimos cambiar el estado en edición
    this.form.get('idOrdenCompra')?.disable({ emitEvent: false });
    this.form.get('montoTotal')?.disable({ emitEvent: false });
  }

  resetForm() {
    this.editMode = false;
    this.editingId = null;
    this.ordenSeleccionada = null;
    this.form.enable({ emitEvent: false });
    this.form.reset();
  }

  // --- Acciones sobre la factura --------------------------------------------

  onToggleEstado(f: FacturaCompra) {
    this.api.cambiarEstado(f.idFacturaCompra).subscribe({
      next: (actualizada) => {
        const idx = this.facturas.findIndex(x => x.idFacturaCompra === actualizada.idFacturaCompra);
        if (idx !== -1) this.facturas[idx] = actualizada;
      },
      error: (e) => {
        console.error('[FacturaCompra] error cambiarEstado:', e);
        this.error = 'No se pudo cambiar el estado de la factura.';
      }
    });
  }

  onDelete(f: FacturaCompra) {
    if (!confirm(`¿Eliminar la factura ${f.numero}?`)) return;

    this.api.eliminar(f.idFacturaCompra).subscribe({
      next: () => {
        this.facturas = this.facturas.filter(x => x.idFacturaCompra !== f.idFacturaCompra);
      },
      error: (e) => {
        console.error('[FacturaCompra] error eliminar:', e);
        this.error = 'No se pudo eliminar la factura.';
      }
    });
  }

  // --- Helpers de template ---------------------------------------------------

  trackById(_index: number, item: FacturaCompra) {
    return item.idFacturaCompra;
  }
}
