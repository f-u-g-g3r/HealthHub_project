import {User} from "./user";

export interface UsersPage {
  content: User[];
  pageable: {
    pageNumber: number;
  }
  last: boolean;
  totalPages: number;
  first: boolean;
}
