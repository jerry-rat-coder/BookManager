<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />
    <div class="tit">
      简易の图书管理系统
    </div>
    <div class="music">
      <audio class="baaaefbb-audios" src="../../../public/music/天元镇.mp3" autoplay loop></audio>
     
    </div>
    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        
        <div class="avatar-wrapper">
          <el-upload
          class="avatar-uploader"
          style="display:inline-block;"
                action="http://localhost:8092/BookManager/update/updateImg"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"> 
              <img v-if="userImg" v-bind:src="currentImage+'?imageView2/1/w/80/h/80'" class="avatar user-avatar" alt="封面无法显示">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          <!-- <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar"> -->
          <span class="user-name"> {{nickname}} </span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <router-link   to="/person/person">
            <el-dropdown-item>个人信息</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
 document.querySelector('.baaaefbb-audios').volume =0.01;
</script>
<script>
import { mapGetters } from 'vuex'
import { getInfo } from '@/api/user'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import {updateImg} from '@/api/user'
import { queryU }from '@/api/user'
import store from '../../store'
import { mapActions } from 'vuex'
export default {
  created() {

   this.userImg = this.avatar
  },
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'name',
      'nickname',
      'id',
      'token'
    ]),
    currentImage: function() {
      return this.userImg
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      this.$router.push(`/login`)
    },
    handleAvatarSuccess(res, file) {
      console.log(res)
      console.log(file)
      if(res.code === 0) {
        this.$message.success('上传成功');
        updateImg(res.data, this.id).then(res => {
          queryU(this.id).then(res => {
          console.log(this.userImg)
          console.log(res)
          this.userImg = res.avatarurl
          console.log(this.userImg)
        })
        })
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
  },
  data() {
    return {
      userImg: '',
           // 表格数据
      tableData: [],
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .user-name {
          cursor: pointer;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
<style scoped>
.tit {
  position: absolute;
  right:44%;
  color:#409eff;
  /* text-align: center; */
  font-size:26px;
  height: 100%;
  line-height: 50px;
  animation: change 3s linear 0s infinite alternate;
}
@keyframes change {	
		0% {
			color: #338feb
		}
		30%{
			color:#58a3ee
		}
		50% {
			color: #90c2f4
		}	
		80% {
			color: #93f0ac
		}	
		100% {color: #55e77c;}
  }	
  .music {
    float: left;
    width: 200px;
    height: 200px;
    /* display: none; */
  }
</style>
