/*eslint-disable */

export default class AuthenticationUtils {

  private static auth: any | undefined;

  public static isLoggedIn(): boolean {
    return this.auth.user !== undefined;
  }

  public static bindAuth(auth: any) {
    this.auth = auth;
  }

  public static getUser(): Promise<any> {
    return new Promise(resolve => {
      const interval = setInterval(() => {
        if (this.auth._data != undefined && this.auth._data.auth0Client != undefined) {
          if (this.auth._data.isAuthenticated) {
            resolve(this.auth._data.user);
          } else {
            resolve(undefined);
          }
          clearInterval(interval);
        }
      }, 10);
    })
  }

  public static getContext() {
    return this.auth;
  }

}