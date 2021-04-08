<template>
  <div name="text-field-container" class="question__text-field-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)" :current-form-display-mode="currentFormDisplayMode">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <input :class="{'question__text-field':true, 'bg-gray-100':(!currentFormDisplayMode.isFill)}" type="text" name="fieldResponse" :disabled="!currentFormDisplayMode.isFill" placeholder=" " v-model="textValue._value" @input="updateProps"/>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Heading from "./componentExtras/Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";
import Popover from "@/components/core/Popover.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
import SelectionValue from "@/models/form/SelectionValue";

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

    @Model("input", {required: true})
    private textValue!: TextValue;

    @Prop({required: true})
    private currentFormDisplayMode!: CurrentFormDisplayMode;

    private baseQuestionProps: BaseQuestionProps | undefined;

    created() {
      this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
    }

    deleteComponent() {
      this.$emit("delete-component");
    }

    updateProps() {
      this.$emit('props-updated', this.$props);
    }
  }
</script>