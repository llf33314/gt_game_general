const index = ()=>import( '@/components/throughHeart/vue/index')
const prizeRecord = ()=>import( '@/components/throughHeart/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/throughHeart/vue/cancelOut')
const addAct = ()=>import( '@/components/throughHeart/vue/addAct')
export const throughHeart = [
    {
        path:'/throughHeart/index',
        component: index,
        name:'throughHeart_index'
    }
    ,{
        path:'/throughHeart/prizeRecord',
        component: prizeRecord,
        name:'throughHeart_prizeRecord'
    }  ,
    {
        path:'/throughHeart/cancelOut',
        component: cancelOut,
        name:'throughHeart_cancelOut'
    }
    ,
    {
        path:'/throughHeart/addAct',
        component: addAct,
        name:'throughHeart_addAct'
    }
]