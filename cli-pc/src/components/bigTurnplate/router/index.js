const index = () =>
  import ('@/components/bigTurnplate/vue/index')
const addAct = () =>
  import ('@/components/bigTurnplate/vue/addAct')
const prizeRecord = () =>
  import ('@/components/bigTurnplate/vue/prizeRecord')

export const bigTurnplate = [{
    path: '/bigTurnplate/index',
    component: index,
    name: 'bigTurnplate_index'
  },
  {
    path: '/bigTurnplate/addAct',
    component: addAct,
    name: 'bigTurnplate_addAct'
  },
  {
    path: '/bigTurnplate/prizeRecord',
    component: prizeRecord,
    name: 'bigTurnplate_prizeRecord'
  },
]
