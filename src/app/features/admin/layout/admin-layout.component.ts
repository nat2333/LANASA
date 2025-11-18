import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopbarComponent } from '../../../shared/components/topbar/topbar.component';
import { AdminSidebarComponent } from '../components/sidebar/sidebar.component';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, TopbarComponent, AdminSidebarComponent],
  template: `
    <app-topbar></app-topbar>
    <div class="layout">
      <app-admin-sidebar></app-admin-sidebar>
      <main class="content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styleUrls: ['./admin-layout.component.scss'],
})
export class AdminLayoutComponent {
  collapsed = signal(false);
  toggleSidebar() { this.collapsed.update(v => !v); }
}

