import { Routes } from '@angular/router';
import { LayoutComponent } from './pages/layout/layout.component';
import { EmployeesComponent } from './pages/employees/employees.component';
import { CompaniesComponent } from './pages/companies/companies.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { StockComponent } from './pages/stock/stock.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { InvoicesComponent } from './pages/invoices/invoices.component';
import { SuppliersComponent } from './pages/suppliers/suppliers.component';
import { DebtsComponent } from './pages/debts/debts.component';
import { CityMarketComponent } from './pages/expenses/city-market/city-market.component';
import { DagorettiMarketComponent } from './pages/expenses/dagoretti-market/dagoretti-market.component';
import { SpicesRiceComponent } from './pages/expenses/spices-rice/spices-rice.component';
import { BulkShoppingComponent } from './pages/expenses/bulk-shopping/bulk-shopping.component';
import { LightShoppingComponent } from './pages/expenses/light-shopping/light-shopping.component';
import { GasComponent } from './pages/expenses/gas/gas.component';
import { RentComponent } from './pages/expenses/rent/rent.component';
import { CharcoalComponent } from './pages/expenses/charcoal/charcoal.component';
import { WaterComponent } from './pages/expenses/water/water.component';
import { FruitsComponent } from './pages/weekly/fruits/fruits.component';
import { VegetablesComponent } from './pages/weekly/vegetables/vegetables.component';
import { MarketComponent } from './pages/weekly/market/market.component';
import { PotatoesComponent } from './pages/weekly/potatoes/potatoes.component';
import { JuiceComponent } from './pages/weekly/juice/juice.component';
import { SodaComponent } from './pages/weekly/soda/soda.component';
import { MiscelleanousComponent } from './pages/weekly/miscelleanous/miscelleanous.component';
import { EmployeeDetailsComponent } from './pages/employee-details/employee-details.component';
import { LoginComponent } from './pages/login/login.component';
import { authGuard } from './guard/auth.guard';
import { RegisterComponent } from './pages/register/register.component';
import { LogoutComponent } from './pages/logout/logout.component';

export const routes: Routes = [
   {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full' ,
   },

  {
    path: 'register',
    component: RegisterComponent
  },

  {
     path: 'login',
    component: LoginComponent
  },

  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },

      {
        path: 'dashboard',
        component: DashboardComponent,
      },

      {
        path: 'employees',
        component: EmployeesComponent,
      },

      {
        path: 'companies',
        component: CompaniesComponent,
      },

      {
        path: 'stock',
        component: StockComponent,
      },

      {
        path: 'orders',
        component: OrdersComponent,
      },

      {
        path: 'invoices',
        component: InvoicesComponent,
      },

      {
        path: 'suppliers',
        component: SuppliersComponent,
      },

      {
        path: 'debts',
        component: DebtsComponent,
      },

      {
        path: 'city-mkt',
        component: CityMarketComponent,
      },

      {
        path: 'dagoretti-mkt',
        component: DagorettiMarketComponent,
      },

      {
        path: 'spices-rice',
        component: SpicesRiceComponent,
      },

      {
        path: 'bulk-shopping',
        component: BulkShoppingComponent,
      },

      {
        path: 'light-shopping',
        component: LightShoppingComponent,
      },

      {
        path: 'gas',
        component: GasComponent,
      },

      {
        path: 'rent',
        component: RentComponent,
      },

      {
        path: 'charcoal',
        component: CharcoalComponent,
      },
      {
        path: 'water',
        component: WaterComponent,
      },
      {
        path: 'fruits',
        component: FruitsComponent,
      },
      {
        path: 'milton',
        component: VegetablesComponent,
      },
      {
        path: 'market',
        component: MarketComponent,
      },
      {
        path: 'potatoes',
        component: PotatoesComponent,
      },
      {
        path: 'juice',
        component: JuiceComponent,
      },
      {
        path: 'soda',
        component: SodaComponent,
      },

      {
        path: 'miscelleanous',
        component: MiscelleanousComponent,
      },
      {
        path:'employees/details',
        component: EmployeeDetailsComponent
      }
    ],
  },
  {
    path:'logout',
    component: LogoutComponent
  },
  {
    path: '**',
    redirectTo: 'login'
   }
];
