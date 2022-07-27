<template>
    <div style="display: flex; height: 100%; width: 100%; flex-direction: row">
        <div style="overflow: hidden auto; padding: 16px; width: 200px; height: 100%; flex: 0 0 auto;">
            <a-tree ref="treeRef" v-model:expandedKeyList="expandedKeyList" v-model:selectedKeys="selectedKeyList"
                :load-data="onLoadData" :tree-data="treeData" :block-node="true" />
        </div>
        <div style="flex: 1 1 auto; height: 100%; position: relative">
            <DaisyCRUD :dataProvider="dataProvider" />
        </div>
    </div>
</template>

<script lang="ts">
import type { TreeProps } from 'ant-design-vue';
import { Tree } from 'ant-design-vue';
import { computed, defineComponent, ref, watch } from "vue";

export default defineComponent({
    name: "zzjg",
    components: {
        'ATree': Tree,
    },
    computed: {
        dataProvider(): any {
            return () => (<any>this).$daisyCRUDDataProviderFactory.create('basis', 'zzjg', {
                event: {
                    afterInitializeRecord: (record: any) => (<any>this).afterInitializeRecord(record),
                },
                base: {
                    criteria: computed(() => (<any>this).criteria),
                }
            });
        },
        dataProvider1(): any {
            return (<any>this).$daisyEntityDataManagerFactory.create("basis", "zzjg");
        },
        criteria(): any {
            if ((<any>this).selectedKeyList.length > 0) {
                return [{ "propertyName": "sjzzjgbh", 'opType': 'equals', "value": (<any>this).selectedKeyList[0] ? (<any>this).selectedKeyList[0] : null }]
            } else {
                return []
            }
        }
    },
    methods: {
        afterInitializeRecord(record: any): any {
            record.sjzzjgbh = (<any>this).selectedKeyList[0]
            return record
        },
        onLoadData(treeNode: any): any {
            return new Promise(resolve => {
                if (treeNode?.dataRef?.children) {
                    resolve(null);
                    return;
                }
                if (!treeNode) {
                    (<any>this).entityDataManager.loadData({ currentPage: 1, pageSize: 0 }, [{ "propertyName": "sjzzjgbh", 'opType': 'equals', "value": null }])
                        .then((response: any) => {
                            (<any>this).treeData = response.recordList.map((record: any) => {
                                return {
                                    title: record.jgmc,
                                    key: record.jgbh,
                                }
                            });
                            if (response.recordList.length > 0) {
                                (<any>this).selectedKeys = [response.recordList[0].jgbh]
                            }
                        });
                } else {
                    (<any>this).entityDataManager.loadData({ currentPage: 1, pageSize: 0 }, [{ "propertyName": "sjzzjgbh", 'opType': 'equals', "value": treeNode.dataRef.key ? treeNode.dataRef.key : null }])
                        .then((response: any) => {
                            treeNode.dataRef.children = response.recordList.map((record: any) => {
                                return {
                                    title: record.jgmc,
                                    key: record.jgbh,
                                }
                            });
                            (<any>this).treeData = [...(<any>this).treeData];
                            resolve(null);
                        });
                }
            });
        }
    },
    mounted() {
        watch(() => (<any>this).treeRef, (newValue) => {
            (<any>this).onLoadData(newValue.treeData[0]);
        });
        watch(() => (<any>this).selectedKeyList, (newValue, oldValue) => {
            if (newValue.length == 0 && oldValue.length > 0) {
                newValue[0] = oldValue[0];
            }
        });
    },
    setup() {
        const treeRef = ref([]);
        const expandedKeyList = ref<string[]>([]);
        const selectedKeyList = ref<string[]>(['']);
        const treeData = ref<TreeProps['treeData']>([{ 'title': '根', 'key': '' }]);

        return {
            treeRef,
            expandedKeyList,
            selectedKeyList,
            treeData,
        };
    },
});
</script>

<style lang="scss" scoped>
// @import "ant-design-vue/lib/style/default.css";
// @import "ant-design-vue/lib/tree/style/index.css";
</style>
