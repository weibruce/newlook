define(function(require) {
  var config, util, openingCode, uid, userCouponId, mainHost

  config = require('./config')
  util = require('./util')

  mainHost = config.http + config.host + config.path + config.project

  console.log('main host: ' + mainHost)

  //get openingCode
  openingCode = util.getSessionItem('openingCode')
  if (openingCode) {
    util.wxShareInit(openingCode)
  }

  //get uid
  var uid = util.getSessionItem('openid')
  if (uid) {
    util.doWechatConfig()
  }

  userCouponId = util.getSessionItem('userCouponId')

  function isEmpty(input) {
    if (!input || !input.trim()) {
      return true
    } else {
      return false
    }
  }

  var app = new Vue({
    el: '#app',
    data: {
      contact: {
        name: '',
        cellphone: '',
        address: ''
      },
      errors: {
        name: '',
        cellphone: '',
        address: ''
      }
    },
    methods: {
      // form validation
      validate: function() {
        if (isEmpty(this.contact.name)) {
          this.errors.name = '请输入姓名'
        } else {
          this.errors.name = ''
        }

        if (isEmpty(this.contact.cellphone)) {
          this.errors.cellphone = '请输入手机号'
        } else if (
          !/^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/.test(
            this.contact.cellphone
          )
        ) {
          this.errors.cellphone = '请输入正确的手机号'
        } else {
          this.errors.cellphone = ''
        }

        if (isEmpty(this.contact.address)) {
          this.errors.address = '请输入地址'
        } else {
          this.errors.address = ''
        }
      },
      handleSubmit: function() {
        this.validate()

        // validation fail
        if (this.errors.name || this.errors.cellphone || this.name) return

        var url = mainHost + '/service/shipping-addresses'
        var parameter = JSON.stringify({
          userCouponOid: userCouponId,
          uid: uid,
          name: this.contact.name,
          cellphone: this.contact.cellphone,
          address: this.contact.address
        })

        $.ajax({
          type: 'POST',
          url: url,
          data: parameter,
          contentType: 'application/json; charset=utf-8',
          dataType: 'json',
          success: function(json) {
            // return back to mybag page
            window.location.replace('mybag.html')
          },
          error: function(json) {
            // showErrorMessage(json.responseJSON.error);
          }
        })
      }
    },
    watch: {
      contact: {
        handler: function() {
          this.validate()
        },
        deep: true
      }
    }
  })
})
