export function getDeviceType(){
  if (/(Android)/i.test(navigator.userAgent) && /(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    console.log("mobile")
  } else {
    console.log("desktop")
  };
}
