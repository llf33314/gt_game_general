
const index = ()=>import( '@/components/standStill/vue/index')
const addAct = ()=>import( '@/components/standStill/vue/addAct')
const cancelOut = ()=>import( '@/components/standStill/vue/cancelOut')
const prizeRecord = ()=>import( '@/components/standStill/vue/prizeRecord') 
const user = ()=>import( '@/components/standStill/vue/user') 
const question = ()=>import( '@/components/standStill/vue/question') 
const addQuest = ()=>import( '@/components/standStill/vue/addQuest') 
const editQuest = ()=>import( '@/components/standStill/vue/editQuest')  
const editAct = ()=>import( '@/components/standStill/vue/editAct')
export const standStill = [
    {
        path:'/standStill/index',
        component: index,
        name:'standStill_index'
    },
    {
        path:'/standStill/editQuest',
        component: editQuest,
        name:'standStill_editQuest'
    },
    {
        path:'/standStill/addQuest',
        component: addQuest,
        name:'standStill_addQuest'
    },
    {
        path:'/standStill/addAct',
        component: addAct,
        name:'standStill_addAct'
    },
    {
        path:'/standStill/editAct',
        component: editAct,
        name:'standStill_editAct'
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
    },
    {
        path:'/standStill/question',
        component: question,
        name:'standStill_question'
    }
]