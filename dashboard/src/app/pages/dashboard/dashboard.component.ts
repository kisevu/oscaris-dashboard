import {ChangeDetectionStrategy ,Component, OnInit } from '@angular/core';
import { DashboardCardComponent } from "../../components/dashboard-card/dashboard-card.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardCardComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardComponent implements OnInit {

  ngOnInit(): void {
  }

}
