import {Doctor} from "./doctor";

export interface DoctorsPage {
  content: Doctor[];
  pageable: {
    pageNumber: number;
  }
  last: boolean;
  totalPages: number;
  first: boolean;
}
