const index = ()=>import( '@/components/cashTree/vue/index')
const addAct = ()=>import( '@/components/cashTree/vue/addAct')
const cancelOut = ()=>import( '@/components/cashTree/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/cashTree/vue/prizeRecord') 
export const cashTree = [
    {
        path:'/cashTree/index',
        component: index,
        name:'cashTree_index'
    },
    {
        path:'/cashTree/addAct',
        component: addAct,
        name:'cashTree_addAct'
    },
    {
        path:'/cashTree/cancelOut',
        component: cancelOut,
        name:'cashTree_cancelOut'
    },
    {
        path:'/cashTree/prizeRecord',
        component: prizeRecord,
        name:'cashTree_prizeRecord'
    }
]