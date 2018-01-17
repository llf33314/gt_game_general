const index = ()=>import( '@/components/luckSake/vue/index')
const prizeRecord = ()=>import( '@/components/luckSake/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/luckSake/vue/cancelOut')
const addAct = ()=>import( '@/components/luckSake/vue/addAct')
const editAct = ()=>import( '@/components/luckSake/vue/editAct')
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
    ,   {
        path:'/luckSake/editAct',
        component: editAct,
        name:'luckSake_editAct'
    },
    {
        path:'/luckSake/addAct',
        component: addAct,
        name:'luckSake_addAct'
    }
]