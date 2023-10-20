import { Allergy } from "./medCard/Allergy";
import { MedHistory } from "./medCard/MedHistory";
import { ChronicDisease } from "./medCard/chronicDisease";
import { ResultOfSurvey } from "./medCard/resultOfSurvey";

export interface MedCard {
    id: number;
    ownerID: number;
    medHistory: MedHistory[];
    bloodType: string;
    rhFactor: string;
    allergies: Allergy[];
    chronicDiseases: ChronicDisease[];
    resultsOfSurveys: ResultOfSurvey[];
    familyDoctorID: number;
}