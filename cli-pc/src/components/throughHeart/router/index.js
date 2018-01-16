const index = ()=>import( '@/components/throughHeart/vue/index')
const prizeRecord = ()=>import( '@/components/throughHeart/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/throughHeart/vue/cancelOut')
const addAct = ()=>import( '@/components/throughHeart/vue/addAct')
const editAct = ()=>import( '@/components/throughHeart/vue/editAct')
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
        path:'/throughHeart/editAct',
        component: editAct,
        name:'throughHeart_editAct'
    } ,
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