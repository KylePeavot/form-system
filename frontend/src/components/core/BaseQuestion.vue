<template>
  <div class="question">
    <div class="question__title-row">
      <div class="flex flex-1">
        <Heading class="question__title" :level="baseQuestionProps.level" v-show="!editingTitle">
          {{ baseQuestionProps.title }}
        </Heading>
        <input ref="titleInput" type="text" class="question__title question__title--edit" v-show="editingTitle" v-model="baseQuestionProps.title" @focusout="finishEditing" @keypress.enter="finishEditing" />
        <button class="question__edit-pencil ph-pencil" @click="editTitle" />
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
      <p v-show="!editingGuidance" class="question__guidance-text">
        {{ baseQuestionProps.guidance }}
      </p>
      <input ref="guidanceInput" type="text" class="question__guidance-text question__guidance-text--edit" v-show="editingGuidance" v-model="baseQuestionProps.guidance" @focusout="finishEditing" @keypress.enter="finishEditing" />
      <button class="question__edit-pencil ph-pencil" @click="editGuidance" />
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import Popover from "@/components/core/Popover.vue";

@Component({
  components: {Popover, Heading}
})
export default class BaseQuestion extends Vue {

  @Prop({required: true})
  private baseQuestionProps!: BaseQuestionProps;

  private editingTitle = false;

  private editingGuidance = false;

  private guidanceTextExists = this.baseQuestionProps.guidance.length !== 0;

  editTitle() {
    this.editingGuidance = false;
    this.editingTitle = true;

    Vue.nextTick(() => {
      (this.$refs.titleInput as HTMLElement).focus();
    });
  }

  editGuidance() {
    this.editingTitle = false;
    this.editingGuidance = true;

    Vue.nextTick(() => {
      (this.$refs.guidanceInput as HTMLElement).focus();
    });
  }

  finishEditing() {
    this.editingTitle = false;
    this.editingGuidance = false;

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
    this.guidanceTextExists = false
  }

}

</script>