## 针对ruoyi-vue的几个改进点
- 去掉rerdis，改成本地缓存
- 去掉nginx部署，直接将静态资源文件通过maven插件打到resources/static下，可以直接使用springboot形式运行前端项目
- 更改vue router为hash模式，不然链接打开会报错401

## 访问地址
${host}/index

