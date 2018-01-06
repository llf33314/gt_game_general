const index = ()=>import( '@/components/openBox/vue/index')
const addAct = ()=>import( '@/components/openBox/vue/addAct')
const cancelOut = ()=>import( '@/components/openBox/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/openBox/vue/prizeRecord') 
export const openBox = [
    {
        path:'/openBox/index',
        component: index,
        name:'openBox_index'
    },
    {
        path:'/openBox/addAct',
        component: addAct,
        name:'openBox_addAct'
    },
    {
        path:'/openBox/cancelOut',
        component: cancelOut,
        name:'openBox_cancelOut'
    },
    {
        path:'/openBox/prizeRecord',
        component: prizeRecord,
        name:'openBox_prizeRecord'
    }
]