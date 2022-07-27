import {MapTrack, MapController, MapPersonPin} from '../mapDefinitions';
import {GaodeTrack} from './gaodeTrack';
import {GaodePersonPin} from './gaodePersonPin';

export class GaodeAMapController implements MapController {
  private readonly AMap: any;
  private readonly mapInstance: any;

  constructor(AMap: any, mapInstance: any) {
    this.AMap = AMap;
    this.mapInstance = mapInstance;
  }

  public setFitView(): void {
    this.mapInstance.setFitView();
  }

  setCenterTo(position: [number, number]): void {
    this.mapInstance.setCenter(position, false, 1500);
  }

  // public showPolyline(option: object, coordinateName?: string): any {
  //   let originalOptionPath = option['path'] || [];
  //   if (!originalOptionPath || originalOptionPath.length < 1) {
  //     return;
  //   }
  //   new Promise(((resolve, reject) => {
  //     if (coordinateName) {
  //       this.AMap.convertFrom(option['path'], coordinateName, (status: string, result: any) => {
  //         if ('complete' !== status || 'ok' !== result?.info) {
  //           reject('转换坐标系失败');
  //         }
  //         let path = [];
  //         for (const location_ of result.locations || []) {
  //           path.push([location_['lng'], location_['lat']]);
  //         }
  //         resolve(path);
  //       })
  //     } else {
  //       resolve(originalOptionPath)
  //     }
  //   })).then((path: any) => {
  //     const polyline = new this.AMap.Polyline({
  //       isOutline: true,
  //       outlineColor: '#eeeeee',
  //       borderWeight: 1,
  //       strokeColor: "#1890FF",
  //       strokeOpacity: 1,
  //       strokeWeight: 5,
  //       // 折线样式还支持 'dashed'
  //       strokeStyle: "solid",
  //       // strokeStyle是dashed时有效
  //       // strokeDasharray: [10, 5],
  //       lineJoin: 'round',
  //       lineCap: 'round',
  //       zIndex: 50,
  //       ...(option || {}),
  //       path,
  //     });
  //     const startMarker = this.createBuiltinMarker('map_start', {
  //       position: path[0],
  //     });
  //     let endMarker = null;
  //     if (path.length > 1) {
  //       endMarker = this.createBuiltinMarker('map_end', {
  //         position: path[path.length - 1],
  //       });
  //     }
  //
  //     let polylineObject = {
  //       polyline,
  //       startMarker,
  //       endMarker,
  //     }
  //     this.polylineList.push(polylineObject);
  //
  //     this.mapInstance.add(polylineObject.polyline);
  //     this.mapInstance.add(polylineObject.startMarker);
  //     this.mapInstance.add(polylineObject.endMarker);
  //   }, reason => {
  //   })
  // }



  public createTrack(polylineOption?: object, passedPolylineOption?: object): MapTrack {
    return new GaodeTrack(
      this.AMap,
      this.mapInstance,
      polylineOption || {},
      passedPolylineOption || {}
    );
  }

  public createPersonPin(position: [number, number], isOnline: boolean, personData: any): MapPersonPin {
    return new GaodePersonPin(
      this.AMap,
      this.mapInstance,
      position,
      isOnline,
      personData,
    );
  }


  private bindingEventsMap: Map<string, Function[]> = new Map<string, Function[]>();

  sendEvent(eventName: string, eventContext?: any): void {
    const bindingEvents = this.bindingEventsMap.get(eventName);
    if (!bindingEvents) {
      return;
    }
    for (const bindingEvent of bindingEvents) {
      bindingEvent(eventContext, this);
    }
  }

  listenEvent(eventName: string, callback: Function): void {
    let bindingEvents = this.bindingEventsMap.get(eventName);
    if (!bindingEvents) {
      bindingEvents = [];
      this.bindingEventsMap.set(eventName, bindingEvents);
    }
    bindingEvents.push(callback)
  }
}
