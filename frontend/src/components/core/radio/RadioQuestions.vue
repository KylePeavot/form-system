<template>
  <div>
    <Heading :level="level">{{ title }}</Heading>
    <p v-if="guidance.length > 0">{{ guidance }}</p>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
import RadioValue from "@/models/form/RadioValue";
@Component({
  components: {Heading}
})
export default class RadioQuestion extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Prop({default: 2})
  private level!: number;

  @Model("input", {required: true})
  private value!: RadioValue;

}

</script>