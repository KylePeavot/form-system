<template>
  <BaseStyleLayout title="Teams" :selected-page="page">
    <Banner>
      <p>You must be in a team to create a form.</p>
      <p>Only teams you belong to will appear here.</p>
    </Banner>
    <template v-if="retrievedTeams">
      <div v-for="team of teams" :key="`team-${team.teamDetail.id}`">
        <div class="team">
          <div class="team__title">{{ team.teamDetail.name }}</div>
          <div class="team__members">
            <ul class="ml-2">
              <li v-for="member of team.teamMembers" :key="`member-${member.username}`">{{ member.username }}</li>
            </ul>
          </div>
        </div>
      </div>
    </template>
    <template v-if="!retrievedTeams">
      <p>Getting teams</p>
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

@Component({
  components: {Banner, BaseStyleLayout}
})
export default class TeamsScreen extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS;
  private teams: TeamView[];
  private retrievedTeams = false;
  private retrieveError: Error | null = null;

  mounted() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/teams`, true)
      .then(value => value.json() as TeamView[])
      .then(value => this.teams = value)
      .then(() => this.retrievedTeams = true)
      .then(() => console.log(this.teams))
      .catch(e => {
        this.retrieveError = e;
      });
  }

}
</script>