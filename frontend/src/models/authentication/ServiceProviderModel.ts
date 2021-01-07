import {IdpConfig} from "@/models/authentication/IdpConfig";

export default class ServiceProviderModel {

  private idpConfig: IdpConfig;
  private requestConfigStore: {[key: string]: IdpConfig | undefined} = {};

  constructor(idpConfig: any) {
    this.idpConfig = idpConfig;
  }

  getIdentityProvider(entityId: string): Promise<any> {
    return Promise.resolve(this.idpConfig);
  }

  storeRequestID(requestId: string, idpConfig: IdpConfig): Promise<any> {
    this.requestConfigStore[requestId] = idpConfig;
    return Promise.resolve();
  }

  verifyRequestID(requestId: string, idpConfig: IdpConfig): Promise<any> {
    return Promise.resolve(this.requestConfigStore[requestId] !== undefined);
  }

  invalidateRequestID(requestId: string, idpConfig: IdpConfig): Promise<any> {
    this.requestConfigStore[requestId] = undefined;
    return Promise.resolve();
  }
}