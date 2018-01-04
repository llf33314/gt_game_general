const index  = ()=>import( '@/components/redpachetFly/vue/index')
const addAct = ()=>import( '@/components/redpachetFly/vue/addAct')

export const redpachetFly = [
    {
        path:'/redpachetFly/index',
        component: index,
        name:'redpachetFly_index'
    },
    {
        path:'/redpachetFly/addAct',
        component: addAct,
        name:'redpachetFly_addAct'
    }
]