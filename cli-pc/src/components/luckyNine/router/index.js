 

const index = ()=>import( '@/components/luckyNine/vue/index')
const prizeRecord = ()=>import( '@/components/luckyNine/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/luckyNine/vue/cancelOut')
const addAct = ()=>import( '@/components/luckyNine/vue/addAct')
const editAct = ()=>import( '@/components/luckyNine/vue/editAct')
export const luckyNine = [
    {
        path:'/luckyNine/index',
        component: index,
        name:'luckyNine_index'
    }
    ,{
        path:'/luckyNine/prizeRecord',
        component: prizeRecord,
        name:'luckyNine_prizeRecord'
    }  ,
    {
        path:'/luckyNine/cancelOut',
        component: cancelOut,
        name:'luckyNine_cancelOut'
    }
    ,
    {
        path:'/luckyNine/addAct',
        component: addAct,
        name:'luckyNine_addAct'
    },
    {
        path:'/luckyNine/editAct',
        component: editAct,
        name:'luckyNine_editAct'
    }
]