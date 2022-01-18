export function getDeviceType(){
  if (/(Android)/i.test(navigator.userAgent) && /(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    return 'mobile';
  } else {
    return 'desktop';
  };
}
