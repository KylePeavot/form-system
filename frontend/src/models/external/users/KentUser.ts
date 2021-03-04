export default class KentUser {

  private readonly _id: string;
  private readonly _name: string;
  private readonly _displayName: string;

  public constructor(id: string, name: string) {
    this._id = id;
    this._name = name;
    this._displayName = `${id} (${name})`;
  }

  public static createUserFromResponseString(responseString: string): KentUser {
    const split = responseString.split(":");
    // Response string formatted like the following:
    // userid:x:numbers:numbers:name,otherid:local_directory:/bin/bash
    // userid is the first parameter
    const userId = split[0];
    // name is the 5th parameter however is comma separated with otherid, so split again and pull
    // out the left side.
    const name = split[4].split(",")[0];
    return new KentUser(userId, name);
  }

  get id(): string {
    return this._id;
  }

  get displayName(): string {
    return this._displayName;
  }

  get name(): string {
    return this._name;
  }
}