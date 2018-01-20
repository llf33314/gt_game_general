
const index = ()=>import( '@/components/luckTranslate/vue/index')
const prizeRecord = ()=>import( '@/components/luckTranslate/vue/prizeRecord') 
const addAct = ()=>import( '@/components/luckTranslate/vue/addAct')
export const luckTranslate = [
    {
        path:'/luckTranslate/index',
        component: index,
        name:'luckTranslate_index'
    },
    {
        path:'/luckTranslate/addAct',
        component: addAct,
        name:'luckTranslate_addAct'
    } ,
    {
        path:'/luckTranslate/prizeRecord',
        component: prizeRecord,
        name:'luckTranslate_prizeRecord'
    }
]