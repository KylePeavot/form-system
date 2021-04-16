import FormComponent from "../FormComponent";
import FormComponentInterface from "@/models/form/interfaces/FormComponentInterface";
import TeamView from "@/models/team/TeamView";

export default interface FormInterface {
  _name: string;
  _teamView: TeamView;
  _componentList: FormComponentInterface[];
}