export function createBuiltinMarker(AMap: any, name: string, options: object): any {
  let icon;
  let offset: [number, number] = [0, 0];

  switch (name) {
    case 'map_vehicle':
      icon = '/image/map_vehicle.png';
      offset = [-20.5, -49];
      break;
    case 'map_start':
      icon = '/image/map_start.png';
      offset = [-15, -58];
      break;
    case 'map_end':
      icon = '/image/map_end.png';
      offset = [-15, -58];
      break;
    case 'map_online_person':
      icon = '/image/map_online_person.png';
      offset = [-15, -58];
      break;
    case 'map_offline_person':
      icon = '/image/map_offline_person.png';
      offset = [-15, -57];
      break;
    case 'map_selected_person':
      icon = '/image/map_selected_person.png';
      offset = [-20, -64];
      break;
    default:
    // 默认的
  }
  return new AMap.Marker({
    offset: new AMap.Pixel(offset[0], offset[1]),
    icon: icon,
    zoom: 12,
    ...options
  });
}
