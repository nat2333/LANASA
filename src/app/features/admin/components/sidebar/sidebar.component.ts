import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class AdminSidebarComponent {
  @Input() collapsed = false;

  open: Record<string, boolean> = {};
  toggle(k: string) { this.open[k] = !this.open[k]; }

  ngOnChanges() {
    if (this.collapsed) this.open = {};
  }
}
