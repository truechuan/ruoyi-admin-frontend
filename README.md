我改了一个版本ruoyi-vue
1、去掉rerdis，改成本地缓存
2、去掉nginx部署，直接将静态资源文件通过maven插件打到resources/static下
3、更改vue router为hash模式，不然链接打开会报错401

访问地址:${host}/index

1、直接将前端静态资源写入frontend/resources下,支持直接部署

2、静态文件运营
2.1、npm install
2.2、npm run dev

3、整个项目运行
-需要针对静态资源编译,mvn compile,或者针对前端项目直接编译
-运行springboot项目

git初始化部分问题解决:https://blog.csdn.net/kuangdacaikuang/article/details/84632883
