const index = ()=>import( '@/components/luckSake/vue/index')
const prizeRecord = ()=>import( '@/components/luckSake/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/luckSake/vue/cancelOut')
const addAct = ()=>import( '@/components/luckSake/vue/addAct')
export const luckSake = [
    {
        path:'/luckSake/index',
        component: index,
        name:'luckSake_index'
    }
    ,{
        path:'/luckSake/prizeRecord',
        component: prizeRecord,
        name:'luckSake_prizeRecord'
    }  ,
    {
        path:'/luckSake/cancelOut',
        component: cancelOut,
        name:'luckSake_cancelOut'
    }
    ,
    {
        path:'/luckSake/addAct',
        component: addAct,
        name:'luckSake_addAct'
    }
]