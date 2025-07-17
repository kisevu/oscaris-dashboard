import { createAction, props } from "@ngrx/store";
import { Company } from "./company.model";

export const loadCompanies = createAction('[Company] Load companies');

export const loadCompaniesSuccess = createAction('[Company] Load companies Success',
props<{companies: Company []}>()
);

export const loadCompaniesFailure = createAction('[Company] Load companies failure',
  props<{error: any}>()
 );


 /*  Add company */
export const addcompany = createAction('[Company] Add Company ', props<{company: Company}>());
export const addcompanySuccess = createAction('[Company] Add Company Success ', props<{company: Company}>());
export const addcompanyFailure = createAction('[Company] Add Company Failure ', props<{error: any}>());



/*  Remove company  */

export const deleteCompany = createAction('[Company] Delete Company ', props<{companyName:string}>());
export const deleteCompanySuccess = createAction('[Company] Delete Company Success ', props<{companyName:string}>());
export const deleteCompanyFailure = createAction('[Company] Delete Company Failure ', props<{error:any}>());


/*  Update company */


export const updateCompany = createAction('[Company] Update Company', props<{company:Company}>());
export const updateCompanySuccess = createAction('[Company] Update Company Success',props<{company: Company}>());
export const updateCompanyFailure = createAction('[Company] update Company Failure',props<{error:any}>());
