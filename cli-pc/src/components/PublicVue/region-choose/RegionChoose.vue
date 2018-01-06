<template>
    <div>
        <el-cascader :props="objprops" :options="options" v-model="selectedOptions" @change="changeRegionOptions" placeholder="请选择省市区">
        </el-cascader>
    </div>
</template>
  
  <script>
import { Region } from "../../../assets/js/region.js";
export default {
  data() {
    return {
      options: Region,
      selecte: [],
      objprops: {
        value: "id",
        label: "cityName",
        children: "ticketCityRes"
      }
    };
  },
  props: {
    selectedOptions: {
      type: Array,
      default: []
    }
  },
  created() {
    this.selecte = this.selectedOptions;
  },
  methods: {
    changeRegionOptions(evt) {
      const r0 = this.selectedOptions[0];
      const r1 = this.selectedOptions[1];
      const r2 = this.selectedOptions[2];
      var mm = [];
      Region.forEach(function(item) {
        if (item.id == r0) {
          mm.push(item.cityName);
          if (item.ticketCityRes) {
            item.ticketCityRes.forEach(function(child) {
              if (child.id == r1) {
                mm.push(child.cityName);
              }
              if (child.ticketCityRes) {
                child.ticketCityRes.forEach(function(child2) {
                  if (child2.id == r2) {
                    mm.push(child2.cityName);
                  }
                });
              }
            });
          }
        }
      });
      this.$emit("change", {
        value: evt,
        name: mm
      });
    }
  }
};
</script>
  