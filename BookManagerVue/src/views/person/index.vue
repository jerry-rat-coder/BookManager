<template>
  
     <el-card style="opacity: 0.7;width: 500px; background: none;border: none; margin-left: 30%;margin-top: 13%;">
      <el-table
          ref="multipleTable"
          :data="tableData"
          border
          style="width: 100%; opacity:0.7;"
          hidden>
        <el-table-column
            fixed
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            fixed
            prop="bookid"
            label="序号"
            width="100">
        </el-table-column>
        <el-table-column
            v-if="roleIsAdmin === false"
            label="图书封面"
            width="155">
            <template slot-scope="scope">
              <el-image :src="scope.row.bookimg" style="width: 130px; height: 180px"></el-image>
            </template>
        </el-table-column>
        <el-table-column
            prop="bookname"
            label="图书名称"
            width="150"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            prop="bookauthor"
            label="图书作者"
            width="100"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            prop="bookprice"
            label="图书价格"
            width="100">
        </el-table-column>
        <el-table-column
            prop="booktypename"
            label="图书类型名"
            width="100"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            prop="bookdesc"
            label="图书描述"
            min-width="300"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            :width="roleIsAdmin?'240px':'110px'">
          <template slot-scope="scope">
            <!-- <el-button v-permission="['admin']" @click="handleUpdate(scope.row)" type="primary" size="small">编辑</el-button> -->
            <el-button v-permission="['admin']" @click="handleDelete(scope.row,scope.$index)" type="danger" size="small">彻底删除</el-button>
            <!-- <el-button  @click="handleBorrow(scope.row)" type="success" size="small">借阅图书</el-button> -->
          </template>
        </el-table-column>
      </el-table>
      <el-form-item label="用户名" prop="userid" hidden>
        <el-input v-model="form.userid" autocomplete="off"></el-input>
      </el-form-item>
    <el-form :model="form"  label-width="80px" size="small">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="电话 " prop="phone">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      
      <el-form-item label="违规次数 " prop="countvio"  >
        <el-input style="width:150px; padding-right:100px;" v-model="form.countvio" autocomplete="off" :disabled="true"></el-input>
        <div class="fr" style="width:200px;">用户状态：
          <span v-if="form.countvio >= 3" style="color:crimson;">封禁</span>
          <span v-else style="color:chartreuse;">正常</span>
        </div>
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" @click="submitForm" style="margin-left:150px">确 定 修 改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import {updateUser} from '@/api/user'
import { queryU }from '@/api/user'
export default {
  created() {
    queryU(this.id).then(res => {
      // console.log('asascnvbnmmnbhjascacascsacqdwqeqdw')
      // console.log(res)
      this.form.username = res.username
        this.form.email = res.email
        this.form.phone = res.phone
        this.form.nickname = res.nickname
        this.form.countvio = res.countVio
        this.form.userid = res.userid
    })
    // this.form.username = this.name
    //     this.form.email = this.email
    //     this.form.phone = this.phone
    //     this.form.nickname = this.nickname
    //     this.form.countvio = this.countvio
    //     this.form.userid = this.id
        // console.log(this.id)
        // console.log(this.form.)
  },
  methods: {
      submitForm(){
        updateUser(this.form).then(res => {
          if(res === 1) {
            this.$message.success('更新信息成功！')
          } else {
            this.$message.error('更新记录失败')
          }
        })
      },
      handleAvatarSuccess(res, file) {
      console.log(res)
      console.log(file)
      if(res.code === 0) {
        this.$message.success('上传成功');
        this.form.avatarUrl = res.data
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
    },
    data() {
        return {
        form: {
        userid:this.id,
        username: this.name,
        email: this.email,
        phone: this.phone,
        avatarUrl: this.avatar,
        nickname: this.nickname,
        countvio:this.countvio,
          },
           // 表格数据
      tableData: [],
      // 记录总数
      recordTotal: 0,
      // 查询参数
      queryParam: {
        page: 1,
        limit: 10,
        username: null
      },
        }
    },
    computed: {
      ...mapGetters(['id','name','roles','phone','email','nickname','avatar','countvio'])
    },
}
</script>
<style >
.el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
  margin-bottom: 30px !important;
}
.el-form-item--small .el-form-item__content, .el-form-item--small .el-form-item__label {
  line-height: 32px;
  color:aquamarine;
}
</style>
<style scoped>
.fr {
  position: absolute !important;
  right:-78px;
  top:0;
  font-weight: 700;
}
</style>