export interface LoginResponse {
  usuario: Usuario;
  token: string;
}

export interface Usuario {
  id: number;
  login: string;
  clave: string; 
  tipoUsuarioNombre: string;
  estado: boolean;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface SessionUser {
  id: number;
  username: string;
  role: 'admin' | 'employee';
  token: string;
}