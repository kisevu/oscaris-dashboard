import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../../store/companies/company.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  private readonly apiUrl: string = environment.companiesBasePath;

  http = inject(HttpClient);

  getCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.apiUrl}/companies`);
  }

  addCompany(company: Company): Observable<any> {
    return this.http.post(`${this.apiUrl}/add-company`, this.mapToSpringDTO(company));
  }

  deleteCompany(companyName: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete`, {
      params: { companyName },
    });
  }

  updateCompany(params:{
   companyName: string,
    constituency?: string,
    county?: string,
    city?: string,
    street?: string,
    contactPersonNo?: string,
    maxOrders?: number,
    minOrders?: number
  }) :Observable<any> {
       let httpParams = new HttpParams().set('companyName', params.companyName);

    if (params.constituency) httpParams = httpParams.set('constituency', params.constituency);
    if (params.county) httpParams = httpParams.set('county', params.county);
    if (params.city) httpParams = httpParams.set('city', params.city);
    if (params.street) httpParams = httpParams.set('street', params.street);
    if (params.contactPersonNo) httpParams = httpParams.set('contactPersonNo', params.contactPersonNo);
    if (params.maxOrders !== undefined) httpParams = httpParams.set('maxOrders', params.maxOrders.toString());
    if (params.minOrders !== undefined) httpParams = httpParams.set('minOrders', params.minOrders.toString());
    return this.http.patch(`${this.apiUrl}/update`, null, { params: httpParams });
  }

  private mapToSpringDTO(form: Company) {
    return {
      companyName: form.companyName,
      maxOrders: form.maxOrders,
      minOrders: form.minOrders,
      kraPin: form.kraPin,
      contactPersonNo: form.contactPersonNo,
      address: {
        street: form.street, // or use a separate field if needed
        city: form.city,
        county: form.county,
        constituency: form.constituency,
      },
    };
  }
}
