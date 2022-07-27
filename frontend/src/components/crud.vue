<template>
  <!--k-standard-page v-bind="standardPage.propList"></k-standard-page-->
</template>
<script lang="ts">
//import { StandardPage as KStandardPage, useStandardPage } from 'daisy.basis';
import { defineComponent, getCurrentInstance, reactive } from "vue";

export default defineComponent({
  name: "crud",
  //components: {
  //  KStandardPage,
  //},
  computed: {
  },
  setup() {
    const instance = getCurrentInstance();
    const parts = (<any>instance).proxy.$route.path.split('/');
//    const standardPage = useStandardPage(null, parts[3], parts[4]);
  const standardPage = "";

    return reactive({
//      standardPage,
    });
  },
});
</script>
<style lang="scss" scoped>
</style>
