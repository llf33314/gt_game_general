
#### 指定中奖人弹窗

```  

 <gt-Fans-detail :visible.sync="dialogFans" :peopleNums="peopleNums" v-on:getFansData="getFansData"></gt-Fans-detail>  


<script> 
export default { 
    data() {
        return {
            dialogFans:false,
            key:0,
            peopleNums:1,
        }
    },
    methods: {  
        getFansData(e){ 
            console.log(e,'子组件的信息') 
        },
    }
}
</script>
```