<template>
  <div>
    <div v-if="visible" class="container">
      <div class="title">
        <span>人员轨迹</span>
        <a-button type="text" @click="visible = false">X</a-button>
      </div>
      <div class="content">
        <div>
          <div>
            <span style="font-size: 17px;">{{ personData['xcrxm'] }}</span>
          </div>
          <div style="font-size: 13px; padding-top: 10px;">
            <div>
              <span>职责：</span><span>{{ personData['postName'] }}</span>
            </div>
            <div>
              <span>单位：</span><span>{{ personData['branchName'] }}</span>
            </div>
            <div>
              <span>电话：</span><span>{{ personData['xcrzh'] }}</span>
            </div>
          </div>
        </div>
        <div>
          <a-button @click="track?.start()">播放</a-button>
          <a-button @click="track?.pause()">暂停</a-button>
          <a-button @click="track?.resume()">继续</a-button>
          <a-button @click="track?.stop()">停止</a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType, reactive, ref} from "vue"
import {MapController, MapTrack} from "@/components/map/mapDefinitions";

export default defineComponent({
  name: "TrackInfoWindow",
  props: {
    mapController: {
      type: Object as PropType<MapController>,
      required: true,
    },
  },
  setup() {
    const visible: boolean = false;
    const track = ref<MapTrack>();
    const personData = ref<any>();

    return reactive({
      visible,
      track,
      personData,
    });
  },
  methods: {},
  mounted() {
    // 监听更新轨迹面板窗口消息
    this.mapController.listenEvent(
      'event:track-tab:update-track-info-window',
      (
        {track, visible}: { track: MapTrack, visible: boolean }
      ) => {
        this.track = track;
        this.personData = track?.getCustomData() || {};
        console.log(this.personData);
        this.visible = visible;
      });
    // 监听tab切换消息
    this.mapController.listenEvent(
      'event:xcgj:switch-tab',
      (
        {tabName}: { tabName: string }
      ) => {
        if (tabName !== 'xcgj') {
          this.visible = false;
        }
      });
  }
})
</script>

<style lang="scss" scoped>
.container {
  width: 200px;
  height: 280px;
  background-color: white;
  border: 1px solid #dddddd;
  box-shadow: grey 2px 2px 5px -1px;

  .title {
    height: 47px;
    line-height: 47px;
    border-bottom: 1px solid #F7F7F7;
    padding-left: 15px;
  }

  .content {
    padding: 0 15px;
  }
}
</style>
