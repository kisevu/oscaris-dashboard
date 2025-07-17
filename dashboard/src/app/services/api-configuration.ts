import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApiConfiguration {
  rootUrl: string = 'http://localhost:7575/api/oscaris';
}

export interface ApiConfigurationParams {
  rootUrl?: string;
}
