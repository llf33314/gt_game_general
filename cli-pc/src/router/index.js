import Vue from 'vue'
import Router from 'vue-router' 
// 加载演示项目路由
import { bigTurnplate }  from '@/components/bigTurnplate/router/index.js'
import { cashTree }      from '@/components/cashTree/router/index.js'
import { christmasGift } from '@/components/christmasGift/router/index.js'
import { crazyMoney }    from '@/components/crazyMoney/router/index.js'
import { eggSmash }      from '@/components/eggSmash/router/index.js'
import { gragonBoat }    from '@/components/gragonBoat/router/index.js'
import { lanternFestival}from '@/components/lanternFestival/router/index.js'
import { luckSake }      from '@/components/luckSake/router/index.js'
import { luckTranslate } from '@/components/luckTranslate/router/index.js'
import { luckyNine }     from '@/components/luckyNine/router/index.js'
import { nationalFlag }  from '@/components/nationalFlag/router/index.js'
import { newYear }       from '@/components/newYear/router/index.js'
import { openBox }       from '@/components/openBox/router/index.js'
import { redpachetFly }  from '@/components/redpachetFly/router/index.js'
import { robIngots }     from '@/components/robIngots/router/index.js'
import {romanceValentine}from '@/components/romanceValentine/router/index.js'
import { scrapeHappy }   from '@/components/scrapeHappy/router/index.js'
import { seaRich }       from '@/components/seaRich/router/index.js'
import { standStill }    from '@/components/standStill/router/index.js'
import { throughHeart }  from '@/components/throughHeart/router/index.js'
Vue.use(Router)
var routes = [
  {
      path: '/',
      redirect: '/lanternFestival/index',
  },
  ...bigTurnplate,
  ...cashTree,
  ...christmasGift,
  ...crazyMoney,
  ...eggSmash,
  ...gragonBoat,
  ...lanternFestival,
  ...luckSake,
  ...luckTranslate,
  ...luckyNine,
  ...nationalFlag,
  ...newYear,
  ...openBox,
  ...redpachetFly,
  ...robIngots,
  ...romanceValentine, 
  ...scrapeHappy,
  ...seaRich,
  ...standStill,
  ...throughHeart
]
const router = new Router({ 
  routes
})
export default router