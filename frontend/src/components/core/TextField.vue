<template>
  <div name="text-field-container" class="question__text-field-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="finishEditing">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger">Delete</button>
    </BaseQuestion>
    <input class="question__text-field" type="text" name="fieldResponse" placeholder=" " v-model="textValue.value"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "./Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";
import Popover from "@/components/core/Popover.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";

@Component({
    components: {Popover, BaseQuestion, Heading}
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

    private baseQuestionProps: BaseQuestionProps | undefined;


    created() {
      this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
    }





  }

</script>
