<template>
  <div>
    <div :class="{'bg-red-50 border-red-600 border-l-8 p-2 mb-1 rounded-md': errorMessage !== null}">
      <p v-if="errorMessage !== null" class="mb-2">{{ errorMessage }}</p>
      <input :id="id" :type="type" :value="wrapper.value" @input="emitInput" @blur="validate" class="question__text-field" />
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, PropSync, Vue, Watch} from "vue-property-decorator";
import Validator from "../../../validators/Validator";
import ValidationWrapper from "@/models/validation/ValidationWrapper";

@Component
export default class TextValidator extends Vue {

  @Prop({default: false})
  private forceShowErrors!: boolean;

  private errorMessage: string | null = null;

  @Watch("forceShowErrors")
  onForceShowChanged(newValue: boolean) {
    this.internalShowErrors = newValue;
    this.validate();
  }

  private internalShowErrors = false;

  @Prop({required: true})
  private id!: string;

  @Prop({default: "text"})
  private type!: string;

  @Model("input", {required: true})
  private wrapper!: ValidationWrapper;

  emitInput(event: Event) {
    const wrapper = new ValidationWrapper((event.target as HTMLInputElement).value, this.wrapper.validators);
    this.$emit("input", wrapper);
  }

  validate() {
    this.errorMessage = null;
    for (const validator of this.wrapper.validators) {
      if (!validator.isValid(this.wrapper)) {
        this.errorMessage = validator.getMessage(this.wrapper);
        return;
      }
    }
  }

  created() {
    if (this.internalShowErrors) {
      this.validate();
    }
  }

}

</script>