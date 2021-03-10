import FormComponent from "../FormComponent";
import FormComponentInterface from "@/models/form/interfaces/FormComponentInterface";

export default interface FormInterface {
  _name: string;
  _componentList: FormComponentInterface[];
}