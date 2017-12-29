const index = ()=>import( '@/components/nationalFlag/vue/index')
const prizeRecord = ()=>import( '@/components/nationalFlag/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/nationalFlag/vue/cancelOut')
const addAct = ()=>import( '@/components/nationalFlag/vue/addAct')
export const nationalFlag = [
    {
        path:'/nationalFlag/index',
        component: index,
        name:'nationalFlag_index'
    }
    ,{
        path:'/nationalFlag/prizeRecord',
        component: prizeRecord,
        name:'nationalFlag_prizeRecord'
    }  ,
    {
        path:'/nationalFlag/cancelOut',
        component: cancelOut,
        name:'nationalFlag_cancelOut'
    }
    ,
    {
        path:'/nationalFlag/addAct',
        component: addAct,
        name:'nationalFlag_addAct'
    }
]