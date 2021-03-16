export default class FormError {

    private readonly _error: Error;
    private readonly _field: string;

    constructor(error: Error, field: string) {
        this._error = error;
        this._field = field;
    }

    get error(): Error {
        return this._error;
    }

    get field(): string {
        return this._field;
    }
}