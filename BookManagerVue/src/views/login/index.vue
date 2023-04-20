<template>
  <div class="login-container bg-slideshow"  :style="{ 'background-image': 'url(' + currentImage + ')' }">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 标题 -->
      <div class="title-container">
        <h3 class="title">简易图书管理系统-登录界面</h3>
      </div>
      <!-- 用户名 -->
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          class="yuan"
          ref="username"
          v-model="loginForm.username"
          placeholder="请输入用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          class="yuan"
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <!-- 权限 -->
      <el-form-item prop="authority">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-select v-model="loginForm.isadmin" placeholder="请选择" style="width: 418px">
          <el-option :key="0" label="读者" :value="0"></el-option>
          <el-option :key="1" label="管理员" :value="1"></el-option>
          <!-- <el-option :key="0" label="读者" :value="0"></el-option> -->
        </el-select>
      </el-form-item>
      
      <!-- 登录按钮 -->
      <div style="height: 40px; margin-bottom: 30px;">
        <el-button :loading="loading" type="primary" style="width: 31%; float: left;" @click.native.prevent="handleLogin">登录</el-button>
        <el-button :loading="loading" type="warning" style="width: 31%; float: left;" @click.native.prevent="handleReset">重置</el-button>
        <el-button :loading="loading" type="success" style="width: 31%; float: right;" @click.native.prevent="handleRegister">注册</el-button>
      </div>

      <!-- 提示 -->
      <div>
        <div class="test">测试数据</div>
        <div class="tips">
          <span style="margin-right:20px;">管理员 username: admin</span>
          <span> password: admin</span>
        </div>
        <div class="tips">
          <span style="margin-right:20px;">读者 username: yys</span>
          <span> password: 12345</span>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
var curpage=0
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      callback()
    }
    const validatePassword = (rule, value, callback) => {
      callback()
    }
    return {
      images: ['0.jpg','5.jpg','6.jpg','22.jpg','21.jpg','20.jpg','19.jpg','18.jpg','17.jpg','16.jpg','15.jpg','14.jpg','13.jpg','12.jpg','11.jpg'],
      currentIndex: 0,
      loginForm: {
        username: 'yys',
        password: '12345',
        isadmin: 0
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  computed: {
    currentImage: function() {
      return this.images[this.currentIndex]
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            // console.log(message)
            this.$router.push({ path: '/' })  // 无脑进首页
            this.loading = false
          }).catch((message) => {
            console.log(message)
            this.$message.error(message)
            this.loading = false
          })
        } else {
          console.log('不允许提交!')
          return false
        }
      })
    },
    handleRegister() {
      console.log("注册按钮")
      this.$router.push({ path: '/register' })  // 进注册页面
    },
    handleReset(){
      this.loginForm = {
        username:'yys',
        password:'12345',
        isadmin:0
        }
        this.$refs.loginForm.resetFields();
      },
      handleSet(){
        curpage=(curpage + 1) % 4
        
      }
    },
    mounted() {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.images.length
    }, 2500)
  }
  }
</script>
<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;


@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}
/* reset element-ui css */
.login-container {
  .el-input.yuan {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
    // input:focus {
    //   box-shadow: 0 0 1px 1px #1459ae;
    // }
  }

  .el-input {
    display: inline-block;
    height: 47px;
    width: 100%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
    
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.6);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  // background-image: url(../../../public/0.jpg),url(../../../public/1.jpg),url(../../../public/2.png);
  background-size: cover;
  background-repeat: no-repeat;
  // animation: bg-animation 3s linear infinite;
  transition: all .7s;
  @keyframes bg-animation {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .left,
  .right {
    opacity: 0.4;
    position: absolute;
    width: 75px;
    height: 75px;
    line-height: 75px;
    text-align: center;
    vertical-align: middle;
    background-color: #A6A6A6;
    border-radius: 50%;
    font-size:46px !important;
  }
  .left {
    left:1em;
    top:50%;
  }
  .right {
    right:1em;
    top:50%;
  }
  .left:hover,
  .right:hover {
    opacity: 0.6;
  }
  .test {
    height: 50px;
    line-height: 50px;
    font-size: 25px;
    color:greenyellow;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
}
</style>
