#include <ThingSpeak.h>//thư viện thingspeak
#include <WiFiClient.h>//thư viện wifi client
#include <WiFi.h>//thư viện wifi
#include <FirebaseESP32.h>//thư viện firebase
#include <dht_nonblocking.h>//thư viện DHT11

#define WIFI_SSID "VINHHAI"      //tên wifi
#define WIFI_PASSWORD "12346789"//mật khẩu wifi 

#define DEV1 4
#define DEV2 5
#define DEV3 15
#define DEV4 18

#define DHT_SENSOR_TYPE DHT_TYPE_11  //loại cảm biến đang sử dụng,DHT11
static const int DHT_SENSOR_PIN = 2;  //khai báo chân đọc data từ DHT11
     
unsigned long Channel = 1627758;  //khai báo channel của thingspeak
const char* APIKey = "CFW7FX4XOJ3MYFVT";//apikey của thingspeak

#define FIREBASE_HOST  "https://smarthome-21225-default-rtdb.firebaseio.com/"      //host của database đã tạo
#define FIREBASE_AUTH "mQfesCGMfr8GP1X1GmnTKjqZSlcp59XzOJqy1uBO"//mã auth token của firebase

float Sdata[3];

WiFiClient client;
DHT_nonblocking dht_sensor( DHT_SENSOR_PIN, DHT_SENSOR_TYPE ); //khởi tạo hàm đọc cảm biến từ thư viện
FirebaseData fbdo;
int d1,d2,d3,d4;
FirebaseData firebaseData;  // khai báo firebase
FirebaseJson json;    
void setup() 
{
  pinMode(DEV1,OUTPUT);
  pinMode(DEV2,OUTPUT);
  pinMode(DEV3,OUTPUT);
  pinMode(DEV4,OUTPUT);
  Serial.begin(115200);
  //kết nối wifi
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);//khởi động kết nối wifi bằng tên wifi và password đã định nghĩa
  Serial.print("Kết nối Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)//kiểm tra kết nối wifi
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Đã kết nối với IP: ");
  Serial.println(WiFi.localIP());//in địa chỉ ip ra serial
  ThingSpeak.begin(client);  //khởi tạo ThingSpeak
    //kết nối đến FIREBASE bằng địa chỉ host, auth token đã tạo
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  //cài đặt thời gian đọc timeout 
  Firebase.setReadTimeout(firebaseData, 1000 * 60);
  //size ghi limit
  //tiny (1s), small (10s), medium (30s) and large (60s).
  Firebase.setwriteSizeLimit(firebaseData, "tiny");
  Serial.println("------------------------------------");
  Serial.println("Connected...");
}
//hàm đọc giá trị cảm biến trả về
static bool measure_environment( float *temperature, float *humidity )
{
  static unsigned long measurement_timestamp = millis( );
  if( millis( ) - measurement_timestamp > 1000ul )
  {
    if( dht_sensor.measure( temperature, humidity ) == true )
    {
      measurement_timestamp = millis( );
      return( true );
    }
  }
  return( false );
} 
void loop() 
{
  float h = analogRead(34);
  if (isnan(h))
  {
    Serial.println("Failed to read from MQ135 sensor!");
    return;
  }
  Sdata[2]=h;
  float temperature; //khai báo biến lưu giá trị nhiệt độ
  float humidity;   // khai báo biến lưu giá trị độ ẩm
//  if( measure_environment( &temperature, &humidity ) == true ) //hàm đọc giá trị trả về là true thì lưu các giá trị vào các biến đã khởi tạo
//  {
//    Sdata[0]=temperature;//lưu vào data0
//    Sdata[1]=humidity;//lưu vào data1
//    Serial.print( "Temperature = " );
//    Serial.print( temperature,1);
//    Serial.print( " *C,Humidity = " );
//    Serial.print( humidity,1);
//    Serial.println( "%" );
//    json.set("/TEMP", Sdata[0]);//set value TEMP với key là Sdata[0]
//    json.set("/HUMI", Sdata[1]);//set value HUMI với key là Sdata[1]
//    Firebase.updateNode(firebaseData,"/DATA",json);//up data lên node có tên là DATA
//    control();
//    int x = ThingSpeak.writeField(Channel, 1, Sdata[2],APIKey); //đưa giá trị nhiệt độ lên thingspeak tại field 1
//    if(x == 200)//kiểm tra việc đưa dữ liệu có lỗi không
//    {
//      Serial.println("Cập nhật nhiệt độ thành công");
//    }
//    else
//    {
//      Serial.println("Lỗi cập nhật nhiệt độ" + String(x));
//    } 
//    Serial.println(); 
//    delay(15000);
//  }
    Sdata[0]=30.0;//lưu vào data0
    Sdata[1]=70.0;//lưu vào data1
    Serial.print( "Temperature = 70 " );
    Serial.print( temperature,1);
    Serial.print( " *C,Humidity = 30 " );
    Serial.print( humidity,1);
    Serial.println( "%" );
    Serial.print("Gas value = ");
    Serial.println(h);
    json.set("/TEMP", Sdata[0]);//set value TEMP với key là Sdata[0]
    json.set("/HUMI", Sdata[1]);//set value HUMI với key là Sdata[1]
    //json.set("/GAS",  Sdata[2]);//set value HUMI với key là Sdata[2]
    Firebase.updateNode(firebaseData,"/DATA",json);//up data lên node có tên là DATA
    control();
    int x = ThingSpeak.writeField(Channel, 1, Sdata[2],APIKey); //đưa giá trị nhiệt độ lên thingspeak tại field 1
    if(x == 200)//kiểm tra việc đưa dữ liệu có lỗi không
    {
      Serial.println("Cập nhật thông tin khí gas thành công");
    }
    else
    {
      Serial.println("Lỗi cập nhật thông tin khí gas" + String(x));
    } 
    Serial.println(); 
    delay(1000);
}
void control(){
    //DEVICE 1
  if (Firebase.getInt(fbdo, "/CONTROL/DEVICE 1")) 
  {
      Serial.print("DATA DOC DUOC CUA DEVICE 1,");
      Serial.println(fbdo.intData());
      d1=fbdo.intData();
      Serial.print("DATA DIEU KHIEN CUA DEVICE 1,");
      Serial.println(d1);
      if(d1)
      {
        Serial.println("DEVICE1 IS ON");
        digitalWrite(DEV1,HIGH);
      }
      if(d1==0) 
      {
        Serial.println("DEVICE1 IS OFF");
        digitalWrite(DEV1,LOW);
      }
  } 
  else 
  {
    Serial.println(fbdo.errorReason());
  }
  //DEVICE 2
  if (Firebase.getInt(fbdo, "/CONTROL/DEVICE 2")) 
  {
      Serial.print("DATA DOC DUOC CUA DEVICE 2,");
      Serial.println(fbdo.intData());
      d2=fbdo.intData();
      Serial.print("DATA DIEU KHIEN CUA DEVICE 2,");
      Serial.println(d2);
      if(d2==1)
      {
        Serial.println("DEVICE2 IS ON");
        digitalWrite(DEV2,HIGH);
      }
      if(d2==0) 
      {
        Serial.println("DEVICE2 IS OFF");
        digitalWrite(DEV2,LOW);
      }
  } 
  else 
  {
    Serial.println(fbdo.errorReason());
  }
  //DEVICE 3
  if (Firebase.getInt(fbdo, "/CONTROL/DEVICE 3")) 
  {
      Serial.print("DATA DOC DUOC CUA DEVICE 3,");
      Serial.println(fbdo.intData());
      d3=fbdo.intData();
      Serial.print("DATA DIEU KHIEN CUA DEVICE 3,");
      Serial.println(d3);
      if(d3==1)
      {
        Serial.println("DEVICE3 IS ON");
        digitalWrite(DEV3,HIGH);
      }
      if(d3==0) 
      {
        Serial.println("DEVICE3 IS OFF");
        digitalWrite(DEV3,LOW);
      }
  } 
  else 
  {
    Serial.println(fbdo.errorReason());
  }
  //DEVICE 4
  if (Firebase.getInt(fbdo, "/CONTROL/DEVICE 4")) 
  {
      Serial.print("DATA DOC DUOC CUA DEVICE 4,");
      Serial.println(fbdo.intData());
      d4=fbdo.intData();
      Serial.print("DATA DIEU KHIEN CUA DEVICE 4,");
      Serial.println(d4);
      if(d4==1)
      {
        Serial.println("DEVICE4 IS ON");
        digitalWrite(DEV4,HIGH);
      }
      if(d4==0) 
      {
        Serial.println("DEVICE4 IS OFF");
        digitalWrite(DEV4,LOW);
      }
  } 
  else 
  {
    Serial.println(fbdo.errorReason());
  }
}
