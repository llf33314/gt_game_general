<template>
    <div>
        <p><span class="w20_demo">中奖人</span><b> : </b> {{ data.nickname }}</p> 
        <p><span class="w20_demo">兑奖人</span><b> : </b> {{ data.memberName }}</p> 
        <p><span class="w20_demo">兑奖人联系方式</span><b> : </b> {{ data.memberPhone }}</p> 
        <p v-if="data.status==1"><span class="w20_demo">兑奖时间</span><b> : </b> {{ $util.DateFormat(data.cashTime, 'yyyy-MM-dd hh:mm:ss') }}</p> 
        <p v-if="data.status!=1"><span class="w20_demo">领取方式</span><b> : </b> {{ data.receiveType | drawingMethod }}</p> 
        <p v-if="data.receiveType==1"><span class="w20_demo">到店领取地址</span><b> : </b> {{ data.addressName }}</p> 
        <p v-if="data.receiveType==2"><span class="w20_demo">收货人姓名</span><b> : </b> {{ data.addressName }}</p> 
        <p v-if="data.receiveType==2"><span class="w20_demo">收货地址</span><b> : </b> {{ data.address }}</p>
    </div>
</template>

<script>
    export default {
        props: {
            data: Object,
            defalut: function() {
                return {}
            }
        },
         methods: {
             
         }
    }
</script>

<style scoped>

</style>