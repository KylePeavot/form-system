<template>
  <div name="text-area-container" class="question__text-area-container">
    <BaseQuestion :base-question-props="baseQuestionProps" >
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <textarea class="question__text-area" name="fieldResponse" rows="4" v-model="textValue.value"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "./componentExtras/Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";

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

    private baseQuestionProps: BaseQuestionProps | undefined;

    created() {
      this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
    }


    deleteComponent() {
      this.$emit("delete-component");
    }
  }

</script>
