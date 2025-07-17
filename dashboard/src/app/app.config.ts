import { ApplicationConfig, isDevMode } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { provideEffects } from '@ngrx/effects';
import { companyReducer } from './store/companies/company.reducer';
import { CompanyEffects } from './store/companies/company.effects';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideStore({companies: companyReducer}),
    provideStoreDevtools({ maxAge: 25, logOnly: !isDevMode() }),
    provideEffects([CompanyEffects]), provideAnimationsAsync(),
  ],
};
