import {Schedule} from "./schedule";

export interface Calendar {
  id: number;
  ownerId: number;
  schedule: Schedule[];
  oneAppointmentTime: string;
  workStartTime: string;
  workEndTime: string;
}
