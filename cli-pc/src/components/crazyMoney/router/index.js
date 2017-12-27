const index = ()=>import( '@/components/crazyMoney/vue/index')
const prizeRecord = ()=>import( '@/components/crazyMoney/vue/prizeRecord')

export const crazyMoney = [
    {
        path:'/crazyMoney/index',
        component: index,
        name:'crazyMoney_index'
    },
    {
        path:'/crazyMoney/prizeRecord',
        component: prizeRecord,
        name:'crazyMoney_prizeRecord'
    }
]