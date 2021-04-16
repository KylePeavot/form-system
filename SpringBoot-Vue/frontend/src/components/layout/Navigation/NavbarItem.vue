<template>
  <span class="navbar__item">
    <router-link :to="primaryRoute.url">
      <a class="px-3 py-2 rounded-md text-sm font-medium text-white navbar__link" :class="[{'bg-blue-800': isSelected}]">
        {{ primaryRoute.name }}
        <template v-if="subrouteSize > 0">
          <span>&#9662;</span>
        </template>
      </a>
    </router-link>
    <div class="absolute navbar__submenu invisible" v-if="subrouteSize > 0">
      <div class="navbar-group">
        <div v-for="(value, index) in subroutes" :key="`${index}-${value.name}`" class="navbar-group--item">
          <router-link :to="value.url">
            <a>
              {{ value.name }}
            </a>
          </router-link>
        </div>
      </div>
    </div>
  </span>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import PageDetail, {SubrouteType} from "@/models/navigation/PageDetail";

  @Component
  export default class NavbarItem extends Vue {

    @Prop({default: {}, required: true})
    private primaryRoute!: PageDetail;

    @Prop({default: false})
    private isSelected!: boolean;

    get subroutes(): SubrouteType {
      return this.primaryRoute.subRoutes ?? {};
    }

    get subrouteSize(): number {
      return Object.keys(this.subroutes).length;
    }

  }

</script>

<style lang="scss" scoped>

  .navbar__item {

    &:hover, &:focus-within {

      .navbar__link {
        @apply bg-blue-800;
      }

      .navbar__submenu {
        visibility: visible;
      }
    }

  }
</style>