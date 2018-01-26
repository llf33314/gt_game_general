const index = ()=>import( '@/components/crazyMoney/vue/index')
const prizeRecord = ()=>import( '@/components/crazyMoney/vue/prizeRecord')

const addAct = ()=>import( '@/components/crazyMoney/vue/addAct')
const editAct = ()=>import( '@/components/crazyMoney/vue/editAct')
const cancelOut = ()=>import( '@/components/crazyMoney/vue/cancelOut')

export const crazyMoney = [
    {
        path:'/crazyMoney/index',
        component: index,
        name:'crazyMoney_index'
    },
    {
        path:'/crazyMoney/addAct',
        component: addAct,
        name:'crazyMoney_addAct'
    },
    {
        path:'/crazyMoney/editAct',
        component: editAct,
        name:'crazyMoney_editAct'
    },
    {
        path:'/crazyMoney/cancelOut',
        component: cancelOut,
        name:'crazyMoney_cancelOut'
    },
    {
        path:'/crazyMoney/prizeRecord',
        component: prizeRecord,
        name:'crazyMoney_prizeRecord'
    },

]