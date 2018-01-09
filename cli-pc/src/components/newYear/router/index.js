const index = ()=>import( '@/components/newYear/vue/index')
const addAct = ()=>import( '@/components/newYear/vue/addAct')
const editAct = ()=>import( '@/components/newYear/vue/editAct')
const cancelOut = ()=>import( '@/components/newYear/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/newYear/vue/prizeRecord') 
export const newYear = [
    {
        path:'/newYear/index',
        component: index,
        name:'newYear_index'
    },
    {
        path:'/newYear/addAct',
        component: addAct,
        name:'newYear_addAct'
    },{
        path:'/newYear/editAct',
        component: editAct,
        name:'newYear_editAct'
    },
    {
        path:'/newYear/cancelOut',
        component: cancelOut,
        name:'newYear_cancelOut'
    },
    {
        path:'/newYear/prizeRecord',
        component: prizeRecord,
        name:'newYear_prizeRecord'
    }
]