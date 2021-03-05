<template>
  <div class="flex">
    <div v-show="!editingField">
      <slot></slot>
    </div>
    <textarea ref="editInput" type="text" :class="editComponentCss" rows="1" v-show="editingField" v-model="value" @focusout="finishEditing" @keypress.enter="finishEditing" />
    <button class="hidden-button ph-pencil" @click="editField" />
  </div>
</template>
<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
@Component
export default class EditableComponent extends Vue {

  @Prop({required: false})
  private editComponentCss!: string | undefined;

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