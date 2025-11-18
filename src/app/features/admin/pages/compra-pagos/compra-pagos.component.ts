import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn
} from '@angular/forms';

import {
  CompraPagosService,
  PagoCompra
} from '../../../../shared/services/compra-pagos.service';

import {
  FacturaCompraService,
  FacturaCompra
} from '../../../../shared/services/factura-compra.service';

import {
  MetodoPagoService,
  MetodoPago
} from '../../../../shared/services/metodo-pago.service';

@Component({
  selector: 'app-compra-pagos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compra-pagos.component.html',
  styleUrls: ['./compra-pagos.component.scss']
})
export class CompraPagosComponent implements OnInit {

  form!: FormGroup;
  pagos: PagoCompra[] = [];

  facturas: FacturaCompra[] = [];
  metodosPago: MetodoPago[] = [];
  facturaSeleccionada: FacturaCompra | null = null;

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private compraPagosService: CompraPagosService,
    private facturaService: FacturaCompraService,
    private metodoPagoService: MetodoPagoService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadFacturas();
    this.loadMetodosPago();
    this.loadPagos();

    // cuando cambia la factura seleccionada, actualizamos referencia
    this.form.get('idFacturaCompra')?.valueChanges.subscribe(id => {
      this.facturaSeleccionada =
        this.facturas.find(f => f.idFacturaCompra === id) ?? null;
      // vuelve a evaluar validaciones cruzadas
      this.form.updateValueAndValidity({ onlySelf: false, emitEvent: false });
    });
  }

  private buildForm(): void {
    this.form = this.fb.group(
      {
        idFacturaCompra: [null, [Validators.required, Validators.min(1)]],
        idMetodoPago: [null, [Validators.required, Validators.min(1)]],
        fechaPago: ['', [Validators.required]],
        monto: [0, [Validators.required, Validators.min(0.01)]]
      },
      {
        validators: this.validarFechaYMonto()
      }
    );
  }

  private validarFechaYMonto(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const idFactura = group.get('idFacturaCompra')?.value as number | null;
      const fechaPago = group.get('fechaPago')?.value as string | null; // 'YYYY-MM-DD'
      const monto = group.get('monto')?.value as number | null;

      const factura = this.facturas.find(f => f.idFacturaCompra === idFactura);

      const errors: any = {};

      if (factura && fechaPago) {
        // fechaFactura viene tipo 'YYYY-MM-DDTHH:mm:ss'
        const fechaFactura = factura.fechaFactura?.substring(0, 10); // nos quedamos con la parte de fecha
        if (fechaFactura && fechaPago < fechaFactura) {
          errors.fechaPagoAntesFactura = true;
        }
      }

      if (factura && monto != null) {
        if (monto > factura.montoTotal) {
          errors.montoMayorFactura = true;
        }
      }

      return Object.keys(errors).length ? errors : null;
    };
  }

  private loadFacturas(): void {
    this.facturaService.listar().subscribe({
      next: data => {
        this.facturas = data;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar facturas de compra';
      }
    });
  }

  private loadMetodosPago(): void {
    this.metodoPagoService.getAll().subscribe({
      next: data => {
        this.metodosPago = data;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar métodos de pago';
      }
    });
  }

  loadPagos(): void {
    this.loading = true;
    this.error = null;

    this.compraPagosService.getAll().subscribe({
      next: (data) => {
        this.pagos = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar pagos';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submit();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.value;

    if (this.editMode) {
      alert('Los pagos no se pueden editar, solo crear y eliminar.');
      return;
    }

    this.create(body);
  }

  private create(body: any): void {
    this.loading = true;
    this.error = null;

    this.compraPagosService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.reset();
        this.loadPagos();
      },
      error: () => {
        this.error = 'Error al crear pago';
        this.loading = false;
      }
    });
  }

  editPago(p: PagoCompra): void {
    this.editMode = true;
    this.editingId = p.idPago;

    this.form.patchValue({
      idFacturaCompra: p.idFacturaCompra,
      idMetodoPago: p.idMetodoPago,
      fechaPago: p.fechaPago?.substring(0, 10),
      monto: p.monto
    });

    // actualizar facturaSeleccionada
    this.facturaSeleccionada =
      this.facturas.find(f => f.idFacturaCompra === p.idFacturaCompra) ?? null;

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deletePago(p: PagoCompra): void {
    if (!confirm(`¿Eliminar pago #${p.idPago}?`)) return;

    this.loading = true;
    this.error = null;

    this.compraPagosService.delete(p.idPago).subscribe({
      next: () => {
        this.loading = false;
        this.loadPagos();
      },
      error: () => {
        this.error = 'Error al eliminar pago';
        this.loading = false;
      }
    });
  }

  toggleEstado(row: PagoCompra): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idPago}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.compraPagosService.toggleEstado(row.idPago).subscribe({
      next: () => {
        this.loading = false;
        this.loadPagos();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el detalle pago compra`;
      }
    });
  }

  reset(): void {
    this.form.reset({
      idFacturaCompra: null,
      idMetodoPago: null,
      fechaPago: '',
      monto: 0
    });

    this.editMode = false;
    this.editingId = null;
    this.facturaSeleccionada = null;
  }

  cancelEdit(): void {
    this.reset();
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
