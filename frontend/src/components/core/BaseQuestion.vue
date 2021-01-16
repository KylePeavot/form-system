<template>
  <div>
    <div class="question__title-row w-auto max-w-sm" style="display: flex">
      <div class="question__title" style="flex: 1">
        <Heading class="question__title" :level="level">
          {{ title }}
        </Heading>
      </div>
      <div class="question__popup" style="flex: 0">
        <slot>
        </slot>
      </div>
    </div>
    <div class="question__guidance">
      <p v-if="guidance.length !== 0" class="question__guidance-text">
        {{ guidance }}
      </p>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";

@Component({
  components: {Heading}
})
export default class BaseQuestion extends Vue {

  @Prop({required: true})
  private level!: number;

  @Prop({required: true})
  private title!: string;

  @Prop({required: true})
  private guidance!: string;

}

</script>