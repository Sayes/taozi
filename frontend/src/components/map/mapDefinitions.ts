// 地图覆盖物
export interface MapCovering {
  show(): void;

  hide(): void;

  remove(): void;

  bindEvent(element: string, eventName: string, callback: Function): void;

  setCustomData(customData: any): void;

  getCustomData(): any;

  showOnMapCenter(): void;
}

// 轨迹覆盖物
export interface MapTrack extends MapCovering {
  start(options?: object): void;

  pause(): void;

  resume(): void;

  stop(callback?: Function): void;
}

// 人员图钉覆盖物
export interface MapPersonPin extends MapCovering {
  openInfoWindow(): void;

  closeInfoWindow(): void;

  changeSelectStatus(isSelected: boolean): void;

  getPersonData(): any;
}

export interface MapController {
  setCenterTo(position: [number, number]): void;

  createTrack(polylineOption?: object, passedPolylineOption?: object): MapTrack;

  createPersonPin(position: [number, number], isOnline: boolean, personData: any): MapPersonPin;

  sendEvent(eventName: string, eventContext?: any): void;

  listenEvent(eventName: string, callback: Function): void;
}
