const index = ()=>import( '@/components/lanternFestival/vue/index')
const prizeRecord = ()=>import( '@/components/lanternFestival/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/lanternFestival/vue/cancelOut')
const addAct = ()=>import( '@/components/lanternFestival/vue/addAct')
export const lanternFestival = [
    {
        path:'/lanternFestival/index',
        component: index,
        name:'lanternFestival_index'
    }
    ,{
        path:'/lanternFestival/prizeRecord',
        component: prizeRecord,
        name:'lanternFestival_prizeRecord'
    }  ,
    {
        path:'/lanternFestival/cancelOut',
        component: cancelOut,
        name:'lanternFestival_cancelOut'
    }
    ,
    {
        path:'/lanternFestival/addAct',
        component: addAct,
        name:'lanternFestival_addAct'
    }
]