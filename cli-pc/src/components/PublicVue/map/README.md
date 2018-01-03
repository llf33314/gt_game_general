
#### 地图组件的使用

```
<template>
    <div>
         <dfMap :mapInformation.sync="mapInformation"></dfMap>
    </div>
</template>

<script>
import dfMap from "@/components/PublicVue/map/dfMap.vue";
export default {
    components: {dfMap},
    data() {
        return {
            // 地图的详细信息
            mapInformation: {
                address: "",                // 当前地址
                position: {lng: '', lat: ''}, //经纬度 
                // 地址逆解析对象
                regeocode: {
                    addressComponent: {
                        province: '',      // 省
                        provinceId: '',    
                        city: '',          // 市
                        cityId: '',
                        district: '',      // 区
                        districtId: ''
                    }
                }
            }
        }
    }
}
</script>
```