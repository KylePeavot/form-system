<template>
  <BaseStyleLayout :selected-page="page" title="Create new team">
    <div class="mb-2" v-if="submissionError.length > 0">
      <div class="bg-red-50 border-red-600 border-l-8 p-2 rounded-md mb-2">
        <ul>
          <li class="list-disc list-inside" v-for="(value, index) in submissionError" :key="`error-${index}`">
            <label class="text-blue-500 cursor-pointer" :for="`${value.field}`">{{ value.error.message }}</label>
          </li>
        </ul>
      </div>
      <hr/>
    </div>
    <Heading :level="2">Team name</Heading>
    <div class="question__text-area-container">
      <TextValidator id="teamName" v-model="teamNameWrapper" :force-show-errors="forceShownTeamNameErrors"/>
    </div>
    <div class="my-2">
      <hr/>
    </div>
    <Heading :level="2">Members</Heading>
    <div class="flex">
      <div class="flex-1 max-w-sm">
        <UserSelector v-model="memberToAdd"/>
      </div>
      <div>
        <button class="button ml-2" @click="addMember">Add member</button>
      </div>
    </div>
    <br/>
    <div class="team__members" v-if="loggedInUser !== null">
        <TeamAccessCard
            v-for="member of members"
            :key="`${member.username}-new-form`"
            :editable="member.username !== loggedInUser.name"
            :member="member"
            @remove-member="removeMember"/>
    </div>
    <div class="my-2">
      <hr/>
    </div>
    <button class="button button--primary" @click="createForm" v-if="mode === 'CREATE'">Create team</button>
    <button class="button button--primary" @click="updateForm" v-if="mode === 'EDIT'">Update team</button>
  </BaseStyleLayout>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "../../models/navigation/Pages";
import TextField from "@/components/core/TextField.vue";
import UserSelector from "@/components/core/widgets/UserSelector.vue";
import KentUser from "@/models/external/users/KentUser";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import TeamMember from "@/models/team/TeamMember";
import CheckOrCrossIcon from "@/components/widgets/CheckOrCrossIcon.vue";
import TeamAccessCard from "@/components/widgets/teams/TeamAccessCard.vue";
import TextValidator from "@/components/widgets/validation/TextValidator.vue";
import ValidationWrapper from "@/models/validation/ValidationWrapper";
import SensibleNameValidator from "@/validators/SensibleNameValidator";
import WebRequestUtils from "@/utils/WebRequestUtils";
import FormError from "@/models/form/FormError";
import TeamView from "@/models/team/TeamView";
import Heading from "@/components/core/componentExtras/Heading.vue";

@Component({
  components: {
    TextValidator,
    Heading,
    TextField,
    BaseStyleLayout,
    UserSelector,
    CheckOrCrossIcon,
    TeamAccessCard
  }
})
export default class TeamCreationScreen extends Vue {

  @Prop({required: true})
  private mode!: "CREATE" | "EDIT";

  @Prop({default: 0})
  private teamId!: number;

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM;

  private teamNameWrapper = new ValidationWrapper("", [new SensibleNameValidator()]);
  private forceShownTeamNameErrors = false;

  private memberToAdd: KentUser | null = null;
  private members: TeamMember[] = [];
  private loggedInUser: unknown | null = null;

  private submissionError: FormError[] = [];

  created() {
    if (this.mode === "CREATE") {
      AuthenticationUtils.getUser().then(user => {
        this.loggedInUser = user;
        this.members.push({
          canManageTeam: true,
          canModifyForms: true,
          username: user.name
        });
      });
    } else if (this.mode === "EDIT") {
      AuthenticationUtils.getUser().then(user => {
        this.loggedInUser = user;
        WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/teams/${this.teamId}`, true)
        .then(response => response.json())
        .then(value => value as TeamView)
        .then(value => {
          this.members = value.teamMembers;
          this.teamNameWrapper.value = value.teamName
        });
      });
    }
  }

  addMember() {
    const member = this.memberToAdd;
    if (member === null || member.id === (this.loggedInUser as any).username) {
      return;
    }
    this.memberToAdd = null;
    this.members.push({
      canManageTeam: false,
      canModifyForms: false,
      username: member?.id!
    });
  }

  removeMember(member: TeamMember) {
    this.members = this.members.filter(value => value.username !== member.username);
  }

  createForm() {
    if (this.validateForm()) {
      WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/teams/new`, {
        members: this.members,
        name: this.teamNameWrapper.value
      })
      .then(value => value.json())
      .then(this.onSuccessfulSubmit);
    }
  }

  updateForm() {
    if (this.validateForm()) {
      WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/teams/${this.teamId}`, {
        members: this.members,
        name: this.teamNameWrapper.value
      })
      .then(value => value.json())
      .then(this.onSuccessfulSubmit);
    }
  }

  // Handle all validation scenarios client-side.
  // Validation is repeated on the server, but this offers runtime safety.
  private validateForm(): boolean {
    this.forceShownTeamNameErrors = true;
    this.submissionError = [];
    if (this.members.length === 0) {
      const err = new Error("Team must have at least one member");
      this.submissionError.push(new FormError(err, "members"));
      return false;
    }
    for (const validator of this.teamNameWrapper.validators) {
      if (!validator.isValid(this.teamNameWrapper)) {
        const err = new Error(validator.getMessage(this.teamNameWrapper));
        this.submissionError.push(new FormError(err, "teamName"));
        return false;
      }
    }
    let atLeastOneCanManageTeam = false;
    for (const member of this.members) {
      if (member.canManageTeam) {
        atLeastOneCanManageTeam = true;
        break;
      }
    }
    if (!atLeastOneCanManageTeam) {
      const err = new Error("At least one member must be able to manage the team");
      this.submissionError.push(new FormError(err, "members"));
      return false;
    }
    let atLeastOneCanModifyForms = false;
    for (const member of this.members) {
      if (member.canModifyForms) {
        atLeastOneCanModifyForms = true;
        break;
      }
    }
    if (!atLeastOneCanModifyForms) {
      const err = new Error("At least one member must be able to modify forms");
      this.submissionError.push(new FormError(err, "members"));
      return false;
    }
    return true;
  }

  onSuccessfulSubmit(response: any) {
    if (response.success === true) {
      this.$router.push(Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.url);
    } else if (response.error !== undefined) {
      this.submissionError.push(new FormError(
          new Error(response.error),
          ""
      ));
    }
  }

}

</script>