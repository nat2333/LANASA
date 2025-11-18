import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-employee-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class EmployeeSidebarComponent {
  openSection: string | null = 'proyectos';

  toggleSection(section: string): void {
    this.openSection = this.openSection === section ? null : section;
  }
}
