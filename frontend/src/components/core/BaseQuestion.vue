<template>
  <div>
    <Heading class="pb-1" :level="level">
      {{ title }}
    </Heading>
    <p v-if="guidance.length !== 0" class="text-gray-500 p-0 pb-2">
      {{ guidance }}
    </p>
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