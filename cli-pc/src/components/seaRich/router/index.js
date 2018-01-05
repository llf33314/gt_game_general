
const index = ()=>import( '@/components/seaRich/vue/index')
const addAct = ()=>import( '@/components/seaRich/vue/addAct')
const editAct = ()=>import( '@/components/seaRich/vue/editAct')
const cancelOut = ()=>import( '@/components/seaRich/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/seaRich/vue/prizeRecord') 
export const seaRich = [
    {
        path:'/seaRich/index',
        component: index,
        name:'seaRich_index'
    },
    {
        path:'/seaRich/addAct',
        component: addAct,
        name:'seaRich_addAct'
    },    {
        path:'/seaRich/editAct',
        component: editAct,
        name:'seaRich_editAct'
    },
    {
        path:'/seaRich/cancelOut',
        component: cancelOut,
        name:'seaRich_cancelOut'
    },
    {
        path:'/seaRich/prizeRecord',
        component: prizeRecord,
        name:'seaRich_prizeRecord'
    }
]