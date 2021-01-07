export type SpConfig = {
  entityId: string,
  credentials: {certificate: string, privateKey: string}[];
  endpoints: {assert: string}
}