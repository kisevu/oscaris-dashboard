import { createFeatureSelector, createSelector } from "@ngrx/store";
import { CompanyState } from "./company.reducer";


export const selectCompanyState = createFeatureSelector<CompanyState>('companies');

export const selectAllCompanies = createSelector(
  selectCompanyState,
  state => state.companies
);


export const selectCompanyLoading = createSelector(
  selectCompanyState,
  state => state.loading
)
