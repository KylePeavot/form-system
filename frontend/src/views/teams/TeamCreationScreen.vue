<template>
  <BaseStyleLayout :selected-page="page" title="Create new team">
    <Heading :level="2">Team name</Heading>
    <div class="question__text-area-container">
      <TextValidator v-model="teamNameWrapper" :force-show-errors="forceShownTeamNameErrors"/>
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
    <div class="team__members">
        <TeamAccessCard
            v-for="member of members"
            :key="`${member.username}-new-form`"
            :editable="member.username !== loggedInUser.name"
            :member="member"/>
    </div>
    <div class="my-2">
      <hr/>
    </div>
    <button class="button button--primary">Create form</button>
  </BaseStyleLayout>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "../../models/navigation/Pages";
import TextField from "@/components/core/TextField.vue";
import Heading from "@/components/core/Heading.vue";
import UserSelector from "@/components/core/widgets/UserSelector.vue";
import KentUser from "@/models/external/users/KentUser";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import TeamMember from "@/models/team/TeamMember";
import CheckOrCrossIcon from "@/components/widgets/CheckOrCrossIcon.vue";
import TeamAccessCard from "@/components/widgets/teams/TeamAccessCard.vue";
import TextValidator from "@/components/widgets/validation/TextValidator.vue";
import ValidationWrapper from "@/models/validation/ValidationWrapper";
import SensibleNameValidator from "@/validators/SensibleNameValidator";

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

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM;

  private teamNameWrapper = new ValidationWrapper("", [new SensibleNameValidator()]);
  private forceShownTeamNameErrors = false;

  private memberToAdd: KentUser | null = null;
  private members: TeamMember[] = [];
  private loggedInUser: unknown | null = null;

  created() {
    AuthenticationUtils.getUser().then(user => {
      this.loggedInUser = user;
      this.members.push({
        canManageTeam: true,
        canModifyForms: true,
        username: user.name
      });
    })
  }

  addMember() {
    const member = this.memberToAdd;
    this.memberToAdd = null;
    this.members.push({
      canManageTeam: false,
      canModifyForms: false,
      username: member?.id!
    });
  }

}

</script>