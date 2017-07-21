# 前言

我们的项目和业务中对于微信接口有如下的要求：

* 支持多种应用：移动、网页、小程序、第三方平台
* 支持多个应用

在github上搜了一下给java用的微信api sdk，有些项目封装的不错，但是多多少少不太符合我们的要求：比如通过文件配置，只能同时支持一个应用；或者是项目本身是为了一个微信公众号服务的，整体是一个web服务，不是一个很好的sdk；有比较长的时间不再更新了。

这些项目有：

* 微信公众号服务：<https://github.com/caspar-chen/caswechat>
* 支持的比较全面的sdk，封装的不错：<https://github.com/liyiorg/weixin-popular>
* 轻量sdk：<https://github.com/ihaolin/wechat>
* 封装不完整，还在开发中：<https://github.com/cuter44/wxpay-sdk>
* 只支持一个的微信公众号sdk，好久没有更新：<https://github.com/sword-org/wechat4j>
* 一个不错的微信sdk，可惜是PHP版：[EasyWechat](https://easywechat.org/)，<https://github.com/overtrue/wechat>

所以，我们又造了一遍轮子，实现了一个自己的微信java sdk，对微信的api进行封装。

# 计划
我们会针对开发平台、公众平台下的移动、小程序、公众号等应用逐步完善api封装

* [ ] 移动应用的用户接口
* [ ] 公众号接口
* [ ] 开放平台接口
