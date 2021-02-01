/*eslint-disable */

export default class AuthenticationUtils {

  private static auth: any | undefined;

  public static async isLoggedIn(): Promise<boolean> {
    return this.getUser()
      .then(async () => {
        return await AuthenticationUtils.getContext()._data.isAuthenticated;
      });
  }

  public static bindAuth(auth: any) {
    this.auth = auth;
  }

  public static getUser(): Promise<any> {
    return new Promise(resolve => {
      const interval = setInterval(async () => {
        if (!this.auth.loading) {
          resolve(await this.auth.user);
          clearInterval(interval);
        }
      }, 1);
    })
  }

  public static getContext() {
    return this.auth;
  }

}