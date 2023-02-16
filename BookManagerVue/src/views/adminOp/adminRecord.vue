<template>
   <div class="app-container">
     <el-table
        ref="multipleTable"
        :data="tableData"
        border
        style="width: 85%;margin: auto;opacity:0.6;">
      <el-table-column
          fixed
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          fixed
          prop="logId"
          label="序号"
          width="50">
      </el-table-column>
      <el-table-column
          v-if="roleIsAdmin === true"
          prop="operatorName"
          label="管理员"
          width="150">
          <!-- <template slot-scope="scope">
            <el-image :src="scope.row.operator_id" style="width: 130px; height: 180px"></el-image>
          </template> -->
      </el-table-column>
      <el-table-column
          prop="operateContent"
          label="操作内容"
          width="200"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="note"
          label="备注"
          width="200"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="operateTimestr"
          label="操作时间"
          width="300">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <!-- <el-button v-permission="['admin']" @click="handleUpdate(scope.row)" type="primary" size="small">编辑</el-button> -->
          <el-button v-permission="['admin']" @click="handleDelete(scope.row,scope.$index)" type="danger" size="small">删除</el-button>
          <!-- <el-button v-permission="['reader']" @click="handleBorrow(scope.row)" type="success" size="small">借阅图书</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="queryParam.page"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="queryParam.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="recordTotal"
        style="margin-top: 15px;text-align: center;opacity:0.6;;">
    </el-pagination>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import permission from '@/directive/permission/index.js' // 权限判断指令
import waves from '@/directive/waves' // waves directive
import {
  getCount,
  queryAdmins,
  queryAdminsByPage,
  deleteAdmin
} from '@/api/admin'
// import { queryBookTypes } from '@/api/booktype'
// import { borrowBook } from '@/api/borrow'
// import { queryUsers } from '@/api/user'
import { queryUsers } from '@/api/user'
export default {
  name: 'adminOp',
  directives: { waves, permission },
  // 创建后
  created() {
    // 从服务器获取数据表格第一页的信息
    queryAdminsByPage(this.queryParam).then(res => {
      console.log('首页数据获取成功',res)
      this.tableData = res.data
      this.recordTotal = res.count
      // console.log(scope.row.operatorname)
    })
    queryAdmins().then(res => {
      console.log('全部数据获取成功',res)
      this.typeData = res
    })
  },
  mounted() {
    if (this.roleIsAdmin === false) {
      this.queryParam.limit = 5
      this.handleSizeChange(this.queryParam.limit)
    }
  },
  methods: {
    // 分页大小改变监听
    handleSizeChange(curSize) {
      const params = this.queryParam
      params.limit = curSize
      queryAdminsByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },

    // 点击分页监听方法
    handleCurrentChange(curPage) {
      const params = this.queryParam
      params.page = curPage
      queryAdminsByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },


    // 点击添加记录
    handleCreate() {
      // 从服务器获取所有的图书类型
      queryBookTypes().then(res => {
        console.log('图书类型获取成功',res)
        this.typeData = res
      })
      // 表单是添加状态
      this.formType = 0
      // 将空数据置入form
      this.form = {
        bookid: null,
        bookname: '红楼梦',
        bookauthor: '曹雪芹',
        bookprice: '35.5',
        booktypeid: 1,
        bookdesc: '为闺阁立传',
        isborrowed: 0,
        bookimg: 'http://wangpeng-imgsubmit.oss-cn-hangzhou.aliyuncs.com/img/202111131322401.jpg'
      }
      // 显示表单框
      this.dialogFormVisible = true
    },


    // 添加和更新的提交表单
    submitForm() {
      if (this.formType === 0) {  // 添加记录
        addBookInfo(this.form).then(res => {
          if(res === 1) {
            this.$message.success('添加记录成功')
            // 跳转到末尾
            getCount().then(res => {
              this.recordTotal = res
              this.queryParam.page = Math.ceil(this.recordTotal / this.queryParam.limit)
              this.handleCurrentChange(this.queryParam.page)
            })
          } else {
            this.$message.error('添加记录失败')
          }
          this.dialogFormVisible = false  // 关闭对话框
        })
      } else if(this.formType === 1) {  //更新记录
        updateBookInfo(this.form).then(res => {
          if(res === 1) {
            this.$message.success('更新记录成功')
            this.handleCurrentChange(this.queryParam.page)
          } else {
            this.$message.error('更新记录失败')
          }
          this.dialogFormVisible = false  // 关闭对话框
        })
      }
    },


    // 删除记录
    handleDelete(row, index) {
      this.$confirm('确定要删除该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // console.log(row)
        deleteAdmin(row).then(res => {
          // console.log(row)
          if(res === 1) {
            this.$message.success('删除记录成功')
            this.tableData.splice(index, 1)
            // 如果删完了，获取上一页 
            if(this.tableData.length === 0) {
              if(this.queryParam.page > 1)this.queryParam.page = this.queryParam.page - 1
              this.handleCurrentChange(this.queryParam.page)
            }
          } else {
            this.$message.error('删除记录失败')
          }
        })
      })
    },

    // 删除一些
    handleDeleteSome() {
      this.$confirm('确定要删除这些记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取选中的对象数组
        const items = this.$refs.multipleTable.selection
        deleteBookInfos(items).then(res => {
          if(res > 0) {
            this.$message.success('删除' + res + '条记录成功')
            if(this.tableData.length === res) {  //如果本页内容全部删光了
              //当前页为上一页
              if(this.queryParam.page > 1) {
                this.queryParam.page = this.queryParam.page - 1
              }
            }
            // 重载当前页
            this.handleCurrentChange(this.queryParam.page)
          } else {
            this.$message.error('删除记录失败')
          }
        })
      })
    },

  },
  data() {
    return {
      // 表格数据
      tableData: [],
      // 记录总数
      recordTotal: 0,
      // // 图书类型数据
      // typeData: [],
      // 用户数据
      userData: [],
      // 查询参数
      queryParam: {
        page: 1,
        limit:10 ,
      }
    }
  },
  computed: {
    // 获得user信息
    ...mapGetters(['id','name','roles']),
    // 通过表单类型计算表单标题
    formTitle() {
      return this.formType === 0 ? '添加记录' : '修改记录'
    },
    roleIsAdmin() {
      if(this.roles[0] === 'admin') return true
      else return false
    }
  }
}

</script>
<style scoped>

</style>