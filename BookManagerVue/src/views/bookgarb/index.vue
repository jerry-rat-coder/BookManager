<template>
    <div class="app-container">
      <!-- 顶部功能 -->
      <div class="filter-container" style="margin-bottom: 15px; opacity:0.6;">
        <!-- 书名输入 -->
        <el-input v-model="queryParam.bookname" placeholder="书名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
        <!-- 作者输入 -->
        <el-input v-model="queryParam.bookauthor" placeholder="作者" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
        <!-- 类型选择 -->
        <el-select v-model="queryParam.booktypeid" filterable placeholder="类型" clearable class="filter-item" style="width: 200px">
          <el-option v-for="item in typeData" :key="item.booktypeid" :label="item.booktypename" :value="item.booktypeid" />
        </el-select>
        <!-- 一些按钮 -->
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleShowAll">
          显示全部
        </el-button>
        <el-button  class="filter-item" style="margin-left: 10px;" type="danger" icon="el-icon-delete" @click="handleDeleteSome">
        批量删除
      </el-button>
      <el-button  class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-refresh" @click="handleRecovers">
        批量恢复
      </el-button>
      </div>
  

  
      <!--数据表格-->
      <el-table
          ref="multipleTable"
          :data="tableData"
          border
          style="width: 100%; opacity:0.7;">
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
            <el-button  @click="handleRecover(scope.row)" type="success" size="small">恢复图书</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!--分页条-->
      <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="queryParam.page"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="queryParam.limit"
          layout="total, sizes, prev, pager, next, jumper"
          :total="recordTotal"
          style="margin-top: 15px;opacity: 0.8;">
      </el-pagination>
    </div>
  </template>
  
  <script>
  import { mapGetters } from 'vuex'
  import permission from '@/directive/permission/index.js' // 权限判断指令
  import waves from '@/directive/waves' // waves directive
  import {
    getCount,
    queryBookInfosByPage,
    addBookInfo,
    deleteBookInfo,
    deleteBookInfos,
    recoverBookInfo,
    recoverBookInfos
  } from '@/api/bookgarb'
  import { queryBookTypes } from '@/api/booktype'
  import { borrowBook } from '@/api/borrow'
  import { queryUsers } from '@/api/user'
  import { addAdmin } from '@/api/admin'
  export default {
    name: 'Bookgarb',
    directives: { waves, permission },
    // 创建后
    created() {
      // 从服务器获取数据表格第一页的信息
      queryBookInfosByPage(this.queryParam).then(res => {
        console.log('首页数据获取成功',res)
        this.tableData = res.data
        this.recordTotal = res.count
      })
      // 从服务器获取所有的图书类型
      queryBookTypes().then(res => {
        console.log('图书类型获取成功',res)
        this.typeData = res
      })
    },
    mounted() {
    },
    methods: {
      // 分页大小改变监听
      handleSizeChange(curSize) {
        const params = this.queryParam
        params.limit = curSize
        queryBookInfosByPage(params).then(res => {
              console.log('分页数据获取成功',res)
              this.tableData = res.data
              this.recordTotal = res.count
        })
      },
  
      // 点击分页监听方法
      handleCurrentChange(curPage) {
        const params = this.queryParam
        params.page = curPage
        queryBookInfosByPage(params).then(res => {
              console.log('分页数据获取成功',res)
              this.tableData = res.data
              this.recordTotal = res.count
        })
      },
  
      // 搜索图书
      handleFilter() {
        this.queryParam.page = 1
        queryBookInfosByPage(this.queryParam).then(res => {
          if(res.code === 0) {
            this.tableData = res.data
            this.recordTotal = res.count
          }
        })
      },
  
      // 显示全部
      handleShowAll() {
        this.queryParam.page = 1
        this.queryParam.bookname = null
        this.queryParam.bookauthor = null
        this.queryParam.booktypeid = null
        queryBookInfosByPage(this.queryParam).then(res => {
          if(res.code === 0) {
            this.tableData = res.data
            this.recordTotal = res.count
          }
        })
      },
  
      // 删除记录
      handleDelete(row, index) {
        this.$confirm('确定要删除该条图书吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteBookInfo(row).then(res => {
            if(res === 1) {
              this.$message.success('删除图书成功')
              addAdmin(this.id,'删除书籍'+row.bookname,'操作成功')
              
              this.tableData.splice(index, 1)
              // 如果删完了，获取上一页
              if(this.tableData.length === 0) {
                this.queryParam.page = this.queryParam.page - 1
                this.handleCurrentChange(this.queryParam.page)
              }
            } else {
              this.$message.error('删除图书失败')
              addAdmin(this.id,'删除书籍','操作失败')
            }
          })
        })
      },
  
      // 删除一些
      handleDeleteSome() {
        this.$confirm('确定要删除这些图书吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 获取选中的对象数组
          const items = this.$refs.multipleTable.selection
          // const cont = items.length 还原用
          deleteBookInfos(items).then(res => {
            if(res > 0) {
              this.$message.success('删除' + res + '条记录成功')
              for(let i = 0; i < res; ++ i){
                addAdmin(this.id,'删除书籍'+items[i].bookname,'操作成功')
              }
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
      handleRecover(row, index) {
        this.$confirm('确定要恢复该条图书吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          recoverBookInfo(row).then(res => {
            if(res === 1) {
              this.$message.success('恢复图书成功')
              addAdmin(this.id,'恢复书籍'+row.bookname,'操作成功')
              
              this.tableData.splice(index, 1)
              // 如果删完了，获取上一页
              if(this.tableData.length === 0) {
                if(this.queryParam.page)this.queryParam.page = this.queryParam.page - 1
                this.handleCurrentChange(this.queryParam.page)
              }
            } else {
              this.$message.error('恢复记录失败')
              addAdmin(this.id,'恢复书籍','操作失败')
            }
          })
        })
      },
  
      // 删除一些
      handleRecovers() {
        this.$confirm('确定要恢复这些记录吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 获取选中的对象数组
          const items = this.$refs.multipleTable.selection
          // const cont = items.length 还原用
          recoverBookInfos(items).then(res => {
            if(res > 0) {
              this.$message.success('恢复' + res + '条记录成功')
              if(this.tableData.length === res) {  //如果本页内容全部删光了
                //当前页为上一页
                if(this.queryParam.page > 1) {
                  this.queryParam.page = this.queryParam.page - 1
                }
              }
              // 重载当前页
              this.handleCurrentChange(this.queryParam.page)
            } else {
              this.$message.error('恢复记录失败')
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
        // 图书类型数据
        typeData: [],
        // 用户数据
        userData: [],
        // 查询参数
        queryParam: {
          page: 1,
          limit: 10,
          bookname: null,
          bookauthor: null,
          booktypeid: null
        },
        // 对话框表单显示
        dialogFormVisible: false,
        dialogFormVisible2: false,
        // 表单类型（添加数据:0,修改数据:1）
        formType: 0,
        // 表单数据
        form: {
          bookid: null,
          bookname: '',
          bookauthor: '',
          bookprice: 0,
          booktypeid: 1,
          bookdesc: '',
          isborrowed: 0,
          bookimg: ''
        },
        form2: {
          userid: 1,
          bookid: 1
        },
        rules: {
          bookname: [
            { required: true, message: '请输入图书名称', trigger: 'blur' }
          ],
          bookauthor: [
            { required: true, message: '请输入作者', trigger: 'blur' }
          ],
          bookprice: [
            { required: true, message: '请输入价格', trigger: 'blur' }
          ],
          booktypeid: [
            { required: true, message: '请选择类型', trigger: 'blur' }
          ],
          bookdesc: [
            { required: true, message: '请输入描述', trigger: 'blur' }
          ],
          isborrowed: [
            { required: true, message: '请选择状态', trigger: 'blur' }
          ]
        },
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
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 150px;
      height: 200px;
      display: block;
    }
    .app-container {
    /* background: url(../../../public/0.jpg) no-repeat; */
    background-size: cover;
  }
  </style>