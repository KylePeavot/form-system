export type IdpConfig = {
  entityId: string,
  credentials: [{ certificate: string }];
  endpoints: { login: string }
}