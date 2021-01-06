<template>
  <div class="py-2">
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
    <input class="question__text-field" type="text" name="fieldResponse" placeholder=" " v-model="text"/>
  </div>
</template>

<script lang="ts">

  import {Component, Prop, Vue, Watch} from "vue-property-decorator";
  import Heading from "./Heading.vue";
  import BaseQuestion from "@/components/core/BaseQuestion.vue";

  @Component({
    components: {BaseQuestion, Heading}
  })
  export default class TextField extends Vue {

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
