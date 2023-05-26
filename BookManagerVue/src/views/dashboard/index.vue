<template>
  <div class="dashboard-container">
    <div title="欢迎页面" style="text-align: center;;padding: 50px; overflow: hidden; color: black;font: lighter 16px 'Lucida Sans Unicode';"
         data-options="iconCls:'icon-heart',plain:true">
         <!-- <div class="recommend">
          
         </div> -->
        <b style="font-size: 36px; line-height: 30px; height: 30px;opacity: 0.6;">欢迎使用简易至上图书管理系统</b>
        <p>项目名：简易到不能再简易的图书管理系统</p>
        <!-- <p>开发周期: 2021/11/8 —— 2021/11/13</p> -->
        <hr/>
        <div id="pies" style="width: 600px; height: 400px; margin: 0 !important;" > </div>
        <div class="qushi">
          <h1>各季度借阅量</h1>
          <img src="../../../public/趋势.png" alt="趋势图">
        </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import * as echarts from 'echarts'
import { ech , zhu } from '@/api/admin' 
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'id',
      'name',
      'roles'
    ])
  },
  methods: {
    createChart(){
      var option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };
    
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    
    // this.request.get("/echarts/members").then(res => {
      zhu().then( res => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data
      // 数据准备完毕之后再set
      myChart.setOption(option);

    })
    },
    createCharts(){
      var pieOption = {
  title: {
    text: '各类型图书数量统计',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      type: 'pie',
      radius: '60%',
      label:{            //饼图图形上的文本标签
        normal:{
          show:true,
          position:'inner', //标签的位置
          textStyle : {
            fontWeight : 300 ,
            fontSize : 14,    //文字的字体大小
            color: "#fff"
          },
          formatter:'{d}%'
        }
      },
      data: [],  // 填空
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
          color: 'skyblue'
        }
      }
    }
  ]
};
let echarts = require('echarts');
let pieDom = document.getElementById('pies')
// console.log(pieDom)
let pieChart = echarts.init(pieDom)

// this.request.get("/echarts/example").then(res => {
  ech().then( res => {
  // console.log(res)
    pieOption.series[0].data = [
        {name: "计算机科学", value: res.data[0]},
        {name: "历史", value: res.data[1]},
        {name: "文学", value: res.data[2]},
        {name: "科幻", value: res.data[3]},
        {name: "小说", value: res.data[4]},
        {name: "外语", value: res.data[5]},
    ]
    pieChart.setOption(pieOption)
})
    }
  },
  mounted() {
    this.createCharts()

  }
}
</script>
<style scoped>
.dashboard-container {
  background: rgba(255, 255, 255, 0.585);
  /* background: url(../../../public/0.jpg) no-repeat; */
  background-size: cover;
}
.recommend {
  left:250px;
  top:100px;
  position:fixed;
  width: 300px;
  height: 600px;
  border: 1px solid pink;
}
@-webkit-keyframes shining {
  from {
    background-position: -500%;
  }
  to {
    background-position: 500%;
  }
}

@keyframes shining {
  from {
    background-position: -500%;
  }
  to {
    background-position: 500%;
  }
}
.h1 {
  background: linear-gradient(90deg, black 0%, white 50%, black 100%);
  background-clip: text;
  -webkit-animation: shining 3s linear infinite;
  animation: shining 7s linear infinite;
  background-size: 80%;
  color:black; 
}
a {
  color: skyblue!important;
}
.qushi {
  opacity: 0.75;
  position: absolute;
  z-index: 1000;
  right:232px;
  top:33%;
}
.qushi img {
  width: 400px;
  height: 300px;

}
</style>
