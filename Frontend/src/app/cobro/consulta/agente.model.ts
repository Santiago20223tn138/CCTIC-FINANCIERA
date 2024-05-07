import { PersoneModel } from "./person.model";

export interface AgenteModel {
    id?: number;
    username: string;
    person : PersoneModel;
    status: 1;
}