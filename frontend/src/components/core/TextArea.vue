<template>
  <div name="text-area-container" class="py-2">
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
    <textarea class="question__text-area" name="fieldResponse" rows="4" v-model="textValue.value"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "./Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";

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

    @Prop({required: true})
    private textValue!: TextValue;
  }

</script>
