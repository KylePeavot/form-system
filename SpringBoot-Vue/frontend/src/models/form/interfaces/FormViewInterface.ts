export default interface FormViewInterface {
  formDetailId: number;
  name: string;
  createdBy: string;
  createdWhen: string;
  lastUpdatedBy: string;
  lastUpdatedWhen: string;
  canModifyForm: boolean;
}