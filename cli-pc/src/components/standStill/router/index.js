
const index = ()=>import( '@/components/standStill/vue/index')
const addAct = ()=>import( '@/components/standStill/vue/addAct')
const cancelOut = ()=>import( '@/components/standStill/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/standStill/vue/prizeRecord') 
const user = ()=>import( '@/components/standStill/vue/user') 
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
    },
    {
        path:'/standStill/user',
        component: user,
        name:'standStill_user'
    }
]