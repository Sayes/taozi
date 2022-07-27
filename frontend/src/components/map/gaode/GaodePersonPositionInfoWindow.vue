<template>
  <div class="info-window">
    <div class="header">人员信息</div>
    <div class="body">
      <div>
        <span style="font-size: 1.5rem;">{{ personData['displayName'] }}</span>
      </div>
      <div style="margin-top: 10px;">
        <span>职责：</span><span>{{ personData['postName'] }}</span>
      </div>
      <div style="margin-top: 10px;">
        <span>单位：</span><span>{{ personData['branchName'] }}</span>
      </div>
      <div style="margin-top: 10px;">
        <span>电话：</span><span>{{ personData['name']}}</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue"

export default defineComponent({
  name: "GaodePersonPositionInfoWindow",
  props: {
    personData: {
      type: Object,
      required: true,
    }
  },
  setup() {
    return {}
  },
  methods: {}
})
</script>

<style lang="scss" scoped>
.info-window {
  height: 200px;
  width: 230px;
  background-color: white;
  border: 1px solid #dddddd;
  box-shadow: grey 2px 2px 5px -1px;

  .header {
    height: 35px;
    line-height: 35px;
    background-color: #F7F7F7;
    padding-left: 20px;
    font-size: 1.1rem;
  }

  .body {
    padding: 10px 20px;
  }
}
</style>
