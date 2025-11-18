export interface Proyecto {
  id_proyecto: number;
  nombre: string;
  descripcion?: string;
  fecha_inicio?: string;
  fecha_fin?: string;
  estado: string;
  id_cliente: number;
}
