const index = ()=>import( '@/components/scrapeHappy/vue/index')
const prizeRecord = ()=>import( '@/components/scrapeHappy/vue/prizeRecord') 
const addAct = ()=>import( '@/components/scrapeHappy/vue/addAct')
const cancelOut = ()=>import('@/components/scrapeHappy/vue/cancelOut')
const editAct = () => import('@/components/scrapeHappy/vue/editAct')

export const scrapeHappy = [
    {
        path:'/scrapeHappy/index',
        component: index,
        name:'scrapeHappy_index'
    },
    {
        path:'/scrapeHappy/addAct',
        component: addAct,
        name:'scrapeHappy_addAct'
    } ,
    {
        path:'/scrapeHappy/editAct',
        component: editAct,
        name:'scrapeHappy_editAct'
    } ,
    {
        path:'/scrapeHappy/prizeRecord',
        component: prizeRecord,
        name:'scrapeHappy_prizeRecord'
    } ,
    {
        path:'/scrapeHappy/cancelOut',
        component: cancelOut,
        name:'scrapeHappy_cancelOut'
    }
]