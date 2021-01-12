<template>
  <div>
    <Heading :level="level">{{ title }}</Heading>
    <p v-if="guidance.length > 0">{{ guidance }}</p>
    <Checkbox :id="id" :checkbox-value="value"/>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/Heading.vue";
import CheckboxValue from "@/models/form/CheckboxValue";
@Component({
  components: {Heading, Checkbox}
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
    private value!: CheckboxValue;

  }

</script>