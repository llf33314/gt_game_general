const index = ()=>import( '@/components/romanceValentine/vue/index')
const prizeRecord = ()=>import( '@/components/romanceValentine/vue/prizeRecord') 
const cancelOut = ()=>import( '@/components/romanceValentine/vue/cancelOut')
const addAct = ()=>import( '@/components/romanceValentine/vue/addAct')
export const romanceValentine = [
    {
        path:'/romanceValentine/index',
        component: index,
        name:'romanceValentine_index'
    }
    ,{
        path:'/romanceValentine/prizeRecord',
        component: prizeRecord,
        name:'romanceValentine_prizeRecord'
    }  ,
    {
        path:'/romanceValentine/cancelOut',
        component: cancelOut,
        name:'romanceValentine_cancelOut'
    }
    ,
    {
        path:'/romanceValentine/addAct',
        component: addAct,
        name:'romanceValentine_addAct'
    }
]