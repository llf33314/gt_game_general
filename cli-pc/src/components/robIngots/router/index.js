const index = ()=>import( '@/components/robIngots/vue/index')
const prizeRecord = ()=>import( '@/components/robIngots/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/robIngots/vue/cancelOut')
const addAct = ()=>import( '@/components/robIngots/vue/addAct')
export const robIngots = [
    {
        path:'/robIngots/index',
        component: index,
        name:'robIngots_index'
    }
    ,{
        path:'/robIngots/prizeRecord',
        component: prizeRecord,
        name:'robIngots_prizeRecord'
    }  ,
    {
        path:'/robIngots/cancelOut',
        component: cancelOut,
        name:'robIngots_cancelOut'
    }
    ,
    {
        path:'/robIngots/addAct',
        component: addAct,
        name:'robIngots_addAct'
    }
]