# gt_game_general

> 游戏类
#### 二、目录结构
``` 
├─build
├─config
├─node_modules
├─src
│  ├─assets
│  ├─components
│  │  └─openBox          // 拆礼盒 
│  │  └─seaRich          // 大海捞金 
│  │  └─bigTurnplate     // 大转盘 
│  │  └─gragonBoat       // 端午赛龙舟
│  │  └─crazyMoney       // 疯狂数钱  
│  │  └─scrapeHappy      // 刮刮乐
│  │  └─luckTranslate    // 好运翻翻看 
│  │  └─robIngots        // 欢乐抢元宝 
│  │  └─romanceValentine // 浪漫七夕
│  │  └─redpachetFly     // 让红包飞
│  │  └─nationalFlag     // 升国旗
│  │  └─christmasGift    // 圣诞大礼包
│  │  └─luckyNine        // 幸运九宫格 
│  │  └─cashTree         // 摇钱树
│  │  └─luckSake         // 摇手气 
│  │  └─throughHeart     // 一箭穿心
│  │  └─standStill       // 一站到底
│  │  └─newYear          // 元旦跨年跳跃
│  │  └─lanternFestival  // 元宵点灯
│  │  └─eggSmash         // 砸金蛋
│  ├─http
│  │   └─index.js    // 统一初始化各个项目的axios请求方法
│  └─router
│  │   └─index.js    // 加载各个项目的路由
└─static   // 公共资源目录
│   ├─css
│   ├─imgs
│   └─js
│       └─lib    // 项目引入的插件
│         └─jquery-2.2.2.min.js
├─index.html  // 项目入口
│
│

```

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
