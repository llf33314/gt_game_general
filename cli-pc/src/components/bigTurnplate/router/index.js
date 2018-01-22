const index  = ()=>import( '@/components/bigTurnplate/vue/index')
const addAct = ()=>import( '@/components/bigTurnplate/vue/addAct')
const prizeRecord = ()=>import( '@/components/scrapeHappy/vue/prizeRecord') 

export const bigTurnplate = [
    {
        path:'/bigTurnplate/index',
        component: index,
        name:'bigTurnplate_index'
    },
    {
        path:'/bigTurnplate/addAct',
        component: addAct,
        name:'bigTurnplate_addAct'
    } ,
    {
        path:'/scrapeHappy/prizeRecord',
        component: prizeRecord,
        name:'scrapeHappy_prizeRecord'
    } ,
]