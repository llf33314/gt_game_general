const index = ()=>import( '@/components/robIngots/vue/index')
const prizeRecord = ()=>import( '@/components/robIngots/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/robIngots/vue/cancelOut')
const addAct = ()=>import( '@/components/robIngots/vue/addAct')
const editAct = ()=>import( '@/components/robIngots/vue/editAct')
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
    },   {
        path:'/robIngots/editAct',
        component: editAct,
        name:'robIngots_editAct'
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