define(function(require, factory) {
  'use strict'

  var config = require('./config')

  var mainHost = config.http + config.host + config.path + config.project

  function getWXConfig(data) {
    wx.config({
      debug: false,
      appId: data.appId,
      timestamp: data.timestamp,
      nonceStr: data.nonceStr,
      signature: data.signature,
      jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
    })
  }

  return {
    wxShareInit: function(openingCode) {
      wx.ready(function() {
        wx.onMenuShareAppMessage({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          desc: '11月14日-11月23日NEW LOOK微信抽奖，快来解锁属于你的惊喜大奖！', // 分享描述
          link:
            'http://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'http://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          type: '', // 分享类型,music、video或link，不填默认为link
          dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
          success: function() {},
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })

        wx.onMenuShareTimeline({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          link:
            'http://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'http://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          success: function() {},
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })
      })
    },

    //Interface Function
    doWechatConfig: function(parameter) {
      //获取微信JS-SDK配置信息
      var encodeUrl = encodeURIComponent(window.location.href)
      var url = mainHost + '/service/wechat/jsconfig?url=' + encodeUrl
      $.get(url, parameter, function(json) {
        if (!json.success) {
          // alert(json.status);
        } else {
          if (json.data) {
            getWXConfig(json.data)
          }
        }
      })
    },

    getSessionItem: function(name) {
      var data = sessionStorage.getItem(name)
      return data
    }
  }
})
