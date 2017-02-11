# AiYaSchoolPush
# 爱吖校推开源持续更新中
<br>
   爱吖校推如同它的名字一样，是一款校园类信息推送交流平台，这么多的家校互动类软件，你选择了我，这是我的幸运。<br>
从第一次在博客园上写博客到现在，我一次一次地提高博文的质量和代码的可读性，都是为了你们，因为有你们，才有我。
    我从一个一个的demo到从0开始做这个app，一路历经艰难险阻，期待你与我进行心灵交流。因为我也曾遇到各种棘手的问题，到处询问不到答案，
    那个时候的我，也许正如现在的你。而我，也还在这条道路上默默前行。
    
#### * 开源不易，希望给个star或者fork奖励
#### * 项目地址：(https://github.com/nanchen2251/AiYaSchoolPush)
#### * 博客主页：(http://www.cnblogs.com/liushilin/p/5999246.html)
#### * 开发交流QQ群：（118116509）
#### * 有问题请联系邮箱：（liushilin520@foxmail.com）
#### * app体验下载：（https://github.com/nanchen2251/AiYaSchoolPush/blob/master/AiYa-SchoolPush-v2.2.2.apk)
#### * app下载后无法登陆的原因：由于本人暂时不能承担阿里云服务器的费用，大家可以下载代码观看，楼主后面会使用花生壳包装域名，以让大家观看效果，有问题还是加入QQ群大家一起讨论吧~
##功能说明
- 一套完善的用户系统；
- 即时通讯，音视频推送；
- 视频拍照等社区交流；
- 一套点赞互评的分群体交友；
- 分群体推送；

##版本更新日志
V2.2.1<br>
 * [新增]图片上传和九宫格查看
 * [新增]仿微信小视频进入微视频时代
 * [优化]减小apk大小
 
##目前存在的问题是：
1. 视频那个部分手机初始化就蹦，这是so文件不够，需要工作量很大，得自己编译ffmpeg，自己写；
2. 数据不能实时刷新，就你说那个；
3. 短信可能收不到，这是我用的第三方短信服务器，毕竟免费，坑自然多；
4. 缓存，其实做了部分缓存，但是获取没有先去读取缓存数据，缓存在框架里面的；
5. 课表，目前数据是死的，只是一个自定义View填的空数据；
6. 即时通讯板块，有时候会掉线，需要重新登录；
7. 后台代码没有考虑高并发，是处理基本的实现功能状态；
8. ffmpeg不熟悉，所以添加水印不是用的ffmpeg加，而是用php代码叠加图片的方式做的；
9. 代码结构混乱，分包不是特别好；


*以上问题可能还会有补充。
后面会有一系列的代码重构，可能会使用rx和mvp模式，并且分包，但可能是一段时间后。 

##效果演示
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF.gif)
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF2.gif)
![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/GIF3.gif)
##截图<br>
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
    
##关于作者
    南尘<br>
    四川成都<br>
    [博客园](http://www.cnblogs.com/liushilin/)
    
##你也可以请我喝杯咖啡
               **支付宝扫一扫 向我付款**                                             **你也可以微信 向我付款**<br>
   ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/apay.png) 
                  ![](https://github.com/nanchen2251/AiYaSchoolPush/blob/master/photo/wxpay.png)
