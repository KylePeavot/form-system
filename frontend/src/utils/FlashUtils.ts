import Flash from "@/models/generic/Flash";

export default class FlashUtils {

    private static flashes: Flash[] = [];

    public static flash(type: FlashType, title: string, message: string) {
        this.flashes.push(new Flash(title, message, type));
    }

    public static getFlashes() {
        const flashesToReturn = [...this.flashes];
        this.flashes = [];
        return flashesToReturn;
    }

}

export enum FlashType {
    SUCCESS = "success",
    ERROR = "error"
}