import {Schedule} from "./schedule";

export interface SchedulesPage {
  content: Schedule[];
  pageable: {
    pageNumber: number;
  }
  last: boolean;
  totalPages: number;
  first: boolean;
}
