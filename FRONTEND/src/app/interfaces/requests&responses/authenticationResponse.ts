export interface AuthenticationResponse {
    token: string;
    emailTaken: boolean;
    role: string;
    uid: number;
    medCardId: number;
}