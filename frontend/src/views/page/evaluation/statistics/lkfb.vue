<template>
    <div class="page">
        <div class="bg-white top">
            <div id='test02' style='width:80%; height:400px' ref='b'>
            </div>        
            <!--div style="width: 100%; height: 300px">
                <DaisyCRUD :dataProvider="dataProvider"/>
            </div-->
        </div>
    </div>
</template>

<script>
import { defineComponent, onMounted, reactive, ref, computed, watch } from "vue";
import * as echarts from 'echarts'

onMounted(
    ()=>{
        console.log("onMounted() called")
        echarts.init()
    }
)

export default defineComponent({
    name: "lkfb",
    setup() {
    },
    data () {
        console.log("data()");
    },
    computed: {
    },
    methods: {
        showorders() {
            // 基于准备好的dom，初始化echarts实例
            var chartDom = document.getElementById('test02');
            var myChart = echarts.init(chartDom);

            //let myChart = this.$echarts.init(this.$refs.b)
            // 指定图表的配置项和数据
            var option;
 
            option = {
                xAxis: {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                    data:[150, 230, 224, 218, 135, 147, 260],
                    type: 'bar'
                    }
                ]
            };
            option && myChart.setOption(option);
        },
    },
    beforeCreated() {
        console.log("beforeCreated()");
    },
    created() {
        console.log("created()");
    },
    mounted() {
        this.showorders()
    }
})
</script>
