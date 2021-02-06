<template>
  <div class="bg-gray-700 shadow-md text-white rounded-md p-4 mb-4">
    <slot></slot>
  </div>
</template>

<script lang="ts">


import {Component, Vue} from "vue-property-decorator";

@Component
export default class Banner extends Vue {

}
</script>