<template>

    <div class="page">

        <div>

            <a-form
                layout="inline"
                :model="formState"
                name="form_xmbh"
                :label-col="{ span: 12 }"
                :wrapper-col="{ span: 16 }"
                autocomplete="off"
                @finish="onFinish"
                @finishFailed="onFinishFailed"
            >
                <a-form-item
                  label="项目编号"
                  name="form_item_xmbh"
                  :rules="[{ required: true, message: '请选择项目编号' }]"
                >
                    <a-space>
                        <a-select
                            name="select_xmbh"
                            ref="select"
                            v-model:value="value_xmbh"
                            style="width: 140px"
                            @focus="focus"
                            @change="handleChange"
                        >
                        <a-select-option v-for="d in searchOptData.project_id" :key="d" :value="d">{{d}}</a-select-option>
                        </a-select>
                    </a-space>
                </a-form-item>

                <a-form-item
                  label="管养单位"
                  name="form_item_gydw"
                  :rules="[{ required: true, message: '请选择管养单位' }]"
                >
                    <a-space>
                        <a-select
                            name="select_gydw"
                            ref="select"
                            v-model:value="value_gydw"
                            style="width: 140px"
                            @focus="focus"
                            @change="handleChange"
                        >
                        <a-select-option v-for="d in searchOptData.maintain" :key="d" :value="d">{{d}}</a-select-option>
                        </a-select>
                    </a-space>
                </a-form-item>

                <a-form-item
                  label="指标名称"
                  name="form_item_zbmc"
                  :rules="[{ required: true, message: '请选择指标名称' }]"
                >
                    <a-space>
                        <a-select
                            name="select_zbmc"
                            ref="select"
                            v-model:value="value_zbmc"
                            :options="list_zbmc"
                            style="width: 140px"
                            @focus="focus"
                            @change="handleChange"
                        >
                        <a-select-option v-for="d in metrics_dict" :key="d" :value="d">{{d}}</a-select-option>
                        </a-select>
                    </a-space>
                </a-form-item>

                <a-form-item
                  label="路线类型"
                  name="form_item_lxlx"
                  :rules="[{ required: true, message: '请选择路线类型' }]"
                >
                    <a-space>
                        <a-select
                            name="select_lxlx"
                            ref="select"
                            v-model:value="value_lxlx"
                            style="width: 140px"
                            @focus="focus"
                            @change="handleChange"
                        >
                        <a-select-option v-for="d in searchOptData.line_dir" :key="d" :value="d">{{d}}</a-select-option>

                        </a-select>
                    </a-space>
                </a-form-item>

            </a-form>
        </div>

        <div>
        </div>

        <div class="bg-white top">
            <div id='id_echarts_pdhz' style='width:100%; height:350px' ref='ref_echarts_pdhz'>
            </div>
            <!--div style="width: 100%; height: 300px">
                <DaisyCRUD :dataProvider="dataProvider"/>
            </div-->
            <!-- 表格区域 -->
            <div id='id_table_pdhz' style='width:100%; height:350px' ref='ref_table_pdhz'>
                <a-table
                    :data-source="pageDataDeal.records"
                    bordered
                    size="middle"
                    :scroll="{ x: 'calc(400px + 50%)', y: 240 }"
                >
                    <a-table-column key="district" data-index="district" title="政区名称"/>

                    <a-table-column-group>
                        <template #title><span>合计</span></template>
                        <a-table-column key="lengthTotal" data-index="lengthTotal" title="总里程"/>
                        <a-table-column key="average" data-index="average" title="平均值"/>
                    </a-table-column-group>

                    <a-table-column-group>
                        <template #title>优</template>
                        <a-table-column key="lengthExcellent" data-index="lengthExcellent" title="里程"/>
                        <a-table-column key="percentExcellent" data-index="percentExcellent" title="占比"/>
                    </a-table-column-group>

                    <a-table-column-group>
                        <template #title>良</template>
                        <a-table-column key="lengthGood" data-index="lengthGood" title="里程"/>
                        <a-table-column key="percentGood" data-index="percentGood" title="占比"/>
                    </a-table-column-group>

                    <a-table-column-group>
                        <template #title>中</template>
                        <a-table-column key="lengthGeneral" data-index="lengthGeneral" title="里程"/>
                        <a-table-column key="percentGeneral" data-index="percentGeneral" title="占比"/>
                    </a-table-column-group>

                    <a-table-column-group>
                        <template #title>次</template>
                        <a-table-column key="lengthFair" data-index="lengthFair" title="里程"/>
                        <a-table-column key="percentFair" data-index="percentFair" title="占比"/>
                    </a-table-column-group>

                    <a-table-column-group>
                        <template #title>差</template>
                        <a-table-column key="lengthBad" data-index="lengthBad" title="里程"/>
                        <a-table-column key="percentBad" data-index="percentBad" title="占比"/>
                    </a-table-column-group>
                </a-table>
            </div>

        </div>
    </div>
</template>

<script>
import * as ant_design_vue from 'ant-design-vue';
import SelectProps from 'ant-design-vue';
import { defineComponent, onMounted, reactive, ref, computed, watch } from "vue";
import * as echarts from 'echarts'

onMounted(
    ()=>{
        console.log("onMounted() called")
        echarts.init()
    }
)

export default defineComponent({
    name: "pdhz",
    setup() {
        console.log("setup()");
    },
    data () {
        console.log("data()");

        return {
            radio: 0,
            more_seen: false,
            show: false,
            queryForm: {},
            loading: false,
            projectId_selected: "20220413-002",
            metrics_dict: ["MQI", "PQI", "PCI", "RQI", "SRI", "PBI", "PWI", "PSSI"],
            grade_dict: ["优","良","中","次","差"],
            pageData: {
                code: null,
                data: {
                    currentPage: null,
                    recordList: [],
                    size: null,
                    totalPage: null
                },
                message: null,
                requestId: null,
                tag: null
            },
            pageDataDeal: {
                records: []
            },
            searchOptData: {
              project_id: [],
              project_name: [],
              maintain: [],
              line_dir: [
                "上行",
                "下行",
                "全幅",
                "A车道",
                "B车道",
                "C车道",
                "D车道",
                "E车道",
                "F车道",
                "G车道",
                "H车道",
                "I车道",
                "J车道",
                "K车道",
                "L车道",
              ],
              custom01: [], //metrics_name
            },
            value_xmbh: ref(''),
            value_gydw: ref(''),
            value_zbmc: ref(''),
            value_lxlx: ref(''),
        }
    },
    computed: {
        dataProvider() {
            console.log("dataProvider()");
            return () => this.$daisyCRUDDataProviderFactory.create('evaluation', 'pdjgmx', {
                event: {
                    afterInitializeRecord: (record) => this.afterInitializeRecord(record),
                },
                base: {
                    tableLoadCriteria: computed(() => this.tableLoadCriteria),
                }
            })
        },
        entityDataManager() {
            console.log("entityDataManager()");
            return this.$daisyEntityDataManagerFactory.create("evaluation", "pdjgmx")
        }
    },
    methods: {
        afterInitializeRecord(record) {
            console.log("afterInitializeRecord(record) called");
            return record
        },
        getSearchOpt() {
            this.searchOptData.xmbh.push("20220413-002");
            this.searchOptData.metrics_value.push("MQI");
            this.searchOptData.metrics_value.push("PQI");
            this.searchOptData.metrics_value.push("PCI");
            this.searchOptData.metrics_value.push("RQI");
            this.searchOptData.metrics_value.push("RDI");
            this.searchOptData.metrics_value.push("SRI");
            this.searchOptData.metrics_value.push("PBI");
            this.searchOptData.metrics_value.push("PWI");
            this.searchOptData.metrics_value.push("PSSI");
            this.searchOptData.line_dir.push("全幅");
            this.searchOptData.line_dir.push("上行");
            this.searchOptData.line_dir.push("下属");
        },
        refreshList() {
            console.log("refreshList()");
            this.entityDataManager.loadData({currentPage:1, pageSize:4000}, [{ "propertyName": "xmbh", 'opType': 'equals', "value": "20220413-002"}])
            .then((response) => {
                if (response.recordList.length > 0) {
                    this.pageData.data = response;
                    this.processScreenData();
                }
            });
            console.log(this.pageData.data.recordList.length)
        },
        processScreenData() {
            console.log("processScreenData() called");

            var chartDom = document.getElementById('id_echarts_pdhz');
            var myChart = echarts.init(chartDom);
            this.list_gydw = []

            for (var i = 0; i < this.pageData.data.recordList.length; i++) {
                if (this.searchOptData.maintain.indexOf(this.pageData.data.recordList[i]["gydwbh"]) == -1) {
                    this.searchOptData.maintain.push(this.pageData.data.recordList[i]["gydwbh"]);
                }
                if (this.searchOptData.project_id.indexOf(this.pageData.data.recordList[i]["xmbh"]) == -1) {
                    this.searchOptData.project_id.push(this.pageData.data.recordList[i]["xmbh"]);
                }
            }

            this.pageDataDeal.lineChartOptions = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data: this.grade_dict
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '差',
                        type: 'bar',
                        stack: 'vistors',
                        barWidth: '20%',
                        data: [],
                        itemStyle:{
                            normal:{
                                color:'#e84335'
                            }
                        }
                    },
                    {
                        name: '次',
                        type: 'bar',
                        stack: 'vistors',
                        barWidth: '20%',
                        data: [],
                        itemStyle:{
                            normal:{
                                color:'#fd8305'
                            }
                        }
                    },
                    {
                        name: '中',
                        type: 'bar',
                        stack: 'vistors',
                        barWidth: '20%',
                        data: [],
                        itemStyle:{
                            normal:{
                                color:'#faf13a'
                            }
                        }
                    },
                    {
                        name: '良',
                        type: 'bar',
                        stack: 'vistors',
                        barWidth: '20%',
                        data: [],
                        itemStyle:{
                            normal:{
                                color:'#3f99ed'
                            }
                        }
                    },
                    {
                        name: '优',
                        type: 'bar',
                        stack: 'vistors',
                        barWidth: '20%',
                        data: [],
                        itemStyle:{
                            normal:{
                                color:'#53c41b'
                            }
                        }
                    },
                ],

                dataZoom: {
                    show: true, // 为true 滚动条出现
                    realtime: true,
                    type:'slider', // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
                    height: 20, // 表示滚动条的高度，也就是粗细
                    start: 20, // 表示默认展示20%～80%这一段。
                    end: 80
                },
            };

            this.pageDataDeal.records = [];

            for (var i = 0; i < this.searchOptData.maintain.length; i ++) {
                this.pageDataDeal.lineChartOptions.xAxis.data.push(this.searchOptData.maintain[i]);
                var record = {
                    district: "",
                    lengthTotal: 0,
                    average: 0,
                    lengthExcellent: 0,
                    percentExcellent: 0,
                    lengthGood: 0,
                    percentGood: 0,
                    lengthGeneral: 0,
                    percentGeneral: 0,
                    lengthFair: 0,
                    percentFair: 0,
                    lengthBad: 0,
                    percentBad: 0,
                };
                record.district = this.searchOptData.maintain[i];
                record.lengthTotal = 0;
                record.average = 0;
                record.lengthExcellent = 0;
                record.percentExcellent = 0;
                record.lengthGood = 0;
                record.percentGood = 0;
                record.lengthGeneral = 0;
                record.percentGeneral = 0;
                record.lengthFair = 0;
                record.percentFair = 0;
                record.lengthBad = 0;
                record.percentBad = 0;
                this.pageDataDeal.records.push(record);
            }

            for (var i = 0; i < this.pageDataDeal.records.length; i++) {
                var average_cnt = 0;
                for (var j = 0; j < this.pageData.data.recordList.length; j++) {
                    if (true
                        && this.pageData.data.recordList[j]["xmbh"] == this.projectId_selected
                        /*&& this.pageData.data.records[j].maintain == this.searchOptData.maintain.selected*/
                        //&& this.pageData.data.recordList[j]["jcfx"] == this.searchOptData.line_dir.selected
                        ) {
                        if (true
                            && this.pageDataDeal.records[i].district == this.pageData.data.recordList[j]["gydwbh"]
                            ) {
                            average_cnt++;

                            var metrics_value = 0;
                            if (this.metrics_dict.selected == 'MQI') {
                                metrics_value = this.pageData.data.recordList[j]["mqi"];
                            }
                            //if (this.metrics_dict.selected == 'PQI') {
                                metrics_value = this.pageData.data.recordList[j]["pqi"];
                            //}
                            if (this.metrics_dict.selected == 'PCI') {
                                metrics_value = this.pageData.data.recordList[j]["pci"];
                            }
                            if (this.metrics_dict.selected == 'RQI') {
                                metrics_value = this.pageData.data.recordList[j]["rqi"];
                            }
                            if (this.metrics_dict.selected == 'RDI') {
                                metrics_value = this.pageData.data.recordList[j]["rdi"];
                            }
                            if (this.metrics_dict.selected == 'SRI') {
                                metrics_value = this.pageData.data.recordList[j]["rsi"];
                            }
                            if (this.metrics_dict.selected == 'PBI') {
                                metrics_value = this.pageData.data.recordList[j]["pbi"];
                            }
                            if (this.metrics_dict.selected == 'PWI') {
                                metrics_value = this.pageData.data.recordList[j]["pwi"];
                            }
                            if (this.metrics_dict.selected == 'PSSI') {
                                metrics_value = this.pageData.data.recordList[j]["pssi"];
                            }

                            var length = this.pageData.data.recordList[j]["ldlc"];
                            if (metrics_value < 20) {
                                this.pageDataDeal.records[i].lengthBad += length;
                            } else if (metrics_value < 40) {
                                this.pageDataDeal.records[i].lengthFair += length;
                            } else if (metrics_value < 60) {
                                this.pageDataDeal.records[i].lengthGeneral += length;
                            } else if (metrics_value < 80) {
                                this.pageDataDeal.records[i].lengthGood += length;
                            } else if (metrics_value < 100) {
                                this.pageDataDeal.records[i].lengthExcellent += length;
                            }
                            this.pageDataDeal.records[i].lengthTotal += length;
                            this.pageDataDeal.records[i].average += metrics_value;
                        }
                    }
                }

                this.pageDataDeal.records[i].percentExcellent = (this.pageDataDeal.records[i].lengthExcellent * 100 / this.pageDataDeal.records[i].lengthTotal).toFixed(2);
                this.pageDataDeal.records[i].percentGood = (this.pageDataDeal.records[i].lengthGood * 100 / this.pageDataDeal.records[i].lengthTotal).toFixed(2);
                this.pageDataDeal.records[i].percentGeneral = (this.pageDataDeal.records[i].lengthGeneral * 100 / this.pageDataDeal.records[i].lengthTotal).toFixed(2);
                this.pageDataDeal.records[i].percentFair = (this.pageDataDeal.records[i].lengthFair * 100 / this.pageDataDeal.records[i].lengthTotal).toFixed(2);
                this.pageDataDeal.records[i].percentBad = (this.pageDataDeal.records[i].lengthBad * 100 / this.pageDataDeal.records[i].lengthTotal).toFixed(2);

                this.pageDataDeal.records[i].average = (this.pageDataDeal.records[i].average / average_cnt).toFixed(2);

                this.pageDataDeal.lineChartOptions.series[4].data.push(this.pageDataDeal.records[i].percentExcellent);
                this.pageDataDeal.lineChartOptions.series[3].data.push(this.pageDataDeal.records[i].percentGood);
                this.pageDataDeal.lineChartOptions.series[2].data.push(this.pageDataDeal.records[i].percentGeneral);
                this.pageDataDeal.lineChartOptions.series[1].data.push(this.pageDataDeal.records[i].percentFair);
                this.pageDataDeal.lineChartOptions.series[0].data.push(this.pageDataDeal.records[i].percentBad);

            }
            myChart.setOption(this.pageDataDeal.lineChartOptions);
        }
    },
    beforeCreated() {
        console.log("beforeCreated()");
    },
    created() {
        console.log("created()");
    },
    mounted() {
        console.log("created()");
        this.refreshList();
    }
})
</script>

<style scoped>
  .more {
    font-size: 13px;
    color:darkgray;
    display: table-cell;
    width: 1400px;
    line-height: 30px;
  }

  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 2px;
  }
  .el-col.el-col-left {
    border-radius: 2px;
    text-align: right;
    vertical-align: middle;
  }
  .grid-content {
    border-radius: 2px;
    min-height: 20px;
  }
  .row-bg {
    padding: 2px 0;
    background-color: #f9fafc;
  }

  .selected-label {
    color: red;
  }

  .el-checkbox {
    margin-right: 5px;
  }

  .el-input {
    margin-bottom: 0px;
    margin-top: 0px;
  }

</style>
