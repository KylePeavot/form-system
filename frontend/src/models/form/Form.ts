import FormComponent from "../form/FormComponent";
import FormInterface from "./interfaces/FormInterface";
import TeamView from "@/models/team/TeamView";

export default class Form {

    private readonly _name: string;
    private readonly _team: TeamView;
    private readonly _componentList: FormComponent[];

    constructor(name: string, team: TeamView, componentList: FormComponent[]) {
        this._name = name;
        this._team = team;
        this._componentList = componentList;
    }

    get name(): string {
        return this._name;
    }

    get componentList(): FormComponent[] {
        return this._componentList;
    }

    static mapFormInterfaceToForm(formInterface: FormInterface): Form {
        const formComponentList: FormComponent[] = formInterface._componentList.map(value => FormComponent.mapFormComponentInterfaceToFormComponent(value));
        return new Form(formInterface._name, formInterface._teamView, formComponentList);
    }

}
