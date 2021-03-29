import TeamMember from "@/models/team/TeamMember";
import TeamView from "@/models/team/TeamView";

export default interface FormResponse {

    assignedBy: TeamMember;
    assignedTimestamp: Date;
    assignedByTeamDetail: any;
    formName: string;
    id: number;

}

export class FormResponseUtils {

    public static createFromResponse(response: object): FormResponse[] {
        return Object.values(response).map(value => {
            return ({
                assignedBy: value.formResponse.assignedBy,
                assignedTimestamp: value.formResponse.assignedTimestamp,
                assignedByTeamDetail: value.formResponse.assignedByTeamDetail,
                formName: value.formResponse.formDetail.name,
                id: value.formResponse.id
            } as FormResponse)
        });
    }

}