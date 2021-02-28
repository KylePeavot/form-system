<template>
  <div class="question">
    <div class="question__title-row">
      <div class="flex flex-1">
        <EditableComponent edit-component-css="question__title question__edit text-2xl" v-model="baseQuestionProps.title" @finish-editing="editTitle">
          <Heading class="question__title" :level="baseQuestionProps.level">
            {{ baseQuestionProps.title }}
          </Heading>
        </EditableComponent>
      </div>
      <div class="question__popup">
        <Popover>
          <button class="popover-menu__item" v-show="!guidanceTextExists" @click="addGuidance">Add guidance</button>
          <button class="popover-menu__item" v-show="guidanceTextExists" @click="removeGuidance">Remove guidance</button>
          <slot></slot>
        </Popover>
      </div>
    </div>
    <div v-if="baseQuestionProps.guidance.length !== 0" class="question__guidance-container">
      <EditableComponent edit-component-css="question__guidance-text question__edit" v-model="baseQuestionProps.guidance" @finish-editing="editGuidance">
        <p class="question__guidance-text">
          {{ baseQuestionProps.guidance }}
        </p>
      </EditableComponent>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/componentExtras/Heading.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import Popover from "@/components/core/Popover.vue";
import EditableComponent from "@/components/core/componentExtras/EditableComponent.vue";

@Component({
  components: {EditableComponent, Popover, Heading}
})
export default class BaseQuestion extends Vue {

  @Prop({required: true})
  private baseQuestionProps!: BaseQuestionProps;

  private guidanceTextExists = this.baseQuestionProps.guidance.length !== 0;


  editTitle(newTitle: string) {
    this.baseQuestionProps.title = newTitle;
    this.finishEditing();
  }

  editGuidance(newGuidance: string) {
    this.baseQuestionProps.guidance = newGuidance;
    this.finishEditing();
  }

  finishEditing() {
    this.$forceUpdate();
    if (this.baseQuestionProps?.guidance.length === 0) { //if the user has deleted the guidance text completely
      this.guidanceTextExists = false;
    }

    this.$emit("finish-editing", this.baseQuestionProps);
  }

  addGuidance() {
    this.baseQuestionProps!.guidance = "Guidance text";
    this.guidanceTextExists = true;
  }

  removeGuidance() {
    this.baseQuestionProps!.guidance = "";
    this.guidanceTextExists = false;
  }

}

</script>