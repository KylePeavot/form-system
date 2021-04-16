import {FlashType} from "@/utils/FlashUtils";

export default class Flash {

    private readonly _title: string;
    private readonly _message: string;
    private readonly _type: FlashType;

    constructor(title: string, message: string, type: FlashType) {
        this._title = title;
        this._message = message;
        this._type = type;
    }

    get title(): string {
        return this._title;
    }

    get message(): string {
        return this._message;
    }

    get type(): FlashType {
        return this._type;
    }
}