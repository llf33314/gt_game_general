const index = ()=>import( '@/components/gragonBoat/vue/index')
const prizeRecord = ()=>import( '@/components/gragonBoat/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/gragonBoat/vue/cancelOut')
const addAct = ()=>import( '@/components/gragonBoat/vue/addAct')
export const gragonBoat = [
    {
        path:'/gragonBoat/index',
        component: index,
        name:'gragonBoat_index'
    }
    ,{
        path:'/gragonBoat/prizeRecord',
        component: prizeRecord,
        name:'gragonBoat_prizeRecord'
    }  ,
    {
        path:'/crazyMoney/cancelOut',
        component: cancelOut,
        name:'crazyMoney_cancelOut'
    }
    ,
    {
        path:'/crazyMoney/addAct',
        component: addAct,
        name:'crazyMoney_addAct'
    }
]