<template>
  <div class="flex">
    <div v-show="!editingField">
      <slot></slot>
    </div>
    <input ref="editInput" type="text" :class="editComponentCss" v-show="editingField" v-model="value" @focusout="finishEditing" @keypress.enter="finishEditing" />
    <button class="question__edit-pencil ph-pencil" @click="editField" />
  </div>
</template>



<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";

@Component
export default class EditableComponent extends Vue {

  @Prop({required: true})
  private editComponentCss!: string;

  @Model("input", {required: true})
  private value!: string;

  private editingField = false;

  editField() {
    this.editingField = true;
    this.$forceUpdate();

    Vue.nextTick(() => {
      (this.$refs.editInput as HTMLElement).focus();
    });
  }

  finishEditing() {
    this.editingField = false;
    this.$emit('finish-editing', this.value);
  }

}

</script>