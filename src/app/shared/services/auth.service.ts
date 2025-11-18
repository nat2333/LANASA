import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoginRequest, LoginResponse, SessionUser } from '../models/auth.models';
import { environment } from '../../../environments/environment';

export type Role = 'admin' | 'employee' | null;

@Injectable({ providedIn: 'root' })
export class AuthService {
  private token: string | null = null;
  private role: Role = null;
  private userId: number | null = null;
  private username: string | null = null;

  constructor(private http: HttpClient) {
    const raw = localStorage.getItem('app_session');
    if (raw) {
      try {
        const u = JSON.parse(raw) as SessionUser;
        this.setSession(u);
      } catch { /* ignore */ }
    }
  }

  login(req: LoginRequest): Observable<SessionUser> {
  return this.http.post<LoginResponse>(`${environment.apiUrl}/autenticacion/login`, req)
    .pipe(
      map(response => {
        const session: SessionUser = {
          id: response.usuario.id,
          username: response.usuario.login,
          role: this.mapearRole(response.usuario.tipoUsuarioNombre),
          token: response.token
        };
        this.setSession(session);
        return session;
      })
    );
}


  private mapearRole(tipo: string): 'admin' | 'employee' {
    if (tipo === 'ADMIN') {
    return 'admin';
  }
  return 'employee';
  }

  setSession(u: SessionUser) {
    this.token = u.token;
    this.role = u.role;
    this.userId = u.id;
    this.username = u.username;
    localStorage.setItem('app_session', JSON.stringify(u));
  }

  clearSession() { this.logout(); }

  logout() {
    this.token = null;
    this.role = null;
    this.userId = null;
    this.username = null;
    localStorage.removeItem('app_session');
  }

  isLoggedIn() { return !!this.token; }
  getRole(): Role { return this.role; }
  hasRole(need: Role | Role[]): boolean {
    if (!this.role) return false;
    return Array.isArray(need) ? need.includes(this.role) : this.role === need;
  }

  getToken(): string | null { return this.token; }
  getUserId(): number | null { return this.userId; }
  getUsername(): string | null { return this.username; }
  
}
