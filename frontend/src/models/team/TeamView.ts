import Team from "@/models/team/Team";
import TeamMember from "@/models/team/TeamMember";

export default interface TeamView {

  teamName: string;
  teamMembers: TeamMember[];
  teamId: number;

}