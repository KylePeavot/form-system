<template>
  <div>
    <BaseQuestion :level="level" :title="title" :guidance="guidance">
      <Popover>
        <button class="popover-menu__item">Edit</button>
        <button class="popover-menu__item">Move</button>
        <button class="popover-menu__item--danger" @click="deleteComponent">Delete</button>
      </Popover>
    </BaseQuestion>
    <Checkbox :id="id" :checkbox-value="value"/>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import Popover from "@/components/core/Popover.vue";

@Component({
  components: {Popover, BaseQuestion, Heading, Checkbox}
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
    private value!: SelectionValue;

    deleteComponent() {
      this.$emit("delete-component");
    }
  }

</script>