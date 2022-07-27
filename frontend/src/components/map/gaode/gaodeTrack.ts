import {MapTrack} from '@/components/map/mapDefinitions';
import {createBuiltinMarker} from './gaodeBuiltinMarker'

export class GaodeTrack implements MapTrack {
  private readonly AMap: any;
  private readonly mapInstance: any;
  private customData: any;

  // 静态显示轨迹相关
  private readonly path: Array<[number, number]>;
  private startPosition: [number, number] = [-1, -1];
  private endPosition: [number, number] = [-1, -1];
  private startMarker: any;
  private endMarker: any;
  private polyline: any;

  // 轨迹动画相关
  private passedPolyline: any;
  private vehicleMarker: any;
  private readonly pathTotal;
  private isMovingElementOnMap = false;
  private movingStatus: number = 0; // 0 创建就绪，1 播放中 2 暂停 3 停止
  private moveOverPathCount: number = 0;

  constructor(AMap: any, mapInstance: any, polylineOption: any, passedPolylineOption: any) {
    this.AMap = AMap;
    this.mapInstance = mapInstance;
    this.path = polylineOption?.path || [];
    this.pathTotal = this.path.length;
    if (this.pathTotal < 2) {
      throw new Error('坐标数小于2，无法生成轨迹');
    }
    this.init(polylineOption, passedPolylineOption);
  };

  private init(polylineOption: any, passedPolylineOption: any): void {
    const this_: any = this;
    this.startPosition = polylineOption.path[0];
    this.endPosition = polylineOption.path[this.pathTotal - 1];

    this.startMarker = createBuiltinMarker(this.AMap, 'map_start', {
      position: this.startPosition,
    });
    this.endMarker = createBuiltinMarker(this.AMap, 'map_end', {
      position: this.endPosition,
    });
    this.vehicleMarker = createBuiltinMarker(this.AMap, 'map_vehicle', {
      position: this.startPosition,
    });
    this.polyline = new this.AMap.Polyline({
      isOutline: true,
      outlineColor: '#ffeeff',
      borderWeight: 1,
      // strokeColor: "#00800f",
      strokeColor: "#1890FF",
      strokeOpacity: 1,
      strokeWeight: 5,
      strokeStyle: "solid",
      lineJoin: 'round',
      lineCap: 'round',
      zIndex: 50,
      showDir: true,
      ...(polylineOption || {}),
      path: this.path,
    });
    this.passedPolyline = new this.AMap.Polyline({
      isOutline: true,
      outlineColor: '#eeeeee',
      borderWeight: 1,
      strokeColor: "#1890FF",
      strokeOpacity: 1,
      strokeWeight: 5,
      strokeStyle: "solid",
      lineJoin: 'round',
      lineCap: 'round',
      zIndex: 50,
      ...(passedPolylineOption || {})
    });

    this.vehicleMarker.on('moving', function (e: any) {
      this_.passedPolyline.setPath(e.passedPath);
      this_.movingStatus = 1;
      this_.polyline.setOptions({
        showDir: true,
        strokeColor: "#00800f",
      });
      this_.polyline.setStrokeColor = '#00800f';
    });
    this.vehicleMarker.on('moveend', (e: any) => {
      this_.moveOverPathCount++;
      if (this_.moveOverPathCount >= this_.pathTotal) {
        console.log('track move end')
      }
    });

    this.hide();
    this.mapInstance.add([
      this.startMarker,
      this.endMarker,
      this.polyline,
      this.passedPolyline,
      this.vehicleMarker,
    ]);
  }

  public show(): void {
    // this.mapInstance.add([this.polyline, this.startMarker, this.endMarker]);
    // this.mapInstance.add(this.startMarker);
    // this.mapInstance.add(this.endMarker);
    this.startMarker.show();
    this.endMarker.show();
    this.polyline.show();
  }

  hide(): void {
    this.stop();
    this.startMarker.hide();
    this.endMarker.hide();
    this.polyline.hide();
    this.passedPolyline.hide();
    this.vehicleMarker.hide();
  }

  remove(): void {
    this.stop();
    this.mapInstance.remove([
      this.startMarker,
      this.endMarker,
      this.polyline
    ]);
    // this.mapInstance.remove(this.endMarker);
    // this.mapInstance.remove(this.polyline);
  }

  bindEvent(element: string, eventName: string, callback: Function): void {
  }

  setCustomData(customData: any): void {
    this.customData = customData;
  }

  getCustomData(): any {
    return this.customData;
  }

  showOnMapCenter(): void {
    this.mapInstance.setCenter(this.startPosition, false, 200);
  }

  // 0 创建就绪，1 播放中 2 暂停 3 停止
  public start(options?: object): void {
    if (!this.isMovingElementOnMap) {
      this.passedPolyline.show();
      this.vehicleMarker.show();
      this.isMovingElementOnMap = true;
    }
    // 只有刚创建或者停止才能播放
    if ([1, 2].includes(this.movingStatus)) {
      return;
    }

    this.vehicleMarker.moveAlong(this.path, {
      speed: 10000,
      autoRotation: false,
      ...(options || {})
    });
    this.movingStatus = 1;
  }

  public pause(): void {
    if (this.movingStatus !== 1) {
      return
    }
    this.vehicleMarker.pauseMove();
    this.movingStatus = 2;
  }

  public resume(): void {
    if (this.movingStatus !== 2) {
      return
    }
    this.vehicleMarker.resumeMove();
    this.movingStatus = 1;
  }

  public stop(callback?: Function): void {
    this.polyline.setOptions({
      strokeColor: "#1890FF",
    });
    if (this.isMovingElementOnMap && (this.movingStatus === 1 || this.movingStatus === 2)) {
      this.movingStatus = 3;
      this.vehicleMarker.stopMove();
      this.passedPolyline.hide();
      this.vehicleMarker.hide();
    }
    this.isMovingElementOnMap = false;
    if (callback) {
      callback();
    }
  }
}
