<template>
  <div>
    <div class="question__title-row">
      <Heading class="question__title" :level="level">
        {{ title }}
      </Heading>
      <div class="question__popup">
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