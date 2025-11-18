import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopbarComponent } from '../../../shared/components/topbar/topbar.component';
import { EmployeeSidebarComponent } from '../components/sidebar/sidebar.component';

@Component({
  selector: 'app-employee-layout',
  standalone: true,
  imports: [RouterOutlet, TopbarComponent, EmployeeSidebarComponent],
  template: `
    <app-topbar></app-topbar>
    <div class="layout">
      <app-employee-sidebar></app-employee-sidebar>
      <main class="content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .layout {
      display: grid;
      grid-template-columns: 260px 1fr;
      min-height: calc(100vh - 50px);
    }
    .content {
      padding: 16px;
    }
  `]
})
export class EmployeeLayoutComponent {}
