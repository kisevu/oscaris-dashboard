import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { CompanyService } from "../../services/company/company.service";
import * as CompanyActions from "./company.actions";
import { catchError, map, mergeMap, of } from "rxjs";


@Injectable()

export class CompanyEffects{

   constructor(
    private readonly action$:Actions,
    private readonly companyService: CompanyService
   ){}


  loadCompanie$ = createEffect(()=>
  this.action$.pipe(
    ofType(CompanyActions.loadCompanies),
    mergeMap(()=>
      this.companyService.getCompanies().pipe(
        map(companies=> CompanyActions.loadCompaniesSuccess({ companies })),
        catchError(error=> of(CompanyActions.loadCompaniesFailure({ error })))
      )
    )
  )
  )


  addCompany$ = createEffect(() =>
    this.action$.pipe(
      ofType(CompanyActions.addcompany),
      mergeMap(({ company }) =>
        this.companyService.addCompany(company).pipe(
          map((savedCompany) =>
            CompanyActions.addcompanySuccess({ company: savedCompany })
          ),
          catchError((error) =>
            of(CompanyActions.addcompanyFailure({ error }))
          )
        )
      )
    )
  );


  deleteCompany$ = createEffect(() =>
    this.action$.pipe(
      ofType(CompanyActions.deleteCompany),
      mergeMap(({ companyName }) =>
        this.companyService.deleteCompany(companyName).pipe(
          map(() =>
            CompanyActions.deleteCompanySuccess({ companyName })
          ),
          catchError((error) =>
            of(CompanyActions.deleteCompanyFailure({ error }))
          )
        )
      )
    )
  );


  updateCompany$ = createEffect(()=>
    this.action$.pipe(
      ofType(CompanyActions.updateCompany),
      mergeMap(({ company })=>
      this.companyService.updateCompany(company).pipe(
        map((updatedCompany)=>
        CompanyActions.updateCompanySuccess({ company: updatedCompany})
        ),
        catchError(( error )=>
        of(CompanyActions.updateCompanyFailure({ error }))
      )
      )
      )
    )
  );



}
