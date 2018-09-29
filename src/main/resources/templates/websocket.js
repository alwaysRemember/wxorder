/**
 * websocket使用 可以直接移植到项目里面
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/23 10:52
 **/

let websocket = null;

if ('WebSocket' in window) {
  /*使用项目域名 和java中webscoket地址*/
  websocket = new WebSocket('ws://www.baidu.com/webSocket');
} else {
  throw new Error("浏览器不支持websocket！");
}

// 建立websocket连接
websocket.onopen = () => {
  console.log("*******建立websocket连接*******");
};

// 关闭websocket连接
websocket.onclose = () => {
  console.log('*******关闭websocket连接*******')
};


// 有消息的时候执行
websocket.onmessage = (ev) =>{
  console.log('*******收到消息*******');
  console.log(`消息内容: ${ev.data}`);
  console.log('*********************');
};

// 发生错误
websocket.onerror = ()=>{
  console.log('*******websocket通信发生错误*******');
  throw new Error("websocket通信发生错误");
};

// 窗口关闭事件
websocket.onbeforeunload = ()=>{
  console.log('*******窗口关闭*******');
  websocket.close();
}



