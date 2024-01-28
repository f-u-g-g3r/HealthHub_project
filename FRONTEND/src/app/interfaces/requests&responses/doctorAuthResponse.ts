export interface DoctorAuthResponse {
    token: String;
    emailTaken: boolean;
    ageValid: boolean;
    role: string;
    doctorId: number;
    status: string;
}
