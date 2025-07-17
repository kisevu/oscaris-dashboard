

import { createReducer, on } from "@ngrx/store";
import {Company} from "./company.model";

import * as CompanyActions from "./company.actions";
import { state } from "@angular/animations";

export interface CompanyState {
  companies: Company [];
  loading: boolean;
  error: any;
}


export const initialState: CompanyState = {
  companies: [],
  loading: false,
  error: null
}


export const companyReducer = createReducer(
  initialState,

  on(CompanyActions.loadCompanies, state => ({
    ...state,
    loading: true
  })),

  on(CompanyActions.loadCompaniesSuccess, (state, {companies})=> ({
    ...state,
    companies,
    loading: true
  })),

  on(CompanyActions.loadCompaniesFailure, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(CompanyActions.addcompany, (state, { company })=> ({
    ...state,
    companies: [...state.companies,company],
    loading: false
  })),

  on(CompanyActions.updateCompanySuccess,(state, { company })=> ({
    ...state,
    companies: state.companies.map((c)=>
    c.companyName === company.companyName ? company : c
    ),
    loading: false
  })),

);
