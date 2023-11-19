export interface AuthenticationResponse {
    token: string;
    emailTaken: boolean;
    role: string;
    uid: number;
    doctorId: number;
    medCardId: number;
    doctorStatus: string;
}