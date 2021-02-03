<template>
  <div name="text-area-container" class="question__text-area-container">
    <BaseQuestion :level="level" :title="title" :guidance="guidance">
      <Popover>
        <button class="popover-menu__item">Edit</button>
        <button class="popover-menu__item">Move</button>
        <button class="popover-menu__item--danger" @click="deleteComponent">Delete</button>
      </Popover>
    </BaseQuestion>
    <textarea class="question__text-area" name="fieldResponse" rows="4" v-model="textValue.value"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "./Heading.vue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import TextValue from "@/models/form/TextValue";
import Popover from "@/components/core/Popover.vue";

@Component({
    components: {Popover, BaseQuestion, Heading}
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

    deleteComponent() {
      this.$emit("delete-component");
    }
  }

</script>
