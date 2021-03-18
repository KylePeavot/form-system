<template>
  <div name="text-area-container" class="question__text-area-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)" @move-component="moveComponent($event)" @delete-component="deleteComponent" :current-form-display-mode="currentFormDisplayMode"/>
    <textarea :class="{'question__text-area':true, 'bg-gray-100':(!currentFormDisplayMode.isFill)}" name="fieldResponse" rows="4" :disabled="!currentFormDisplayMode.isFill" v-model="textValue.value"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "./componentExtras/Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

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

    @Prop({required: true})
    private currentFormDisplayMode!: CurrentFormDisplayMode;

    private baseQuestionProps: BaseQuestionProps | undefined;

    created() {
      this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
      this.textValue = TextValue.mapTextValueInterfaceToTextValue(this.textValue);
    }

    moveComponent(direction: string) {
      this.$emit('move-component', direction);
    }

    deleteComponent() {
      this.$emit("delete-component");
    }

    updateProps(baseQuestionProps: BaseQuestionProps) {
      this.$emit('props-updated', {title: baseQuestionProps.title, guidance: baseQuestionProps.guidance});
    }
  }

</script>
