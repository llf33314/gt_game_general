
const index = ()=>import( '@/components/standStill/vue/index')
const addAct = ()=>import( '@/components/standStill/vue/addAct')
const cancelOut = ()=>import( '@/components/standStill/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/standStill/vue/prizeRecord') 
export const standStill = [
    {
        path:'/standStill/index',
        component: index,
        name:'standStill_index'
    },
    {
        path:'/standStill/addAct',
        component: addAct,
        name:'standStill_addAct'
    },
    {
        path:'/standStill/cancelOut',
        component: cancelOut,
        name:'standStill_cancelOut'
    },
    {
        path:'/standStill/prizeRecord',
        component: prizeRecord,
        name:'standStill_prizeRecord'
    }
]