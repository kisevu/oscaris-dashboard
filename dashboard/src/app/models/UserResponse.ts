import { Role } from "./Role";
import { UserInfo } from "./UserInfo";

export interface UserResponse {
  email:string;
  firstName:string;
  lastName: string;
  enabled: boolean;
  userInfo: UserInfo;
  roles: Array<Role>;
}
