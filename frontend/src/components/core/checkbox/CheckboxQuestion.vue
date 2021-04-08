<template>
  <div name="checkbox-question-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)" @move-component="moveComponent($event)" @delete-component="deleteComponent" :current-form-display-mode="currentFormDisplayMode" />
    <Checkbox :id="id" :selection-value="selectionValue" :current-form-display-mode="currentFormDisplayMode" :is-deletable="false" :can-remove="false" @props-updated="updateProps"/>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/componentExtras/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";
import {FormDisplayModeEnum} from "@/models/form/FormDisplayModeEnum";
import currentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

@Component({
  components: {BaseQuestion, Heading, Checkbox}
})
  export default class CheckboxQuestion extends Vue {

    @Prop({required: true})
    private id!: string;

    @Prop({required: true})
    private title!: string;

    @Prop({default: ""})
    private guidance!: string;

    @Prop({default: 2})
    private level!: number;

    @Model("input", {required: true})
    private selectionValue!: SelectionValue;

    @Prop({required: true})
    private currentFormDisplayMode!: CurrentFormDisplayMode;

    private baseQuestionProps: BaseQuestionProps | undefined;

    created() {
      this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
    }

    updateProps() {
      this.$emit('props-updated', this.$props);
    }

    moveComponent(direction: string) {
      this.$emit('move-component', direction);
    }

    deleteComponent() {
      this.$emit("delete-component");
    }
  }

</script>