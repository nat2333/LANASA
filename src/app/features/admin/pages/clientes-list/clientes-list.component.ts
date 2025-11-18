import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { FormsModule } from '@angular/forms'; 
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';
import { forkJoin } from 'rxjs';

interface Cliente {
  id: number;
  estado: boolean;
  direccion: string | null;
  pais: string | null;
  ciudad: string | null;
  telefono: string;
  correo: string;
  idTipoCliente: number;
  tipoCliente: string;
  // Empresa
  nombreEmpresa?: string | null;
  rut?: string | null;
  razonSocial?: string | null;
  // Persona Natural
  cedula?: string | null;
  primerNombre?: string | null;
  segundoNombre?: string | null;
  primerApellido?: string | null;
  segundoApellido?: string | null;
}

interface TipoCliente {
  id: number;
  tipo: string;  
  estado: boolean;
}

@Component({
  selector: 'app-clientes-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,  FormsModule],
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.scss'],
})
export class ClientesListComponent implements OnInit {
  form!: FormGroup;
  clientes: Cliente[] = [];
  clientesFiltrados: Cliente[] = [];
  tiposCliente: TipoCliente[] = [];
  loading = false;
  error: string | null = null;
  editMode = false;

  tipoSeleccionado: 'empresa' | 'persona natural' | null = null;
  filtroTipoCliente: number | string = '';
  private base = `${environment.apiUrl}/clientes`;
  private baseTipoCliente = `${environment.apiUrl}/tipo-cliente`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],                 
      idTipoCliente:['', Validators.required],       
      direccion: [''],
      pais: [''],
      ciudad: [''],
      telefono: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],

       // Campos de Empresa
      nombreEmpresa: [''],
      rut: [''],
      razonSocial: [''],
      
      // Campos de Persona Natural
      cedula: [''],
      primerNombre: [''],
      segundoNombre: [''],
      primerApellido: [''],
      segundoApellido: [''],
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    forkJoin({
      clientes: this.http.get<Cliente[]>(this.base),
      tiposCliente: this.http.get<TipoCliente[]>(this.baseTipoCliente)
    }).subscribe({
      next: data => {
        this.clientes = data.clientes || [];
        this.tiposCliente = data.tiposCliente.filter(tc => tc.estado) || [];
        this.loading = false;
        this.error = null;

        this.form.get('idTipoCliente')?.valueChanges.subscribe(value => {
          this.onTipoClienteChange(value);
        });
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudieron cargar los clientes';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.getRawValue();

    let req;

    if (this.editMode && v.id != null) {
      this.actualizarCliente(v);
    } else {
      this.crearCliente(v);
    }
  }

  edit(row: Cliente): void {
    this.editMode = true;

    const tipo = this.tiposCliente.find(tc => tc.id === row.idTipoCliente);
    if (tipo) {
      this.onTipoClienteChange(row.idTipoCliente);
    }

    this.form.patchValue({
      id: row.id,
      idTipoCliente: row.idTipoCliente,
      direccion: row.direccion || '',
      pais: row.pais || '',
      ciudad: row.ciudad || '',
      telefono: row.telefono,
      correo: row.correo,
      // Empresa
      nombreEmpresa: row.nombreEmpresa || '',
      rut: row.rut || '',
      razonSocial: row.razonSocial || '',
      // Persona
      cedula: row.cedula || '',
      primerNombre: row.primerNombre || '',
      segundoNombre: row.segundoNombre || '',
      primerApellido: row.primerApellido || '',
      segundoApellido: row.segundoApellido || '',
    });
    this.form.get('idTipoCliente')?.disable();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.tipoSeleccionado = null;
    this.clearValidators();
    
    this.form.reset({
      id: null,
      idTipoCliente: '',
      direccion: '',
      pais: '',
      ciudad: '',
      telefono: '',
      correo: '',
      nombreEmpresa: '',
      rut: '',
      razonSocial: '',
      cedula: '',
      primerNombre: '',
      segundoNombre: '',
      primerApellido: '',
      segundoApellido: '',
    });
    
    this.form.get('idTipoCliente')?.enable();
  }

  remove(row: Cliente): void {
    if (!confirm(`¿Eliminar cliente con correo "${row.correo}"?`)) return;

    this.loading = true;
    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo desactivar/eliminar el cliente';
      },
    });
  }

  toggleEstado(row: Cliente): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el cliente "${row.correo}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.id}/estado`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el cliente`;
      },
    });
  }

  buscarIdTipoCliente(nombre: string): number | string {
    const tipo = this.tiposCliente.find(tc => tc.tipo === nombre);
    return tipo ? tipo.id : '';
  }

  onTipoClienteChange(idTipo: string | number): void {
    if (!idTipo) {
      this.tipoSeleccionado = null;
      this.clearValidators();
      return;
    }

    const tipo = this.tiposCliente.find(tc => tc.id === Number(idTipo));
    
    if (!tipo) {
      this.tipoSeleccionado = null;
      return;
    }

    const tipoNombre = tipo.tipo.toUpperCase().trim();;
    
    // Limpiar validadores anteriores
    this.clearValidators();
    
    if (tipoNombre === 'EMPRESA' || tipoNombre.includes('EMPRESA')) {
      this.tipoSeleccionado = 'empresa';
      // Agregar validadores para empresa
      this.tipoSeleccionado = 'empresa';
      this.form.get('razonSocial')?.setValidators([Validators.required, Validators.maxLength(150)]);
      this.form.get('nombreEmpresa')?.setValidators([Validators.maxLength(150)]);
      this.form.get('rut')?.setValidators([Validators.maxLength(40)]);
    } else if (tipoNombre === 'PERSONA NATURAL' || 
             tipoNombre.includes('PERSONA') || 
             tipoNombre.includes('NATURAL')) {
      this.tipoSeleccionado = 'persona natural';
      // Agregar validadores para persona natural
      this.tipoSeleccionado = 'persona natural';
      this.form.get('primerNombre')?.setValidators([Validators.required, Validators.maxLength(60)]);
      this.form.get('segundoNombre')?.setValidators([Validators.maxLength(60)]);
      this.form.get('primerApellido')?.setValidators([Validators.required, Validators.maxLength(60)]);
      this.form.get('segundoApellido')?.setValidators([Validators.maxLength(60)]);
      this.form.get('cedula')?.setValidators([Validators.maxLength(30)]);
    } else {
      this.tipoSeleccionado = null;
    }
    
    // Actualizar validadores
    this.updateValidators();
  }

  clearValidators(): void {
    // Empresa
    this.form.get('nombreEmpresa')?.clearValidators();
    this.form.get('rut')?.clearValidators();
    this.form.get('razonSocial')?.clearValidators();
    
    // Persona Natural
    this.form.get('cedula')?.clearValidators();
    this.form.get('primerNombre')?.clearValidators();
    this.form.get('segundoNombre')?.clearValidators();
    this.form.get('primerApellido')?.clearValidators();
    this.form.get('segundoApellido')?.clearValidators();
  }

  updateValidators(): void {
    // Empresa
    this.form.get('nombreEmpresa')?.updateValueAndValidity();
    this.form.get('rut')?.updateValueAndValidity();
    this.form.get('razonSocial')?.updateValueAndValidity();
    
    // Persona Natural
    this.form.get('cedula')?.updateValueAndValidity();
    this.form.get('primerNombre')?.updateValueAndValidity();
    this.form.get('segundoNombre')?.updateValueAndValidity();
    this.form.get('primerApellido')?.updateValueAndValidity();
    this.form.get('segundoApellido')?.updateValueAndValidity();
  }

  crearCliente(v: any): void {
    const payload = {
      idTipoCliente: Number(v.idTipoCliente),
      direccion: v.direccion || null,
      pais: v.pais || null,
      ciudad: v.ciudad || null,
      telefono: v.telefono,
      correo: v.correo,
      // Campos de empresa 
      nombreEmpresa: v.nombreEmpresa || null,
      rut: v.rut || null,
      razonSocial: v.razonSocial || null,
      // Campos de persona natural
      cedula: v.cedula || null,
      primerNombre: v.primerNombre || null,
      segundoNombre: v.segundoNombre || null,
      primerApellido: v.primerApellido || null,
      segundoApellido: v.segundoApellido || null,
    };

    this.loading = true;
    this.error = null;

    this.http.post(this.base, payload).subscribe({
      next: () => {
        this.loading = false;
        this.cancel();
        this.load();
      },
      error: err => {
        this.loading = false;
        this.error = 'Error al crear el cliente: ' + (err.error?.message || err.message);
      },
    });
  }

  actualizarCliente(v: any): void {
    const payload = {
      direccion: v.direccion || null,
      pais: v.pais || null,
      ciudad: v.ciudad || null,
      telefono: v.telefono,
      correo: v.correo,
      // Campos adicionales según el tipo
      nombre: v.nombreEmpresa || null,
      razonSocial: v.razonSocial || null,
      primerNombre: v.primerNombre || null,
      segundoNombre: v.segundoNombre || null,
      primerApellido: v.primerApellido || null,
      segundoApellido: v.segundoApellido || null,
    };
    
    this.loading = true;
    
    this.http.put(`${this.base}/${v.id}`, payload).subscribe({
      next: () => {
        this.loading = false;
        this.cancel();
        this.load();
      },
      error: err => {
        this.loading = false;
        this.error = 'Error al actualizar: ' + (err.error?.message || err.message);
      },
    });
  }

  getNombreCompleto(cliente: Cliente): string {
    if (cliente.primerNombre) {
      // Es persona natural
      return `${cliente.primerNombre} ${cliente.segundoNombre || ''} ${cliente.primerApellido} ${cliente.segundoApellido || ''}`.trim();
    } else if (cliente.razonSocial) {
      // Es empresa
      return cliente.razonSocial;
    }
    return cliente.correo;
  }

  filtrarPorTipo(): void {
    if (!this.filtroTipoCliente || this.filtroTipoCliente === '') {
      // Mostrar todos
      this.clientesFiltrados = this.clientes;
      return;
    }

    this.loading = true;
    this.http.get<Cliente[]>(`${this.base}/tipo/${this.filtroTipoCliente}`).subscribe({
      next: (data) => {
        this.clientesFiltrados = data || [];
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Error al filtrar clientes';
      }
    });
  }

   onFiltroChange(idTipo: string | number): void {
    this.filtroTipoCliente = idTipo;
    this.filtrarPorTipo();
  }

}
