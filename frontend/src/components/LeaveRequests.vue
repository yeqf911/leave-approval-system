<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix"></div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="date" label="日期" width="180"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="type" label="类型" width="180"> </el-table-column>
      <el-table-column prop="reason" label="理由"> </el-table-column>
    </el-table>
  </el-card>
</template>

<script lang="ts">
export default {
  data() {
    return {
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          type: "事假",
          reason: "家中有事"
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          type: "事假",
          reason: "家中有事"
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          type: "病假",
          reason: "生病住院"
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          type: "事假",
          reason: "家中有事"
        }
      ]
    };
  }
};
</script>

<style lang="less" scoped></style>
