<template>
  <div>
    <div id="map-container"/>
    <div v-show="mapLoading"
         style="height: 100%; width: 100%; text-align: center; padding-top: 350px; background: rgba(0, 0, 0, 0.05);"
    >
      <div>
        <a-spin size="large"/>
      </div>
    </div>
    <div></div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue"
import AMapLoader from '@amap/amap-jsapi-loader';

// TODO: read from configuration
const aMapKey = '5c78c6e2cb9839a5e6300c32f77e10d1'; // 暂时使用这种方式，高德文档中有好的推荐的方式
const aMapSecret = '420d0282bcf1e99a1f48cde1cc1f0f96'; // 暂时使用这种方式，高德文档中有好的推荐的方式
// const aMapUrl = 'https://webapi.amap.com/maps?v=1.4.15&key=5c78c6e2cb9839a5e6300c32f77e10d1&callback=onAMapLoad'
const wmsUrl = 'http://localhost:8980/geoserver/langfang_demo/wms'
import {GaodeAMapController} from "./gaodeAMapController";

export default defineComponent({
  name: "GaodeAMap",
  props: {
    mapInitializeParam: {
      type: Object,
      required: true,
    },
  },
  setup(props, {emit}) {
    const mapLoading = ref<boolean>(true);
    const mapController = ref<GaodeAMapController>();

    return {
      mapLoading,
      mapController,
    };
  },
  methods: {
    loadAMapJs() {
      window['_AMapSecurityConfig'] = {
        securityJsCode: aMapSecret,
      }
      AMapLoader.load({
        "key": aMapKey, // 申请好的Web端开发者Key，首次调用 load 时必填
        "version": "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        "plugins": ['AMap.MoveAnimation'], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        const mapInstance = new AMap.Map('map-container', {
          // layers: [new AMap.TileLayer.Satellite()], // 设置卫星图
          center: this.mapInitializeParam.center,
          zoom: this.mapInitializeParam.zoom || 12,
        });
        this.mapController = new GaodeAMapController(AMap, mapInstance);
        mapInstance.on('click', () => {
          this.mapController?.sendEvent('event:map:click');
        });
        this.$emit('loadOk', {
          controller: this.mapController
        })
        this.mapLoading = false;
      }).catch(e => {
        console.log(e);
      })
    }
  },
  created() {
    this.loadAMapJs();
  }
})
</script>

<style lang="scss">
#map-container {
  height: 100%;
  width: 100%;

  * {
    user-select: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
  }
}
</style>
