import {defineComponent, reactive, ref, unref, computed, watch, PropType, createVNode, render} from 'vue';
import {MapPersonPin} from '@/components/map/mapDefinitions';
import {createBuiltinMarker} from './gaodeBuiltinMarker';
import GaodePersonPositionInfoWindow from "@/components/map/gaode/GaodePersonPositionInfoWindow.vue";

export class GaodePersonPin implements MapPersonPin {
  private readonly AMap: any;
  private readonly mapInstance: any;
  private customData: any;

  private readonly position: [number, number];
  private readonly isOnline: boolean;
  private readonly personData: boolean;
  private marker: any;
  private selectedMarker: any;
  private infoWindow: any;
  private hidden: boolean = false;

  constructor(AMap: any, mapInstance: any, position: [number, number], isOnline: boolean, personData: any) {
    this.AMap = AMap;
    this.mapInstance = mapInstance;
    this.position = position;
    this.isOnline = isOnline;
    this.personData = personData;

    this.init();
  }

  private init(): void {
    const this_ = this;

    if (this.isOnline) {
      this.marker = createBuiltinMarker(this.AMap, 'map_online_person', {
        position: this.position,
      });
    } else {
      this.marker = createBuiltinMarker(this.AMap, 'map_offline_person', {
        position: this.position,
      });
    }
    this.selectedMarker = createBuiltinMarker(this.AMap, 'map_selected_person', {
      position: this.position,
    });

    // 创建信息面板
    const node = document.createElement('div');
    document.body.appendChild(node);
    let vNodeInstance = createVNode(GaodePersonPositionInfoWindow, {
      personData: this.personData,
    });
    render(vNodeInstance, node);
    this.infoWindow = new this.AMap.InfoWindow({
      isCustom: true,
      offset: new this.AMap.Pixel(0, 205),
      content: vNodeInstance.el,
    });

    this.hide();
    this.mapInstance.add(this.marker);
    this.mapInstance.add(this.selectedMarker);
  }

  remove(): void {
    this.marker.remove();
    this.selectedMarker.remove();
  }

  show(): void {
    this.marker.show();
    this.hidden = false;
  }

  hide(): void {
    this.marker.hide();
    this.selectedMarker.hide();
    this.hidden = true;
  }

  bindEvent(element: string, eventName: string, callback: Function): void {
    const this_ = this;
    this[element].on(eventName, (e: any) => {
      callback(this_, e);
    })
  }

  setCustomData(customData: any): void {
    this.customData = customData;
  }

  getCustomData(): any {
    return this.customData;
  }

  showOnMapCenter(): void {
    this.mapInstance.setCenter(this.position, false, 1000);
  }

  openInfoWindow(): void {
    this.infoWindow.open(this.mapInstance, this.position);
  }

  closeInfoWindow(): void {
    this.infoWindow.hide();
  }

  changeSelectStatus(isSelected: boolean): void {
    if (this.hidden) {
      return;
    }
    if (isSelected) {
      this.marker.hide();
      this.selectedMarker.show();
    } else {
      this.selectedMarker.hide();
      this.marker.show();
    }
  }

  getPersonData(): any {
    return this.personData;
  }
}
