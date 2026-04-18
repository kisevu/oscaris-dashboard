
export interface Company {
  companyName: string;
  kraPin: string;
  contactPerson: string;
  count:number;
  address: Address;
  phoneNumber: string;
}

export interface Address {
  street:string;
  city: string;
  building: string;
}
