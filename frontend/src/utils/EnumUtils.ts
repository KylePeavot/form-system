export default class EnumUtils {

    public static getEnumFromKey<T, U>(enm: T, value: U): any {
        return ((enm as unknown as any)[value]) as T;
    }

}