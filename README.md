# AiYaSchoolPush
# 目前爱吖校推停止维护

#### 该APP后台由于没有在线上搭建，所以在笔者服务器电脑没打开服务器的时候会出现无法访问，故作者采用代码家开源接口重写APP，
#### 从零搭建Retrofit+Rx+MVP框架APP,欢迎关注：https://github.com/nanchen2251/AiYaGirl
毕业所有文档包含PPT全部上传至https://github.com/nanchen2251/AiYaSchoolPush/tree/master/document  。<br>


爱吖校推如同它的名字一样，是一款校园类信息推送交流平台，这么多的家校互动类软件，你选择了我，这是我的幸运。<br>
从第一次在博客园上写博客到现在，我一次一次地提高博文的质量和代码的可读性，都是为了你们，因为有你们，才有我。<br>
我从一个一个的demo到从0开始做这个app，一路历经艰难险阻，期待你与我进行心灵交流。<br>
因为我也曾遇到各种棘手的问题，到处询问不到答案，<br>
那个时候的我，也许正如现在的你。而我，也还在这条道路上默默前行。<br>



#### 项目中的压缩库地址：https://github.com/nanchen2251/CompressHelper
   
#### * 开源不易，希望给个star或者fork奖励
#### * 博客主页：(http://www.cnblogs.com/liushilin/p/5999246.html)
#### * 开发交流QQ群：（118116509）
#### * 有问题请联系邮箱：（liushilin520@foxmail.com）
#### * app下载后无法登陆的原因：由于本人暂时不能承担阿里云服务器的费用，大家可以下载代码观看，楼主后面会使用花生壳包装域名，以让大家观看效果，有问题还是加入QQ群大家一起讨论吧~
#### * 如果出现启动闪退请把ReleaseActivity的微视频入口注释掉，因为目前微视频的ffmpeg的so文件不完善，所以不支持部分机型；

## 功能说明

- 一套完善的用户系统；
- 即时通讯，音视频推送；
- 视频拍照等社区交流；
- 一套点赞互评的分群体交友；
- 分群体推送；


## 版本更新日志

V2.3.1<br>
 * [优化]替换原有压缩方式，提高图片清晰度；
 * [修复]解决在三星手机上拍照压缩后图片旋转的bug；
 
V2.2.1<br>
 * [新增]图片上传和九宫格查看
 * [新增]仿微信小视频进入微视频时代
 * [优化]减小apk大小
 
## 目前存在的问题是：
1. 视频那个部分手机初始化就蹦，这是so文件不够，需要工作量很大，得自己编译ffmpeg，自己写；
2. 数据不能实时刷新；
3. 短信可能收不到，这是我用的第三方短信服务器，毕竟免费，坑自然多；
4. 缓存，其实做了部分缓存，但是获取没有先去读取缓存数据，缓存在框架里面的；
5. 课表，目前数据是死的，只是一个自定义View填的空数据；
6. 即时通讯板块，有时候会掉线，需要重新登录；
7. 后台代码没有考虑高并发，是处理基本的实现功能状态；
8. ffmpeg不熟悉，所以添加水印不是用的ffmpeg加，而是用php代码叠加图片的方式做的；
9. 代码结构混乱，分包不是特别好；


*以上问题可能还会有补充。
后面会有一系列的代码重构，可能会使用rx和mvp模式，并且分包，但可能是一段时间后。 

## 效果演示
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF.gif)
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF2.gif)
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF3.gif)
## 截图<br>
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen1.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen2.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen3.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen4.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen5.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen6.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen7.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen8.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen9.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen10.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen11.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen12.jpg) 
    ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/screen13.jpg) 

    
## 你也可以请我喝杯咖啡
               **支付宝扫一扫 向我付款**                                             **你也可以微信 向我付款**<br>
   ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/apay.png) 
                  ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/wxpay.png)
                  
### 关于作者
    南尘<br>
    四川成都<br>
    [其它开源](https://github.com/nanchen2251/)<br>
    [个人博客](https://nanchen2251.github.io/)<br>
    [简书](http://www.jianshu.com/u/f690947ed5a6)<br>
    [博客园](http://www.cnblogs.com/liushilin/)<br>
    交流群：118116509<br>
    欢迎投稿(关注)我的唯一公众号，公众号搜索 nanchen 或者扫描下方二维码：<br>
    ![](http://images2015.cnblogs.com/blog/845964/201707/845964-20170718083641599-1963842541.jpg)

    
#### 有码走遍天下 无码寸步难行（引自网络）

> 1024 - 梦想，永不止步!  
爱编程 不爱Bug  
爱加班 不爱黑眼圈  
固执 但不偏执  
疯狂 但不疯癫  
生活里的菜鸟  
工作中的大神  
身怀宝藏，一心憧憬星辰大海  
追求极致，目标始于高山之巅  
一群怀揣好奇，梦想改变世界的孩子  
一群追日逐浪，正在改变世界的极客  
你们用最美的语言，诠释着科技的力量  
你们用极速的创新，引领着时代的变迁  
  
------至所有正在努力奋斗的程序猿们！加油！！  
    
## Licenses
```
 Copyright 2017 nanchen(刘世麟)

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
