#include <ESP8266WiFi.h>        // 本程序使用 ESP8266WiFi库
#include <ESP8266WiFiMulti.h>   //  ESP8266WiFiMulti库
#include <WiFiManager.h>
#include <PubSubClient.h>
#include <Ticker.h>

ESP8266WiFiMulti wifiMulti;     // 建立ESP8266WiFiMulti对象,对象名称是 'wifiMulti'

const char* ssid = "2.4";
const char* password = "ZHANG-shubai1";
/*
  const char* ssid = "iPhoneMax";
  const char* password = "1357924680";
*/
//const char* mqttServer = "test.ranye-iot.net";
const char* mqttServer = "47.108.65.135";

Ticker ticker;
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

// ****************************************************
// 注意！以下需要用户根据然也物联平台信息进行修改！否则无法工作!
// ****************************************************

const char* mqttUserName = "admin";         // 服务端连接用户名(需要修改)
const char* mqttPassword = "public";          // 服务端连接密码(需要修改)
String controlTopic = WiFi.macAddress() + "-control"; // 设备控制主题
String reportTopic = WiFi.macAddress() + "-report";   // 设备更新主题
String updateTopic = "mqtt-report";   // 系统报告主题
String willTopicString = "mqtt-will";   // 遗嘱主题
String userControlString;                       // 用户通过MQTT协议发来的设置信息
// ****************************************************

const int sampleWindow = 50; // 以mS为单位的采样窗口宽度（50 mS = 20Hz）
unsigned int sample;
unsigned long startMillis;  // 样本窗口的开始
unsigned int peakToPeak;   // 峰峰值

unsigned int signalMax;
unsigned int signalMin;
//遗嘱相关信息
//const char* willMsg = "esp8266_offline";        // 遗嘱主题信息
const int willQoS = 0;                          // 遗嘱QoS
const bool willRetain = true;                   // 遗嘱保留

const int subQoS = 1;            // 客户端订阅主题时使用的QoS级别（截止2020-10-07，仅支持QoS = 1，不支持QoS = 2）
const bool cleanSession = false; // 清除会话（如QoS>0必须要设为false）
bool led_Status = HIGH;
int count;    // Ticker计数用变量
String productKey = WiFi.macAddress();
String types = "insert";
float temperature = 0.0;
float humidity = 0.0;
float deviceData;
String ledStatus = "off";
String message = "";
String deviceName = "esp8266";
void setup(void) {

  Serial.begin(9600);   // 启动串口通讯
  pinMode(LED_BUILTIN, OUTPUT); //设置内置LED引脚为输出模式以便控制LED

  pinMode(A0, INPUT);

  // 连接WiFi
  connectWifi();

  // 设置MQTT服务器和端口号
  mqttClient.setServer(mqttServer, 1883);
  mqttClient.setKeepAlive(10); // 设置心跳间隔时间
  mqttClient.setCallback(receiveCallback);

  // 连接MQTT服务器
  connectMQTTserver();
  //连接成功后发送设备信息
  ledStatus = "off";
  types = "insert";
  deviceData = deviceF();
  pubMQTTmsg(JSON());

  ticker.attach(1, tickerCount);
  //灯光闪烁提示初始化完成~
  digitalWrite(LED_BUILTIN, HIGH);
  delay(200);
  digitalWrite(LED_BUILTIN, LOW);
  delay(200);
  digitalWrite(LED_BUILTIN, HIGH);
  delay(200);
  digitalWrite(LED_BUILTIN, LOW);
  delay(200);
  digitalWrite(LED_BUILTIN, HIGH);  // 启动后关闭板上LED
}

void tickerCount() {
  count++;
}

String JSON() {
  String payloadJson = "{\"productKey\": \"";
  payloadJson += productKey;
  payloadJson += "\",\"deviceName\": \"";
  payloadJson += deviceName;
  payloadJson += "\",\"ledStatus\": \"";
  payloadJson += ledStatus;
  payloadJson += "\",\"deviceData\":\"";
  payloadJson += deviceData;
  payloadJson += "\",\"humidity\": \"";
  payloadJson += humidity;
  payloadJson += "\",\"temperature\": \"";
  payloadJson += temperature;
  payloadJson += "\",\"types\": \"";
  payloadJson += types;
  payloadJson += "\",\"message\": \"";
  payloadJson += message;
  payloadJson += "\"}";
  return payloadJson;
}

float deviceF() {
  startMillis = millis();  // 样本窗口的开始
  peakToPeak = 0;   // 峰峰值
  signalMax = 0;
  signalMin = 1024;

  // collect data for 50 mS
  while ( millis() - startMillis < sampleWindow ) {
    sample = analogRead(A0);
    if (sample < 1024) { // 抛出错误的读数
      if (sample > signalMax) {
        signalMax = sample;  // 只保存最大级别
      }
      else if (sample < signalMin) {
        signalMin = sample;  // 仅保存最低级别
      }
    }
  }
  peakToPeak = signalMax - signalMin;  // max-min = 峰峰值幅度
  delay(50);
  float volts = (peakToPeak * 5.0) / 1024;  // 转换为伏特
  float temp = (pow(volts, 2) * pow(volts, 3)) / 10;
  return temp;
}

void loop() {
  // 如果开发板未能成功连接服务器，则尝试连接服务器
  if (mqttClient.connected()) { // 如果开发板成功连接服务器
    // 超过阈值时发送信息
    deviceData = deviceF();
    if (deviceData > 80 && ledStatus == "on") {
      types = "report";
      message = "Warning! 设备检测到异常！";
      pubMQTTmsg(JSON());
      message = "";
    }

    // 每隔3秒钟发布一次信息
    if (count >= 3) {
      types = "update";
      temperature++;
      humidity++;
      pubMQTTmsg(JSON());
      count = 0;
    }

    mqttClient.loop();          // 处理信息以及心跳
  } else {                      // 如果开发板未能成功连接服务器
    connectMQTTserver();        // 则尝试连接服务器
  }
}


// 连接MQTT服务器并订阅信息
void connectMQTTserver() {
  // 根据ESP8266的MAC地址生成客户端ID（避免与其它ESP8266的客户端ID重名）
  String clientId = "esp8266-WiFimodel-" + WiFi.macAddress();

  char willTopic[willTopicString.length() + 1];
  strcpy(willTopic, willTopicString.c_str());

  // 连接MQTT服务器
  message = "offline";
  types = "will";
  String offlineMessageString = JSON();
  char willMsg[offlineMessageString.length() + 1];
  strcpy(willMsg, offlineMessageString.c_str());

  if (mqttClient.connect(clientId.c_str(), willTopic, willQoS, willRetain, willMsg)) {
    Serial.println("MQTT Server Connected.");
    Serial.print("Server Address:"); Serial.println(mqttServer);
    Serial.print("ClientId: "); Serial.println(clientId);
    Serial.print("Will Topic: "); Serial.println(willTopic);
    publishOnlineStatus();
    subscribeTopic(); // 订阅指定主题
  } else {
    Serial.print("MQTT Server Connect Failed. Client State:");
    Serial.println(mqttClient.state());
    delay(5000);
  }
}

// 发布信息
void publishOnlineStatus() {
  // 以“-Will”结尾，这是为确保不同ESP8266客户端的遗嘱主题名称各不相同。
  char willTopic[willTopicString.length() + 1];
  strcpy(willTopic, willTopicString.c_str());
  message = "online";
  // 建立设备在线的消息。此信息将向遗嘱主题发布
  String onlineMessageString = JSON();
  char onlineMsg[onlineMessageString.length() + 1];
  
  strcpy(onlineMsg, onlineMessageString.c_str());
  // 向遗嘱主题发布设备在线消息
  if (mqttClient.publish(willTopic, onlineMsg, true)) {
    Serial.print("Published Online Message: "); Serial.println(onlineMsg);
  } else {
    Serial.println("Online Message Publish Failed.");
  }
}

// 收到信息后的回调函数
void receiveCallback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message Received [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println("");
  Serial.print("Message Length(Bytes) ");
  Serial.println(length);

  userControlString = "";
  types = "update";
  for (int i = 0; i < length; i++) {
    userControlString += (char)payload[i];
  }
  if (String(topic) == controlTopic) {

    Serial.print("userControlString = ");
    Serial.println(userControlString);

    if (userControlString == "LEDswitch") {    // 如果收到的信息为“LEDswitch”
      if (ledStatus == "off") {
        led_Status = LOW;
        ledStatus = "on";
        digitalWrite(BUILTIN_LED, led_Status);  // 则点亮LED。
        pubMQTTmsg(JSON());
      } else if (ledStatus == "on") {
        led_Status = HIGH;
        ledStatus = "off";
        digitalWrite(BUILTIN_LED, led_Status); // 否则熄灭LED。
        pubMQTTmsg(JSON());
      }
    }
    if (payload[0] == 'c' && payload[1] == 'd' && payload[2] == 'n') {
      userControlString = "";
      for (int i = 3; i < length; i++) {
        userControlString += (char)payload[i];
      }
      deviceName = userControlString;
      pubMQTTmsg(JSON());
    }
  } else if (String(topic) == reportTopic) {
    Serial.print("userControlString = ");
    Serial.println(userControlString);
    if (userControlString == "Refresh") {    // 如果收到的信息为“Refresh”

      deviceData = deviceF();

      pubMQTTmsg(JSON());
    }
  }
}

// 订阅指定主题
void subscribeTopic() {

  // 建立订阅主题1。主题名称以Taichi-Maker-Sub为前缀，后面添加设备的MAC地址。
  // 这么做是为确保不同设备使用同一个MQTT服务器测试消息订阅时，所订阅的主题名称不同
  String topicString = controlTopic;
  char subTopic1[topicString.length() + 1];
  strcpy(subTopic1, topicString.c_str());

  // 建立订阅主题2
  String topicString2 = "sub-" + WiFi.macAddress();
  char subTopic2[topicString2.length() + 1];
  strcpy(subTopic2, topicString2.c_str());

  // 通过串口监视器输出是否成功订阅主题1以及订阅的主题1名称
  if (mqttClient.subscribe(subTopic1)) {
    Serial.println("Subscrib Topic:");
    Serial.println(subTopic1);
  } else {
    Serial.print("Subscribe Fail...");
  }

  // 通过串口监视器输出是否成功订阅主题2以及订阅的主题2名称
  if (mqttClient.subscribe(subTopic2)) {
    Serial.println("Subscrib Topic:");
    Serial.println(subTopic2);
  } else {
    Serial.print("Subscribe Fail...");
  }
}

// 发布信息
void pubMQTTmsg(String pubMessage) {

  char publishMsg[pubMessage.length() + 1];
  strcpy(publishMsg, pubMessage.c_str());

  String topicString = updateTopic;
  char pubTopic[topicString.length() + 1];
  strcpy(pubTopic, topicString.c_str());

  // 实现ESP8266向主题发布信息
  if (mqttClient.publish(pubTopic, publishMsg)) {
    Serial.println("Publish Topic:"); Serial.println(pubTopic);
    Serial.println("Publish message:"); Serial.println(pubMessage);
  } else {
    Serial.println("Message Publish Failed.");
  }
}

// ESP8266连接wifi
void connectWifi() {

  // ---WiFi准备工作---

  // 建立WiFiManager对象
  //WiFiManager wifiManager;
  // 自动连接WiFi。以下语句的参数是连接ESP8266时的WiFi名称
  //  wifiManager.autoConnect("Taichi-Maker-IoT-Display", "12345678");
  //  wifiManager.autoConnect(ssid, password);

  WiFi.begin(ssid, password);

  //等待WiFi连接,成功连接后输出成功信息
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi Connected!");
  Serial.println("");

  /*
    wifiMulti.addAP(ssid, password); // 将需要连接的一系列WiFi ID和密码输入这里
    wifiMulti.addAP("ssid_from_AP_2", "your_password_for_AP_2"); // ESP8266-NodeMCU再启动后会扫描当前网络
    wifiMulti.addAP("ssid_from_AP_3", "your_password_for_AP_3"); // 环境查找是否有这里列出的WiFi ID。如果有
    Serial.println("Connecting ...");                            // 则尝试使用此处存储的密码进行连接。

    int i = 0;
    while (wifiMulti.run() != WL_CONNECTED) {  // 此处的wifiMulti.run()是重点。通过wifiMulti.run()，NodeMCU将会在当前
    delay(1000);                             // 环境中搜索addAP函数所存储的WiFi。如果搜到多个存储的WiFi那么NodeMCU
    Serial.print(i++); Serial.print(' ');    // 将会连接信号最强的那一个WiFi信号。
    }                                          // 一旦连接WiFI成功，wifiMulti.run()将会返回“WL_CONNECTED”。这也是
                                             // 此处while循环判断是否跳出循环的条件。

    // WiFi连接成功后将通过串口监视器输出连接成功信息
    Serial.println('\n');
    Serial.print("Connected to ");
    Serial.println(WiFi.SSID());              // 通过串口监视器输出连接的WiFi名称
    Serial.print("IP address:\t");
    Serial.println(WiFi.localIP());           // 通过串口监视器输出ESP8266-NodeMCU的IP
  */
}
