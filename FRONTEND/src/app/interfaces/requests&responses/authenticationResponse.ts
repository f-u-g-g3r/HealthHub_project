export interface AuthenticationResponse {
    token: string;
    emailTaken: boolean;
    ageValid: boolean;
    role: string;
    uid: number;
    doctorId: number;
    medCardId: number;
    status: string;
}
