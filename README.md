# SmartFarming
账号：admin 密码：123456
# 一、框架
浊度传感器+Lora+GPRS+Onenet云平台+App
---
# 五、APP
---
## （1）开发环境
* ubantu18.04
* Android Studio 4.1.2
* JDK8
---
## （2）添加依赖库
* okhttp3：使用okhttp3框架从云平台上下载数据
* Gson：谷歌Json序列/反序列库。用来解析Json数据
* ECharts:百度图表显示
* CardView：自定义卡片式View
---
## （3）登陆界面
* ![](https://github.com/17302550114/SmartFarming/blob/master/%E7%99%BB%E5%BD%95%E7%95%8C%E9%9D%A2.png)
* 主要功能：登陆、验证、记住账号和密码
* 实现方式：SharedPreferences
* 使用sqlite存储用户信息进行登录验证（未实现）
---
## （4）主界面（底部导航栏+ViewPage+Fragment）
* ![](https://github.com/17302550114/SmartFarming/blob/master/%E4%B8%BB%E7%95%8C%E9%9D%A2.png)
### 首页
* 主要功能：降雨量显示、液位显示、图表显示浊度变化
* 实现方式：自定义View显示、Echarts图表实时显示浊度变化
---
### 地图
* 嵌入高德2D地图，实现定位（未实现）
### 服务
* 提供查询服务
### 我的
* 显示用户信息
