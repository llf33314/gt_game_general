const index = ()=>import( '@/components/eggSmash/vue/index')
const prizeRecord = ()=>import( '@/components/eggSmash/vue/prizeRecord') 
const addAct = ()=>import( '@/components/eggSmash/vue/addAct')
const cancelOut = ()=>import( '@/components/eggSmash/vue/cancelOut')
const editAct = () => import('@/components/eggSmash/vue/editAct')

export const eggSmash = [
    {
        path:'/eggSmash/index',
        component: index,
        name:'eggSmash_index'
    },
    {
        path:'/eggSmash/addAct',
        component: addAct,
        name:'eggSmash_addAct'
    } ,
    {
        path:'/eggSmash/editAct',
        component: editAct,
        name:'eggSmash_editAct'
    } ,
    {
        path:'/eggSmash/prizeRecord',
        component: prizeRecord,
        name:'eggSmash_prizeRecord'
    } ,
    {
        path:'/eggSmash/cancelOut',
        component: cancelOut,
        name:'eggSmash_cancelOut'
    }
]