const index       = ()=>import( '@/components/christmasGift/vue/index')
const prizeRecord = ()=>import( '@/components/christmasGift/vue/prizeRecord')
const addAct = ()=>import( '@/components/christmasGift/vue/addAct') 
export const christmasGift = [
    {
        path:'/christmasGift/index',
        component: index,
        name:'christmasGift_index'
    }, {
        path:'/christmasGift/prizeRecord',
        component: prizeRecord,
        name:'christmasGift_prizeRecord'
    }, {
        path:'/christmasGift/addAct',
        component: addAct,
        name:'christmasGift_addAct'
    }
]