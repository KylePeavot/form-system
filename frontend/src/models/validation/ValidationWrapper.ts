import Validator from "@/validators/Validator";

export default class ValidationWrapper {

    private _value: any;
    private _validators: Validator[];

    constructor(value: any, validators: Validator[]) {
        this._value = value;
        this._validators = validators;
    }

    get value(): any {
        return this._value;
    }

    set value(value: any) {
        this._value = value;
    }

    get validators(): Validator[] {
        return this._validators;
    }

    set validators(value: Validator[]) {
        this._validators = value;
    }
}