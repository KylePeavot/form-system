import FormComponent from "@/models/form/FormComponent";

export default class Form {

    private readonly _name: string;
    private readonly _componentList: FormComponent[];

    constructor(name: string, componentList: FormComponent[]) {
        this._name = name;
        this._componentList = componentList;
    }

    get name(): string {
        return this._name;
    }

    get componentList(): FormComponent[] {
        return this._componentList;
    }


}
