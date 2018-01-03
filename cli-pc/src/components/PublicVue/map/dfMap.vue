<template>
    <div class="demoMap">
        <el-input class="inputAddress"  v-model="mapInformation.address" placeholder="请点击选择地址" @focus="openDialog"></el-input> <i class="iconfont gt-icon_map" @click="openDialog"></i>
        <el-dialog title="选择地址" :visible.sync="dialogVisible" top="10%" :close-on-click-modal="false" class="largeDialog">
            <div class="map">
                <el-input placeholder="搜索地址" icon="search" v-model="input" @change="searchKeyword"  @keyup.enter.native="searchKeyword" :on-icon-click="searchKeyword"></el-input>
                <span class="textAddress">当前定位的地址：<span class="c333 fs18 textAddress ell">{{MapData.address}}</span></span>
                <div id="container" class="map" tabindex="0"></div>
                <div id="panel"></div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="confirmFuc">确 定</el-button>
                <el-button @click="dialogVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>


<script>
export default {
    props: {
        mapInformation: {
            type: Object
        }
    },
    data() {
        return {
            input: "", //弹框的输入框
            dialogVisible: false, //弹框
            // 当前定位具体信息
            MapData: {},
            // 地图相关变量
            map: '',
            autocomplete: '',
            placeSearch: ''
        }
    },
    methods: {
        // 打开弹窗加根据省份初始化地图
        openDialog() {
            let _this = this
            _this.dialogVisible = true;

            _this.$nextTick(function () {

                _this.map = new AMap.Map('container', {
                    resizeEnable: true,
                    zoom: 16       // 缩放级别范围
                })

                _this.map.plugin(["AMap.ToolBar"], function () {
                    _this.map.addControl(new AMap.ToolBar());
                });
                // if(location.href.indexOf('&guide=1') !== -1) {
                //     _this.map.setStatus({scrollWheel: false})
                // }
                AMap.plugin(['AMap.Scale'],
                    function () {
                        _this.map.addControl(new AMap.Scale());
                    });

                AMap.plugin(['AMap.PlaceSearch'], function () {
                    // 开发指南-获取地图数据-搜索-周边搜索
                    _this.placeSearch = new AMap.PlaceSearch({
                        pageSize: 3,
                        pageIndex: 1,
                        city: '010', // 城市
                        map: _this.map,
                        panel: "panel"
                    })
                    // 获取地图中心点所在区域，回调函数返回对象属性分别对应为{省，市，区/县}
                    _this.map.getCity(function (result) {
                        // 根据初始定位中心省市县  作为关键字搜索
                        _this.placeSearch.search(result.province + result.city + result.district)
                    })
                })
                AMapUI.loadUI(['misc/PositionPicker', 'control/BasicControl'], function (PositionPicker, BasicControl) {
                    //缩放控件
                    _this.map.addControl(new BasicControl.Zoom({
                        position: 'lt',   //left top，左上角
                    }));

                    let positionPickerObj = new PositionPicker({
                        mode: 'dragMap',  //设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                        map: _this.map    //依赖地图对象
                    });
                    // 获取定位成功事件
                    positionPickerObj.on('success', function (positionResult) {
                        if(positionResult.info == 'OK') {
                            // console.log('定位具体位置', positionResult.address)
                            _this.MapData = positionResult
                        }
                    });
                    // 获取定位失败事件
                    positionPickerObj.on('fail', function (positionResult) {
                        this.$message.error('获取定位失败')
                        // console.log('定位失败', positionResult)
                        // 海上或海外无法获得地址信息
                    });

                    positionPickerObj.start();
                });
            })
        },
        //根据输入框值搜索地图
        searchKeyword() {
            let _this = this
            _this.placeSearch.search(_this.input)
        },
        confirmFuc() {
            // this.currentAddress = this.MapData.address
            this.dialogVisible = false

             let obj = {
                 province: this.MapData.regeocode.addressComponent.province,
                 city: this.MapData.regeocode.addressComponent.city,
                 district: this.MapData.regeocode.addressComponent.district
             }
             
            let addressObj = this.$util.MapAddressCode(obj)

            this.MapData.regeocode.addressComponent.provinceId = addressObj.provinceId
            this.MapData.regeocode.addressComponent.cityId = addressObj.cityId
            this.MapData.regeocode.addressComponent.districtId = addressObj.districtId
            this.$emit('update:mapInformation', this.MapData)
        }
    }
}
</script>

<style lang="less">
#panel {
  background-color: white;
  overflow-y: auto;
  margin-top: 10px;
}
// 下拉列表样式
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
.demoMap {
  display: inline-block;

  .inputAddress {
      width: 400px;
  }
  .map {
    width: 100%;
  }
  #container {
    height: 350px;
  }
  .el-dialog {
    width: 900px;
  }
  .textAdderess {
    display: inline-block;
    width: 440px;
  }
  .gt-icon_map {
      font-size: 30px;
      color: #1296db;
      vertical-align: middle;
      cursor: pointer;
  }
}
</style>