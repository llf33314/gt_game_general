const index  = ()=>import( '@/components/bigTurnplate/vue/index')
const addAct = ()=>import( '@/components/bigTurnplate/vue/addAct')

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
    }
]