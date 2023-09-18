export interface AuthenticationResponse {
    token: String;
    emailTaken: boolean;
    role: string;
    uid: number;
    medCardId: number;
}