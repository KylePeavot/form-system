<template>
  <div class="py-2">
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
    <textarea class="question__text-area" name="fieldResponse" rows="4" v-model="text"/>
  </div>
</template>

<script lang="ts">

  import {Component, Prop, Vue, Watch} from "vue-property-decorator";
  import Heading from "./Heading.vue";
  import BaseQuestion from "@/components/core/BaseQuestion.vue";

  @Component({
    components: {BaseQuestion, Heading}
  })
  export default class TextArea extends Vue {

    @Prop({required: true})
    private level!: number;

    @Prop({required: true})
    private title!: string;

    @Prop({default: ""})
    private guidance!: string;

    private text = "";

    @Watch("text")
    textUpdated(updatedValue: string) {
      this.$emit("change", updatedValue);
    }
  }

</script>
