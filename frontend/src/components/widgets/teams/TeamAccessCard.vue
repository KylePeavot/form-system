<template>
  <div class="mb-1 mr-2 p-2 bg-gray-200 inline-block rounded-md">
    <p>{{ member.username }}</p>
    <template v-if="editable">
      <div class="text-sm text-gray-700">
        <input :id="`member-${member.username}-cmf`"
               class="checkbox"
               type="checkbox"
               v-model="member.canModifyForms"/>
        <label :for="`member-${member.username}-cmf`">
          Modify forms
        </label>
      </div>
      <div class="text-sm text-gray-700">
        <input :id="`member-${member.username}-cmt`"
               class="checkbox"
               type="checkbox"
               v-model="member.canManageTeam"/>
        <label :for="`member-${member.username}-cmt`">
          Manage team
        </label>
      </div>
    </template>
    <template v-else>
      <div class="text-sm text-gray-700">
        <CheckOrCrossIcon :value="member.canModifyForms"/>
        <span>Modify forms</span>
      </div>
      <div class="text-sm text-gray-700">
        <CheckOrCrossIcon :value="member.canManageTeam"/>
        <span>Manage team</span>
      </div>
    </template>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import TeamMember from "../../../models/team/TeamMember";
import CheckOrCrossIcon from "@/components/widgets/CheckOrCrossIcon.vue";

@Component({
  components: {
    CheckOrCrossIcon
  }
})
export default class TeamAccessCard extends Vue {

  @Prop({default: false})
  private editable!: boolean;

  @Prop({required: true})
  private member!: TeamMember;

}

</script>