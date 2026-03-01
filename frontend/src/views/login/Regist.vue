<template>
  <a-card :bordered="false" hoverable style="margin-top: 130px">
    <div style="text-align: left;font-size: 14px;margin-bottom: 30px"><b>账号注册</b></div>
    <div class="user-layout-register">
      <a-form ref="formRegister" :autoFormCreate="(form)=>{this.form = form}" id="formRegister">
        <a-form-item
          fieldDecoratorId="name"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入用户姓名' }]}">
          <a-input type="text" v-model="name" placeholder="用户姓名"></a-input>
        </a-form-item>
        <a-form-item
          :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入邮箱地址' }]}">
          <a-input-search :disabled="emailFlag" placeholder="邮箱地址" :enter-button="enterText" @search="onCheck" />
        </a-form-item>
        <a-form-item
          fieldDecoratorId="verificationCode"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入验证码' }]}">
          <a-input-search :disabled="emailFlag" placeholder="验证码" enter-button="验证" @search="verificationCheck" />
        </a-form-item>
        <a-divider orientation="left"><span style="font-size: 12px">账户注册</span></a-divider>
        <a-form-item
          fieldDecoratorId="email"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入注册账号' },  { validator: this.handleUsernameCheck }], validateTrigger: ['change', 'blur']}">
          <a-input :disabled="!emailFlag" type="text" v-model="username" placeholder="账号"></a-input>
        </a-form-item>
        <a-popover placement="rightTop" trigger="click" :visible="state.passwordLevelChecked">
          <template slot="content">
            <div :style="{ width: '240px' }">
              <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
              <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor "/>
              <div style="margin-top: 10px;">
                <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
              </div>
            </div>
          </template>
          <a-form-item
            fieldDecoratorId="password"
            :fieldDecoratorOptions="{rules: [{ required: true, message: '至少6位密码'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}">
            <a-input :disabled="!emailFlag" v-model="password" type="password" @click="handlePasswordInputClick" autocomplete="false"
                     placeholder="至少6位密码"></a-input>
          </a-form-item>
        </a-popover>

        <a-form-item
          fieldDecoratorId="password2"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '至少6位密码' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}">

          <a-input :disabled="!emailFlag" type="password" autocomplete="false" placeholder="确认密码"></a-input>
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            htmlType="submit"
            class="register-button"
            :loading="registerBtn"
            @click.stop.prevent="handleSubmit"
            :disabled="registerBtn">立即注册
          </a-button>
          <a class="login" @click="returnLogin">使用已有账户登录</a>
        </a-form-item>

      </a-form>
    </div>
  </a-card>
</template>

<script>
const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Regist',
  components: {},
  data () {
    return {
      emailFlag: false,
      sendFlag: false,
      enterText: '发送',
      form: null,
      name: '',
      email: '',
      verificationCode: '',
      username: '',
      password: '',
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    onCheck (data) {
      this.isEmail(data)
    },
    verificationCheck (data) {
      if (data === '') {
        this.$message.warning('请填写验证码！')
        return false
      }
      if (!this.sendFlag) {
        this.$message.warning('请填写邮箱获取验证码！')
        return false
      }
      this.$get('/cos/staff-info/verification/check', {
        validateCode: data,
        email: this.email
      }).then((r) => {
        if (r.data.data) {
          this.emailFlag = true
          this.$message.success('验证成功！')
        } else {
          this.$message.error('验证失败！')
        }
      })
    },
    isEmail (data) {
      var reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/
      if (data === '') {
        this.$message.warning('请填写邮箱地址！')
        return false
      } else if (!reg.test(data)) {
        this.$message.warning('验证不通过！')
        return false
      } else {
        this.enterText = '发送中..'
        this.$get('/cos/staff-info/register/email', {
          email: data
        }).then((r) => {
          if (r.data.data) {
            this.email = data
            this.sendFlag = true
            this.$message.success('发送成功！')
          } else {
            this.$message.warning('该邮箱地址已存在！')
          }
          this.enterText = '发送'
        })
      }
    },
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    handlePasswordCheck (rule, value, callback) {
      let password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },

    handleUsernameCheck (rule, value, callback) {
      let username = this.username.trim()
      if (username.length) {
        if (username.length > 10) {
          callback(new Error('用户名不能超过10个字符'))
        } else if (username.length < 4) {
          callback(new Error('用户名不能少于4个字符'))
        } else {
          this.$get(`user/check/${username}`).then((r) => {
            if (r.data) {
              callback()
            } else {
              this.validateStatus = 'error'
              callback(new Error('抱歉，该用户名已存在'))
            }
          })
        }
      } else {
        callback()
      }
    },

    // handlePhoneCheck (rule, value, callback) {
    //   callback()
    // },

    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },

    handleSubmit () {
      if (this.name === '') {
        this.$message.error('请填写姓名！')
        return false
      }
      if (!this.emailFlag) {
        this.$message.error('请先通过邮箱验证！')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.$post('regist', {
            username: this.username,
            password: this.password,
            name: this.name,
            email: this.email
          }).then(() => {
            this.$message.success('注册成功')
            this.returnLogin()
          }).catch(() => {
            this.$message.error('抱歉，注册账号失败')
          })
        }
      })
    },
    // getCaptcha (e) {
    //   e.preventDefault()
    //   let that = this
    //
    //   this.form.validateFields(['mobile'], {force: true},
    //     (err, values) => {
    //       if (!err) {
    //         this.state.smsSendBtn = true
    //
    //         let interval = window.setInterval(() => {
    //           if (that.state.time-- <= 0) {
    //             that.state.time = 60
    //             that.state.smsSendBtn = false
    //             window.clearInterval(interval)
    //           }
    //         }, 1000)
    //       }
    //     }
    //   )
    // },
    returnLogin () {
      this.$emit('regist', 'Login')
    }
  }
}
</script>
<style lang="less">
.user-register {
  &.error {
    color: #ff0000;
  }
  &.warning {
    color: #ff7e05;
  }
  &.success {
    color: #52c41a;
  }
}
.user-layout-register {
  .ant-input-group-addon {
    &:first-child {
      background-color: #fff;
    }
  }
  & > h3 {
    font-size: 16px;
    margin-bottom: 20px;
  }
  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }
  .register-button {
    width: 50%;
  }
  .login {
    float: right;
    line-height: 40px;
  }
}
</style>
