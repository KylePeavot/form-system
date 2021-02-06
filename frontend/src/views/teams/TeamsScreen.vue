<template>
  <BaseStyleLayout title="Teams" :selected-page="page">
    <Banner>
      <p>You must be in a team to create a form.</p>
      <p>Only teams you belong to will appear here.</p>
    </Banner>
    <p><strong>All team members can send forms to others</strong></p>
    <br/>
    <button class="button button--primary">Create new team</button>
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
          <div class="team__members">
            <div class="mb-1 mr-2 p-2 bg-gray-200 inline-block rounded-md" v-for="member of team.teamMembers" :key="`member-${member.username}-form-${team.teamDetail.id}`">
              <p>{{ member.username }}</p>
              <div class="text-sm text-gray-700">
                <CheckOrCrossIcon :value="member.canModifyForms"/>
                <span>Modify forms</span>
              </div>
              <div class="text-sm text-gray-700">
                <CheckOrCrossIcon :value="member.canManageTeam"/>
                <span>Manage team</span>
              </div>
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
import Heading from "@/components/core/Heading.vue";
import CheckOrCrossIcon from "@/components/widgets/CheckOrCrossIcon.vue";

@Component({
  components: {CheckOrCrossIcon, Heading, Banner, BaseStyleLayout}
})
export default class TeamsScreen extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS;
  private teams: TeamView[] = [];
  private retrievedTeams = false;
  private retrieveError: Error | null = null;

  mounted() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/teams`, true)
      .then(value => value.json() as unknown as TeamView[])
      .then(value => this.teams = value)
      .then(() => this.retrievedTeams = true)
      .then(() => console.log(this.teams))
      .catch(e => {
        this.retrieveError = e;
      });
  }

}
</script>