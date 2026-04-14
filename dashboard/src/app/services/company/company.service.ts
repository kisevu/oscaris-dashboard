import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../../store/companies/company.model';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  private readonly companyUrl: string = environment.companyBaseUrl;

  http = inject(HttpClient);

  getCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.companyUrl}/companies`);
  }

  addCompany(company: Company): Observable<any> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.post(`${this.companyUrl}/companies/add`, company,{headers} );
  }


  deleteCompany(companyName: string): Observable<any> {
    return this.http.delete(`${this.companyUrl}/delete`, {
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
    return this.http.patch(`${this.companyUrl}/update`, null, { params: httpParams });
  }
}
