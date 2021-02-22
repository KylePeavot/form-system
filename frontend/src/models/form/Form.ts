import FormCreationComponent from "@/models/form/FormCreationComponent";

export default class Form {

    private readonly _name: string;
    private readonly _componentList: FormCreationComponent[];

    constructor(name: string, componentList: FormCreationComponent[]) {
        this._name = name;
        this._componentList = componentList;
    }

    get name(): string {
        return this._name;
    }

    get componentList(): FormCreationComponent[] {
        return this._componentList;
    }


}
