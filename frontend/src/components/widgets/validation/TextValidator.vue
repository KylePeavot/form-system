<template>
  <div>
    <div :class="{'bg-red-50 border-red-600 border-l-8 p-2 mb-1 rounded-md': getValidationErrorMessage() !== null}">
      <p v-if="getValidationErrorMessage() !== null" class="mb-2">{{ getValidationErrorMessage() }}</p>
      <input :type="type" :value="wrapper.value" @input="emitInput" class="question__text-field" />
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

  @Watch("forceShowErrors")
  onForceShowChanged(newValue: boolean) {
    this.internalShowErrors = newValue;
  }

  private internalShowErrors = false;

  @Prop({default: "text"})
  private type!: string;

  @Model("input", {required: true})
  private wrapper!: ValidationWrapper;

  emitInput(event: Event) {
    const wrapper = new ValidationWrapper((event.target as HTMLInputElement).value, this.wrapper.validators);
    this.$emit("input", wrapper);
    this.internalShowErrors = true;
  }

  getValidationErrorMessage(): string | null {
    // Don't show validation errors if the field has never been used
    if (!this.internalShowErrors) {
      return null;
    }
    for (const validator of this.wrapper.validators) {
      if (!validator.isValid(this.wrapper)) {
        return validator.getMessage(this.wrapper);
      }
    }
    return null;
  }

}

</script>