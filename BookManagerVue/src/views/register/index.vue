<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <!-- 标题 -->
      <div class="title-container">
        <h3 class="title">图书管理系统-注册界面</h3>
      </div>
      <div align="center" >
            <!-- <h3 style="color:aquamarine;font-weight:400;">点击上方图片上传头像</h3> -->
            <!--上传图片-->
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8092/BookManager/update/updateImg"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="loginForm.avatarurl" :src="loginForm.avatarurl" class="avatar" alt="封面无法显示" style="width:100px;height:100px;">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <h4 style="color:aquamarine;font-weight:400;">点击上方图片上传头像</h4>
          </div>
      <!-- 用户名 -->
      <el-form-item prop="nickname">
        <span class="svg-container">
          <svg-icon icon-class="other" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.nickname"
          placeholder="请输入昵称"
          name="username"
          type="text"
          tabindex="4"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="请输入用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="email">
        <span class="svg-container">
          <svg-icon icon-class="link" />
        </span>
        <el-input
          ref="email"
          v-model="loginForm.email"
          placeholder="请输入邮箱"
          name="email"
          type="text"
          tabindex="5"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="phone">
        <span class="svg-container">
          <svg-icon icon-class="tree" />
        </span>
        <el-input
          ref="phone"
          v-model="loginForm.phone"
          placeholder="请输入手机号"
          name="phone"
          type="text"
          tabindex="5"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          ref="password"
          v-model="loginForm.password"
          type="password"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 确认密码 -->
      <el-form-item prop="repeat">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          ref="repeat"
          v-model="loginForm.repeat"
          type="password"
          placeholder="请确认密码"
          name="repeat"
          tabindex="3"
          auto-complete="on"
          @keyup.enter.native="handleRight"
        />
      </el-form-item>
      <!-- 权限 -->
      <!-- <el-form-item prop="authority">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-select v-model="loginForm.isadmin" placeholder="请选择" style="width: 418px">
          <el-option :key="1" label="管理员" :value="1"></el-option>
          <el-option :key="0" label="读者" :value="0"></el-option>
        </el-select>
      </el-form-item> -->

      <!-- 注册按钮 -->
      <div style="height: 40px; margin-bottom: 30px">
        <el-button :loading="loading" type="primary" style="width: 48%; float: left" @click.native.prevent="handleRight">确认</el-button>
        <el-button :loading="loading" type="success" style="width: 48%; float: right" @click.native.prevent="handleBack">返回登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { register } from '@/api/user'


export default {
  name: 'Login',
  data() {
    const validateRepeat = (rule, value, callback) => {
      if (value !== this.loginForm.password) {
        callback(new Error('两次输入的密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        nickname: '',
        username: '',
        email: '',
        phone: '',
        password: '',
        repeat: '',
        avatarurl: 'https://cdn.acwing.com/media/user/profile/photo/60364_lg_786b0c4c45.jpg?imageView2/1/w/80/h/80'
        // isadmin:0
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        repeat: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { trigger: 'blur', validator: validateRepeat }
        ]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  methods: {
    handleRight() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          register({ username: this.loginForm.username, password: this.loginForm.password, email: this.loginForm.email, phone: this.loginForm.phone, nickname: this.loginForm.nickname, avatarurl:this.loginForm.avatarurl}).then((res) => {
            // console.log(this.loginForm.avatarurl)
            if (res === 0) {
              this.$message.error('注册失败，用户名已存在')
            } 
            else if(res === -1){
              this.$message.error('注册失败，用户昵称已存在')
            }
            else if(res === -2){
              this.$message.error('注册失败，邮箱已存在')
            }
            else if(res === -3){
              this.$message.error('注册失败，手机号已存在')
            }
            else {
              this.$message.success('注册成功')
          this.loading = true
          this.$store.dispatch('user/logins', this.loginForm).then(() => {
            // console.log(message)
            this.$router.push({ path: '/' })  // 无脑进首页
            this.loading = false
          }).catch((message) => {
            console.log(message)
            this.$message.error(message)
            this.loading = false
          })
              // this.$router.push({ path: '/' })
              // setTimeout(()=>{

              // },1000)
            }
          })
        } else {
          console.log('不允许提交!')
          return false
        }
      })
    },
    handleBack() {
      this.$router.push('/login')
    },
    handleAvatarSuccess(res, file) {
      console.log(res)
      console.log(file)
      if(res.code === 0) {
        this.$message.success('上传成功');
        this.loginForm.avatarurl = res.data
        console.log(res.data)
      } else {
        this.$message.error('上传失败，请联系管理员');
      }
    },

    // 图片上传之前监听
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传封面图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传封面图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
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
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
  .el-icon-arrow-up:before {
    content: "";
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 100px 35px 0;
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
}
</style>
