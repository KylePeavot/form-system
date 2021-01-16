<template>
  <div class="py-2">
    <BaseQuestion :level="level" :title="title" :guidance="guidance">
      <Popover>
        <div slot-scope="{ showMenu, togglePopper, closePopper }" @focusout="closePopper" @click="togglePopper">
          <button name="popper-button" class="focus:outline-none">
            ...
          </button>
          <div name="popper-menu" v-show="showMenu" class="w-28 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
            <a href="#edit" @click="togglePopper" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">Edit</a>
            <a href="#move" @click="togglePopper" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">Move</a>
            <a href="#delete" @click="togglePopper" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">Delete</a>
          </div>
        </div>
      </Popover>
    </BaseQuestion>
    <input class="question__text-field" type="text" name="fieldResponse" placeholder=" " v-model="textValue.value"/>
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
  export default class TextField extends Vue {

    @Prop({required: true})
    private level!: number;

    @Prop({required: true})
    private title!: string;

    @Prop({default: ""})
    private guidance!: string;

    @Prop({required: true})
    private textValue!: TextValue;
  }

</script>
