import { DidService } from './DidService';

export interface GenericAttribute {
    value: string;
    type: string;
}
export interface Did {
    id: number;
    externalKey: string;
    did: string;
    services: DidService[];
    logo: GenericAttribute;
    title: GenericAttribute;
    subTitle1: GenericAttribute;
    subTitle2: GenericAttribute;
}
