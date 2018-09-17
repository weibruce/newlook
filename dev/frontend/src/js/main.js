/**
 * NEWLOOK 1001 main: includ newlookOpening class and newlookMybag class.
 *
 * @author Bruce Wei
 * @version 0.9.6
 *
 * Date Created: 7.17.2017
 * Last Updated: 9.25.2017
 *
 * http://www.newlook.com/
 * Copyright (c) 2017 NEW LOOK (http://www.newlook.com)
 * Licensed under the MIT License
 */

;(function($) {
  /**
   * newlookOpening: Code for index page, show event infor and rules, user can shake and win prize cupoun.
   *
   * @author Bruce Wei
   * @version 2.1
   *
   * Date Created: 7.17.2017
   * Last Updated: 9.19.2017
   */
  $.fn.newlookOpening = function(options) {
    var settings = $.extend(
      {
        http: 'http://',
        https: 'https://',
        host: '',
        port: '',
        path: '/',
        project: ''
      },
      options
    )

    var http = settings.http
    var https = settings.https
    var host = settings.host
    var port = settings.port
    var path = settings.path == '/' ? '' : settings.path
    var project = settings.project

    var mainHost = https + host + path + project
    var openingCode
    var uid
    var from = 'share'
    var shakeCount = 0
    var prizeData
    var shakeFlag = false

    //shake variable
    var last_update = 0
    var x = (y = z = last_x = last_y = last_z = 0)
    var SHAKE_THRESHOLD = 1000

    main()

    //Function
    function main() {
      openingCode = getRequest('openingCode')
      from = getRequest('from')

      if (openingCode) {
        wxShareInit()

        doEventOpeningData()
      }
    }

    function wxShareInit() {
      wsReady()
    }

    function wsReady() {
      wx.ready(function() {
        wx.onMenuShareAppMessage({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          desc: '11月14日-11月23日NEW LOOK微信抽奖，快来解锁属于你的惊喜大奖！', // 分享描述
          link:
            'https://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'https://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          type: '', // 分享类型,music、video或link，不填默认为link
          dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
          success: function() {
            callShareFun()
          },
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })

        wx.onMenuShareTimeline({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          link:
            'https://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'https://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          success: function() {
            callShareFun()
          },
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })
      })
    }

    function getRequest(name) {
      var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
      var r = window.location.search.substr(1).match(reg)
      if (r != null) return decodeURI(r[2])
      return null
    }

    function randomWord(randomFlag, min, max) {
      var str = '',
        range = min,
        arr = [
          '0',
          '1',
          '2',
          '3',
          '4',
          '5',
          '6',
          '7',
          '8',
          '9',
          'a',
          'b',
          'c',
          'd',
          'e',
          'f',
          'g',
          'h',
          'i',
          'j',
          'k',
          'l',
          'm',
          'n',
          'o',
          'p',
          'q',
          'r',
          's',
          't',
          'u',
          'v',
          'w',
          'x',
          'y',
          'z'
        ]
      // arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

      if (randomFlag) {
        range = Math.round(Math.random() * (max - min)) + min
      }

      for (var i = 0; i < range; i++) {
        var pos = Math.round(Math.random() * (arr.length - 1))
        str += arr[pos]
      }
      return str
    }

    function formatDate(time) {
      var getTime = new Date(time)
      var year = getTime.getFullYear() + '年'
      var month = getTime.getMonth() + 1 + '月'
      var date = getTime.getDate() + '日'
      var formarDate = [year, month, date].join('')
      return formarDate
    }

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

    function isCoupon(oid) {
      return oid > 2
    }

    function initOpeningData(data) {
      var openingData = data
      uid = openingData.openId
      sessionStorage.setItem('openid', openingData.openId)
      sessionStorage.setItem('openingCode', openingCode)

      if (uid) {
        doWechatConfig()

        addOpeningEvent()

        if (window.DeviceOrientationEvent) {
          window.addEventListener('devicemotion', devicemotionHandler)
        } else {
          alert("Sorry your browser doesn't support Device Orientation")
        }
      } else {
        showWXErrorModal()
      }

      var startDate = formatDate(openingData.startTime)
      var endDate = formatDate(openingData.endTime)
      var curTime = new Date().getTime()

      if (curTime < openingData.startTime) {
        $('#expireModal').addClass('show')
        $('#expireTitle').text('活动尚未开始')
      } else if (curTime > openingData.endTime) {
        $('#expireModal').addClass('show')
        $('#expireTitle').text('活动已结束')
      }
    }

    function showWXErrorModal() {
      $('#refreshBtn').on('click', function() {
        window.location.href =
          mainHost +
          '/service/wechat/from?openingCode=' +
          openingCode +
          '&state=' +
          from
      })

      $('#errorModal').addClass('show')
    }

    function addOpeningEvent() {
      //Event Opening Page
      $('.container-modal .modal-close').on('click', function() {
        $('#prizeModal').removeClass('show')
        $('#thanksModal').removeClass('show')
        $('#errorModal').removeClass('show')
        $('#shareModal').removeClass('show')
        shakeFlag = false
      })
      $('.container-modal .modal-share-btn').on('click', function() {
        $('#shareModal').addClass('show')
        shakeFlag = true
      })
      $('.container-modal .modal-share-close').on('click', function() {
        $('#shareModal').removeClass('show')
        shakeFlag = false
      })
      $('#shakeBtn').on('click', function() {
        if (!shakeFlag) {
          callShakeFun()
        }
      })
      $('#bagBtn').on('click', function() {
        window.location.href = 'mybag.html'
      })
      $('#ruleBtn').on('click', function() {
        $('#ruleModal').addClass('show')
        shakeFlag = true
      })
      $('.container-modal-rule .rule-close').on('click', function() {
        $('#ruleModal').removeClass('show')
        shakeFlag = false
      })
    }

    function callShareFun() {
      var curTime = new Date().getTime()
      var parameterJSON = JSON.stringify({
        uid: uid,
        sharedOccurTime: curTime,
        event: {
          openingCode: openingCode
        }
      })
      doSharedEventsShare(parameterJSON)
    }

    function callShakeFun() {
      shakeFlag = true
      var haveChance = true
      // if (!isShared) {
      // 	if (shakeCount > 0) {
      // 		haveChance = false;
      // var confirmMsg = confirm("分享给朋友，可以再多摇1次奖哦！");
      // if (confirmMsg) {
      // 	$('#shareModal').addClass('show');
      // 	shakeFlag = true;
      // } else {
      // 	shakeFlag = false;
      // };
      // 	};
      // };

      if (haveChance) {
        var curTime = new Date().getTime()
        var parameterJSON = JSON.stringify({
          uid: uid,
          occurTime: curTime,
          event: {
            openingCode: openingCode
          }
        })
        doCouponsDraw(parameterJSON)
      }
    }

    function showPrizeModal(data) {
      shakeFlag = true
      if (!data) {
        $('#expireModal').addClass('show')
        shakeFlag = true
      } else {
        prizeData = data
        if (data.coupon.oid == 9999) {
          $('#thanksTitle').text('您没有中奖')
          $('#thanksModal').addClass('show')
          shakeFlag = true
        } else {
          $('#prizeModal').addClass('show')
          shakeFlag = true
          $('#prizeName').text(data.coupon.name + '！')
          $('#prizeImg').removeClass(
            'prize-img2 prize-img6 prize-img7 prize-img8 prize-img9'
          )
          $('#prizeImg').addClass('prize-img' + data.coupon.oid)

          if (isCoupon(data.coupon.oid)) {
            $('#prizeDesc').html('奖券已放入我的礼袋，请凭券到店内领取')
          } else {
            $('#prizeDesc').html('奖品已放入我的礼袋，请前往我的礼袋查看')
          }
        }
      }
    }

    function devicemotionHandler(event) {
      var acceleration = event.accelerationIncludingGravity
      var curTime = new Date().getTime()

      if (curTime - last_update > 100) {
        var diffTime = curTime - last_update
        last_update = curTime
        x = acceleration.x
        y = acceleration.y
        z = acceleration.z
        var speed =
          Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

        if (!shakeFlag) {
          if (speed > SHAKE_THRESHOLD) {
            callShakeFun()
          }
        }

        last_x = x
        last_y = y
        last_z = z
      }
    }

    //Interface Function
    function doWechatConfig(parameter) {
      //获取微信JS-SDK配置信息
      var encodeUrl = encodeURIComponent(window.location.href)
      var url = mainHost + '/service/wechat/jsconfig?url=' + encodeUrl
      $.get(url, parameter, function(json) {
        if (!json.success) {
          // alert(json.status);
          showWXErrorModal()
        } else {
          if (json.data) {
            getWXConfig(json.data)
          }
        }
      })
    }

    function doEventOpeningData(parameter) {
      //根据开业代码获得当前活动信息
      var url = mainHost + '/service/events/' + openingCode
      $.get(url, parameter, function(json) {
        if (!json.success) {
          // alert(json.status);
          showWXErrorModal()
        } else {
          if (json.data) {
            initOpeningData(json.data)
          }
        }
      })
    }

    function doCouponsDraw(parameter) {
      //抽奖
      var url = mainHost + '/service/coupons/draw'
      $.ajax({
        type: 'POST',
        url: url,
        data: parameter,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(json) {
          showPrizeModal(json.data)
          shakeCount++
        },
        error: function(json) {
          shakeFlag = false
          $('#thanksTitle').text('今天的摇奖次数已用完')
          $('#thanksModal').addClass('show')
        }
      })
    }

    function doSharedEventsShare(parameter) {
      //分享活动页面
      var url = mainHost + '/service/shared-events/share'
      $.ajax({
        type: 'POST',
        url: url,
        data: parameter,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(json) {
          $('#prizeModal').removeClass('show')
          $('#thanksModal').removeClass('show')
          $('#shareModal').removeClass('show')
          $('#errorModal').removeClass('show')
          shakeFlag = false
        },
        error: function(json) {
          showWXErrorModal()
        }
      })
    }

    //ErrorCode and Exception
    function showErrorMessage(error) {
      if (!error.errorCode) {
        //
      } else {
        switch (error.errorCode) {
          case '0x010203003':
            alert('奖券已失效或兑奖时间已过')
            break
          case '0x010203006':
            alert('今天的摇奖次数已用完')
            break
          case '0x010201001': //uid is required.
            // alert('请用微信打开，或刷新此页面');
            break
          default:
          // alert(error.developerMessage);
        }
      }
    }
  }

  /**
   * newlookMybag: Code for mybag page, show baglist, show coupons and qrcode, and use coupons.
   *
   * @author Bruce Wei
   * @version 1.18
   *
   * Date Created: 8.15.2017
   * Last Updated: 9.19.2017
   */
  $.fn.newlookMybag = function(options) {
    var settings = $.extend(
      {
        http: 'http://',
        https: 'https://',
        host: '',
        port: '',
        path: '/',
        project: ''
      },
      options
    )

    var http = settings.http
    var https = settings.https
    var host = settings.host
    var port = settings.port
    var path = settings.path == '/' ? '' : settings.path
    var project = settings.project

    var mainHost = https + host + path + project
    var openingCode
    var uid
    var drawList

    mainMybag()

    //Function
    function mainMybag() {
      //get openingCode
      openingCode = getSessionItem('openingCode')
      if (openingCode) {
        wxShareInit()
      }

      //get uid
      uid = getSessionItem('openid')
      // uid = 'oSwljw8HX0Yjn358cR86qeQdRRWs'
      if (uid) {
        doWechatConfig()

        addMybagEvent()
        resetBagPage()
      } else {
        $('#bagLoading').removeClass('show')
        $('#shakeModal').addClass('show')
      }
    }

    function wxShareInit() {
      wsReady()
    }

    function wsReady() {
      wx.ready(function() {
        wx.onMenuShareAppMessage({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          desc: '11月14日-11月23日NEW LOOK微信抽奖，快来解锁属于你的惊喜大奖！', // 分享描述
          link:
            'https://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'https://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          type: '', // 分享类型,music、video或link，不填默认为link
          dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
          success: function() {
            callShareFun()
          },
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })

        wx.onMenuShareTimeline({
          title: 'NEW LOOK摇一摇抽奖，快来开启你的专属大奖', // 分享标题
          link:
            'https://www.5hac.com/querer/service/wechat/from?openingCode=' +
            openingCode +
            '&state=share', // 分享链接
          imgUrl: 'https://www.5hac.com/src/images/20171111/share.jpg', // 分享图标
          success: function() {
            callShareFun()
          },
          cancel: function() {
            // 用户取消分享后执行的回调函数
          }
        })
      })
    }

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

    function isCoupon(oid) {
      //洗脸机 1, 唇膏 2, 50元 3, 5元 4, 1000元 6, 9折 7, 85折 8, 8折 9;
      return oid > 2
    }

    function addMybagEvent(argument) {
      //Event Mybag Page
      $('.bag-modal .modal-close').on('click', function() {
        $('#bagModal').removeClass('show')
      })
      $('#shakeModal .modal-close').on('click', function() {
        window.history.go(-1)
        $('#shakeModal').removeClass('show')
      })
      $('.bag-modal .modal-close2').on('click', function() {
        $('#confirmCover').removeClass('show')
      })

      $('#confirmBtn').on('click', function() {
        var oid = $(this).attr('data-id')
        if (isCoupon(oid)) {
          $('#confirmCover').addClass('show')
        } else {
          window.location.replace('contact.html')
        }
      })
      $('#destroyBtn').on('click', function() {
        var oid = $(this).attr('data-id')
        var curTime = new Date().getTime()
        var parameterJSON = JSON.stringify({
          oid: oid,
          uid: uid,
          usedOccurTime: curTime
        })

        doCouponsUse(parameterJSON)
      })
    }

    function getSessionItem(name) {
      var data = sessionStorage.getItem(name)
      return data
    }

    function resetBagPage() {
      $('#bagLoading').addClass('show')
      $('#shakeModal').removeClass('show')
      $('#bagList').removeClass('show')

      drawList = null
      doCouponsList()
    }

    function showBagList(data) {
      $('#bagLoading').removeClass('show')

      if (data.length <= 0) {
        $('#shakeModal').addClass('show')
      } else {
        drawList = data
        $('#bagList').empty()
        var convertHtml = ''
        for (var i = 0; i < drawList.length; i++) {
          if (drawList[i].coupon.oid != 9999) {
            var itemHtml = ''
            var iconStyle = drawList[i].coupon.oid > 2 ? 'coupon' : 'gift'
            var iconDisableStyle = drawList[i].used ? 'icon-disable' : ''
            var coverStyle = drawList[i].used ? 'show' : ''
            var btnText = drawList[i].used ? '已领取' : '点击查看'
            itemHtml +=
              '<div id="item' +
              drawList[i].oid +
              '" class="container-baglist-item">'
            itemHtml +=
              '<div class="item-icon"><div class="icon-left icon-' +
              iconStyle +
              ' ' +
              iconDisableStyle +
              '"></div></div>'
            itemHtml +=
              '<div class="item-box"><div class="item-disalbe-cover ' +
              coverStyle +
              '"></div><div class="item-content">'
            itemHtml +=
              '<div class="item-title">' + drawList[i].coupon.name + '</div>'
            itemHtml +=
              '<div class="item-img prize-img' +
              drawList[i].coupon.oid +
              '"></div>'
            itemHtml += '<div class="item-btn">' + btnText + '</div>'
            itemHtml += '</div></div></div>'

            convertHtml += itemHtml
          }
        }

        if (convertHtml == '') {
          $('#shakeModal').addClass('show')
        } else {
          $('#bagList').addClass('show')
          $('#bagList').html(convertHtml)
        }

        $('.container-baglist-item .item-btn').on('click', function() {
          $('#bagModal').addClass('show')
          var oid = $(this)
            .parent()
            .parent()
            .parent()
            .attr('id')
            .substring(4)
          showBagModal(oid)
        })
      }
    }

    function showBagModal(oid) {
      if (!oid) {
        //
      } else {
        var drawItem
        for (var i = 0; i < drawList.length; i++) {
          if (drawList[i].oid == oid) {
            drawItem = drawList[i]
          }
        }

        $('#bagName').text(drawItem.coupon.name + '！')
        $('#bagImg').removeClass(
          'prize-img2 prize-img6 prize-img7 prize-img8 prize-img9'
        )
        $('#bagImg').addClass('prize-img' + drawItem.coupon.oid)
        $('#destroyBtn').attr('data-id', oid)
        $('#confirmBtn').attr('data-id', drawItem.coupon.oid)
        sessionStorage.setItem('userCouponId', oid)

        if (isCoupon(drawItem.coupon.oid)) {
          $('#bagDesc').html('请于结账时到收银台出示使用<br />使用规则请参见活动细则')
          $('#confirmBtn').text('领奖后核销')
          $('#bagModalComment').text('*领到奖品后，由店员点击核销')
          showBarcode(drawItem.barcode)
        } else {
          $('#bagDesc').html('')
          $('#confirmBtn').text('立即领奖并填写联系方式')
          $('#bagModalComment').text('')
          hideBarcode()
        }
      }
    }

    function showBarcode(num) {
      // 去掉编号开头的字母
      var _num = num.substring(1)
      // 仅用数字生成条形码
      JsBarcode('#code128', _num, {
        format: 'code128',
        height: 40
      })
      // 条形码下显示完整编号
      $('#code128 text').text(num)
      $('.modal-desc').height(15)
      $('.modal-barcode').css({
        'margin-top': '-25px'
      })
      $('.modal-barcode').addClass('show')
      $('#barcodeSvg').addClass('show')
    }

    function hideBarcode() {
      $('.modal-desc').height(60)
      $('.modal-barcode').css({
        'margin-top': '0px'
      })
      $('#barcodeSvg').removeClass('show')
    }

    //Interface Function
    function doWechatConfig(parameter) {
      //获取微信JS-SDK配置信息
      var url =
        mainHost + '/service/wechat/jsconfig?url=' + window.location.href
      $.get(url, parameter, function(json) {
        if (!json.success) {
          // alert(json.status);
        } else {
          if (json.data) {
            getWXConfig(json.data)
          }
        }
      })
    }

    function doCouponsList(parameter) {
      //查询当前活动的用户抽到的奖品列表
      var url = mainHost + '/service/coupons/' + openingCode + '?uid=' + uid

      $.get(url, parameter, function(json) {
        if (!json.success) {
          // alert(json.status);
        } else {
          showBagList(json.data)
        }
      })
    }

    function doCouponsUse(parameter) {
      //使用奖品
      var url = mainHost + '/service/coupons/use'
      $.ajax({
        type: 'PUT',
        url: url,
        data: parameter,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(json) {
          $('#bagModal').removeClass('show')
          $('#confirmCover').removeClass('show')
          resetBagPage()
        },
        error: function(json) {
          // showErrorMessage(json.responseJSON.error);
        }
      })
    }
  }
})(jQuery)
