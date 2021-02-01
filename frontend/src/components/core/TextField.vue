<template>
  <div name="text-field-container" class="question__text-field-container">
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
    <input class="question__text-field" type="text" name="fieldResponse" placeholder=" " v-model="textValue.value"/>
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
  export default class TextField extends Vue {

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
