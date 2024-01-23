export interface Doctor {
    id: number;
    uuid: string;
    firstname: string;
    lastname: string;
    dateOfBirth: string;
    age: number;
    gender: string;
    address: string;
    email: string;
    phone: string;
    password: string;
    specialization: string;
    placeOfWork: string;
    licenseNumber: string;
    licenseIssuingDate: string;
    licenseIssuingAuthority: string;
    status: string;
    calendarId: number;
    configured: boolean;
}
