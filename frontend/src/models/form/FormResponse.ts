import TeamMember from "@/models/team/TeamMember";
import TeamView from "@/models/team/TeamView";

export default interface FormResponse {

    assignedBy: TeamMember;
    assignedTimestamp: Date;
    assignedByTeamDetail: any;
    formName: string;

}

export class FormResponseUtils {

    public static createFromResponse(response: object): FormResponse[] {
        return Object.values(response).map(value => {
            console.log(value);
            return ({
                assignedBy: value.formResponse.assignedBy,
                assignedTimestamp: value.formResponse.assignedTimestamp,
                assignedByTeamDetail: value.formResponse.assignedByTeamDetail,
                formName: value.formResponse.formDetail.name
            } as FormResponse)
        });
    }

}