export interface DoctorAuthRequest {
    id: number;
    uuid: string;
    firstname: string;
    lastname: string;
    dateOfBirth: string;
    gender: string;
    address: string;
    email: string;
    phone: string;
    password: string;
    medCardID: number;
    specialization: string;
    placeOfWork: string;
    licenseNumber: string;
    licenseIssuingDate: string;
    licenseIssuingAuthority: string;
}