const index = () =>
  import ('@/components/bigTurnplate/vue/index')
const addAct = () =>
  import ('@/components/bigTurnplate/vue/addAct')
const prizeRecord = () =>
  import ('@/components/bigTurnplate/vue/prizeRecord')
const editAct = () =>
  import ('@/components/bigTurnplate/vue/editAct')

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
  {
    path: '/bigTurnplate/editAct',
    component: editAct,
    name: 'bigTurnplate_editAct'
  },
]
