<template>
  <BaseStyleLayout title="Teams" :selected-page="page">
    <Banner>
      <p>You must be in a team to create a form.</p>
      <p>Only teams you belong to will appear here.</p>
    </Banner>
    <p><strong>All team members can send forms to others</strong></p>
    <br/>
    <router-link :to="createTeamUrl">
      <a class="button button--primary">Create new team</a>
    </router-link>
    <br/><br/>
    <hr/>
    <div class="my-2">
      <Heading :level="2">Your teams</Heading>
    </div>
    <template v-if="retrievedTeams">
      <div v-for="team of teams" :key="`team-${team.teamDetail.id}`">
        <div class="team">
          <div class="team__title my-2">
            <Heading :level="3">
              {{ team.teamDetail.name }}
            </Heading>
          </div>
          <div class="team__body">
            <div v-if="selfCanManageTeam(team.teamMembers)" class="my-2">
              <div class="flex">
                <div class="flex-1 max-w-sm">
                  <UserSelector :value="teamAndUserMap.get(team)"/>
                </div>
                <div>
                  <button class="button ml-2">Add member</button>
                </div>
              </div>
            </div>
            <div class="team__members">
              <TeamAccessCard
                  v-for="member of team.teamMembers"
                  :key="`member-${member.username}-form-${team.teamDetail.id}`"
                  :editable="selfCanManageTeam(team.teamMembers)"
                  :member="member"/>
            </div>
          </div>
        </div>
      </div>
    </template>
    <template v-if="!retrievedTeams">
      <p class="text-gray-600 text-xl">
        Retrieving your teams
      </p>
    </template>
  </BaseStyleLayout>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import BaseStyleLayout from '@/components/layout/BaseStyleLayout.vue';
import Pages from "@/models/navigation/Pages";
import Banner from "@/components/widgets/Banner.vue";
import WebRequestUtils from "@/utils/WebRequestUtils";
import TeamView from "@/models/team/TeamView";
import CheckOrCrossIcon from "@/components/widgets/CheckOrCrossIcon.vue";
import KentUser from "@/models/external/users/KentUser";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import TeamMember from "@/models/team/TeamMember";
import UserSelector from "@/components/core/widgets/UserSelector.vue";
import TeamAccessCard from "@/components/widgets/teams/TeamAccessCard.vue";
import Heading from "@/components/core/componentExtras/Heading.vue";

@Component({
  components: {
    TeamAccessCard,
    CheckOrCrossIcon,
    Heading,
    Banner,
    BaseStyleLayout,
    UserSelector
  }
})
export default class TeamsScreen extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS;
  private teamAndUserMap: Map<TeamView, KentUser | null> = new Map<TeamView, KentUser | null>();
  private retrievedTeams = false;
  private retrieveError: Error | null = null;
  private user: KentUser | null = null;
  private createTeamUrl = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM.url;

  mounted() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/teams`, true)
    .then(value => value.json() as unknown as TeamView[])
    .then(value => {
      this.teamAndUserMap = new Map<TeamView, KentUser | null>();
      value.forEach(team => {
        this.teamAndUserMap.set(team, null);
      });
    })
    .then(() => this.retrievedTeams = true)
    .catch(e => {
      this.retrieveError = e;
    });
    AuthenticationUtils.getUser().then(self => {
      this.user = new KentUser(self.name, self.name);
    });
  }

  get teams() {
    const list: TeamView[] = [];
    this.teamAndUserMap.forEach((value, key) => {
      list.push(key);
    });
    return list;
  }

  selfCanManageTeam(team: TeamMember[]) {
    if (this.user === null) {
      return false;
    }
    const userInTeam = team.find(value => value.username === this.user?.name);
    if (userInTeam !== undefined) {
      return userInTeam.canManageTeam;
    }
    return false;
  }

}
</script>