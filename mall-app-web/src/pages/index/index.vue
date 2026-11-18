<template>
  <view class="container">
    <!-- 小程序头部兼容 -->
    <!-- #ifdef MP -->
    <view class="mp-search-box">
      <text class="yticon icon-saomiao nav-icon" @click="handleScan"></text>
      <input class="ser-input" type="text" value="输入关键字搜索" disabled @click="handleSearch" />
      <text class="yticon icon-icon-- nav-icon" @click="handleNavToNotice"></text>
    </view>
    <!-- #endif -->

    <!-- 顶部搜索（H5/App） -->
    <!-- #ifndef MP -->
    <view class="search-bar">
      <view class="search-box" @click="handleSearch">
        <text class="yticon icon-sousuo search-icon"></text>
        <text class="search-placeholder">搜索商品，如：手机</text>
      </view>
    </view>
    <!-- #endif -->

    <!-- 头部轮播 -->
    <view class="carousel-section">
      <!-- 标题栏和状态栏占位符 -->
      <view class="titleNview-placing"></view>
      <!-- 背景色区域 -->
      <view class="titleNview-background" :style="{ backgroundColor: titleNViewBackground }"></view>
      <swiper class="carousel" circular @change="handleSwiperChange">
        <swiper-item
          v-for="(item, index) in advertiseList"
          :key="index"
          class="carousel-item"
          @click="handleNavToAdvertisePage(item)"
        >
          <image :src="item.pic" mode="aspectFill" />
        </swiper-item>
      </swiper>
      <!-- 自定义swiper指示器 -->
      <view class="swiper-dots">
        <text class="num">{{ swiperCurrent + 1 }}</text>
        <text class="sign">/</text>
        <text class="num">{{ swiperLength }}</text>
      </view>
    </view>

    <!-- 头部功能区 -->
    <view class="cate-section">
      <view class="cate-item" @click="handleNavToTopicPage">
        <image src="/static/temp/c3.png"></image>
        <text>专题</text>
      </view>
      <view class="cate-item" @click="handleNavToTopicPage">
        <image src="/static/temp/c5.png"></image>
        <text>话题</text>
      </view>
      <view class="cate-item" @click="handleNavToRecommendBrandPage">
        <image src="/static/temp/c6.png"></image>
        <text>优选</text>
      </view>
      <view class="cate-item" @click="handleNavToHotProductListPage">
        <image src="/static/temp/c7.png"></image>
        <text>特惠</text>
      </view>
    </view>

    <!-- 品牌制造商直供 -->
    <view class="section-card">
      <view class="f-header" @click="handleNavToRecommendBrandPage">
        <view class="tit-box">
          <text class="tit">品牌制造商直供</text>
          <text class="tit2">工厂直达消费者，剔除品牌溢价</text>
        </view>
        <text class="more-btn">查看更多</text>
      </view>
      <view class="guess-section">
        <view
          v-for="(item, index) in brandList"
          :key="index"
          class="guess-item"
          @click="handleNavToBrandDetailPage(item)"
        >
          <view class="image-wrapper-brand">
            <image :src="item.logo" mode="aspectFit"></image>
          </view>
          <text class="title clamp">{{ item.name }}</text>
          <text class="title2">商品数量：{{ item.productCount }}</text>
        </view>
      </view>
    </view>

    <!-- 秒杀专区 -->
    <view class="section-card" v-if="homeFlashPromotion">
      <view class="f-header">
        <view class="tit-box">
          <text class="tit">秒杀专区</text>
          <text class="tit2">下一场 {{ formatTime(homeFlashPromotion?.nextStartTime) }} 开始</text>
        </view>
        <view class="countdown">
          <text class="countdown-label">本场结束剩余</text>
          <view class="countdown-time">
            <text class="timer">{{ cutDownTime.endHour }}</text>
            <text class="timer-colon">:</text>
            <text class="timer">{{ cutDownTime.endMinute }}</text>
            <text class="timer-colon">:</text>
            <text class="timer">{{ cutDownTime.endSecond }}</text>
          </view>
        </view>
      </view>
      <view class="guess-section">
        <view
          v-for="(item, index) in homeFlashPromotion.productList"
          :key="index"
          class="guess-item"
          @click="handleNavToDetailPage(item)"
        >
          <view class="image-wrapper">
            <image :src="item.pic" mode="aspectFill"></image>
          </view>
          <text class="title name-2">{{ item.name }}</text>
          <text class="title2 clamp">{{ item.subTitle }}</text>
          <text class="price">￥{{ item.flashPromotionPrice || item.price }}</text>
        </view>
      </view>
    </view>

    <!-- 新鲜好物 -->
    <view class="section-card">
      <view class="f-header" @click="handleNavToNewProductListPage">
        <view class="tit-box">
          <text class="tit">新鲜好物</text>
          <text class="tit2">为你寻觅世间好物</text>
        </view>
        <text class="more-btn">查看更多</text>
      </view>
      <view class="seckill-section">
        <scroll-view class="floor-list" scroll-x>
          <view class="scoll-wrapper">
            <view
              v-for="(item, index) in newProductList"
              :key="index"
              class="floor-item"
              @click="handleNavToDetailPage(item)"
            >
              <image :src="item.pic" mode="aspectFill"></image>
              <text class="title name-2">{{ item.name }}</text>
              <text class="title2 clamp">{{ item.subTitle }}</text>
              <text class="price">￥{{ item.price }}</text>
            </view>
          </view>
        </scroll-view>
      </view>
    </view>

    <!-- 人气推荐楼层 -->
    <view class="section-card">
      <view class="f-header" @click="handleNavToHotProductListPage">
        <view class="tit-box">
          <text class="tit">人气推荐</text>
          <text class="tit2">大家都赞不绝口的</text>
        </view>
        <text class="more-btn">查看更多</text>
      </view>

      <view class="hot-section">
        <view
          v-for="(item, index) in hotProductList"
          :key="index"
          class="guess-item"
          @click="handleNavToDetailPage(item)"
        >
          <view class="image-wrapper">
            <image :src="item.pic" mode="aspectFill"></image>
          </view>
          <view class="txt">
            <text class="title clamp">{{ item.name }}</text>
            <text class="title2">{{ item.subTitle }}</text>
            <text class="price">￥{{ item.price }}</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 猜你喜欢 -->
    <view class="section-card">
      <view class="f-header">
        <view class="tit-box">
          <text class="tit">猜你喜欢</text>
          <text class="tit2">你喜欢的都在这里了</text>
        </view>
      </view>

      <view class="guess-section">
        <view
          v-for="(item, index) in recommendProductList"
          :key="index"
          class="guess-item"
          @click="handleNavToDetailPage(item)"
        >
          <view class="image-wrapper">
            <image :src="item.pic" mode="aspectFill"></image>
          </view>
          <text class="title name-2">{{ item.name }}</text>
          <text class="title2 clamp">{{ item.subTitle }}</text>
          <text class="price">￥{{ item.price }}</text>
        </view>
      </view>
    </view>
    <uni-load-more class="load-more" :status="loadingType"></uni-load-more>
  </view>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import {
  onLoad,
  onPullDownRefresh,
  onReachBottom,
  onPageScroll,
  onNavigationBarButtonTap,
  onNavigationBarSearchInputClicked,
} from '@dcloudio/uni-app'
import { getHomeContentAPI, getRecommendProductListAPI } from '@/apis/home'
import type { SmsHomeAdvertise, HomeFlashPromotion } from '@/types/home'
import type { PmsProduct } from '@/types/product'
import type { PmsBrand } from '@/types/brand'
import type { PageParam } from '@/types/common'

// ===== 页面数据 =====
// 标题栏背景色
const titleNViewBackground = ref('')
// 标题栏背景色列表
const titleNViewBackgroundList = ['rgb(203, 87, 60)', 'rgb(205, 215, 218)']
// 轮播图当前索引
const swiperCurrent = ref(0)
// 轮播图长度
const swiperLength = ref(0)
// 加载状态
const loadingType = ref<'more' | 'loading' | 'noMore'>('more')
// 页面滚动状态
const isScrolled = ref(false)

// 轮播广告
const advertiseList = ref<SmsHomeAdvertise[]>([])
// 品牌制造商直供
const brandList = ref<PmsBrand[]>([])
// 秒杀专区
const homeFlashPromotion = ref<HomeFlashPromotion | null>(null)
// 新鲜好物
const newProductList = ref<PmsProduct[]>([])
// 人气推荐
const hotProductList = ref<PmsProduct[]>([])
// 猜你喜欢
const recommendProductList = ref<PmsProduct[]>([])
// 猜你喜欢分页参数
const recommendPageParam = ref<PageParam>({
  pageNum: 1,
  pageSize: 4,
})

// ===== 计算属性 =====
// 秒杀倒计时计算
const cutDownTime = computed(() => {
  if (!homeFlashPromotion.value?.endTime) {
    return { endHour: '00', endMinute: '00', endSecond: '00' }
  }
  const endTime = new Date(homeFlashPromotion.value.endTime)
  const now = new Date()
  const endDateTime = new Date()
  endDateTime.setHours(endTime.getHours())
  endDateTime.setMinutes(endTime.getMinutes())
  endDateTime.setSeconds(endTime.getSeconds())
  const offsetTime = endDateTime.getTime() - now.getTime()
  const endHour = String(Math.floor(offsetTime / (60 * 60 * 1000))).padStart(2, '0')
  const offsetMinute = offsetTime % (60 * 60 * 1000)
  const endMinute = String(Math.floor(offsetMinute / (60 * 1000))).padStart(2, '0')
  const offsetSecond = offsetTime % (60 * 1000)
  const endSecond = String(Math.floor(offsetSecond / 1000)).padStart(2, '0')
  return { endHour, endMinute, endSecond }
})

// ===== 数据加载 =====
// 加载首页数据
const loadData = async () => {
  try {
    const res = await getHomeContentAPI()
    const data = res.data
    advertiseList.value = data?.advertiseList || []
    brandList.value = data?.brandList || []
    homeFlashPromotion.value = data?.homeFlashPromotion || null
    newProductList.value = data?.newProductList || []
    hotProductList.value = data?.hotProductList || []
    swiperLength.value = advertiseList.value.length
    titleNViewBackground.value = titleNViewBackgroundList[0]
    // 加载推荐商品
    const recommendRes = await getRecommendProductListAPI(recommendPageParam.value)
    recommendProductList.value = recommendRes.data || []
    uni.stopPullDownRefresh()
  } catch (err) {
    console.error('加载首页数据失败', err)
    uni.stopPullDownRefresh()
  }
}

// ===== 生命周期 =====
// 页面加载时执行
onLoad(() => {
  loadData()
})

// 下拉刷新时执行
onPullDownRefresh(() => {
  recommendPageParam.value.pageNum = 1
  loadData()
})

// 上拉加载更多时执行
onReachBottom(async () => {
  recommendPageParam.value.pageNum++
  loadingType.value = 'loading'
  try {
    const res = await getRecommendProductListAPI(recommendPageParam.value)
    const list = res.data as PmsProduct[]
    if (!list || list.length === 0) {
      recommendPageParam.value.pageNum--
      loadingType.value = 'noMore'
    } else {
      recommendProductList.value = recommendProductList.value.concat(list)
      loadingType.value = 'more'
    }
  } catch (err) {
    loadingType.value = 'more'
  }
})

// 页面滚动监听 - 改变导航栏按钮颜色
// @ts-ignore
onPageScroll((e: { scrollTop: number }) => {
  isScrolled.value = e.scrollTop > 50
  // #ifdef APP-PLUS
  // 动态改变导航栏按钮颜色 (App端)
  const pages = getCurrentPages()
  const page = pages[pages.length - 1]
  // @ts-ignore
  const currentWebview = page.$getAppWebview()
  if (currentWebview) {
    // @ts-ignore
    currentWebview.setTitleNViewButtonStyle(0, {
      color: isScrolled.value ? '#303133' : '#ffffff',
    })
    // @ts-ignore
    currentWebview.setTitleNViewButtonStyle(1, {
      color: isScrolled.value ? '#303133' : '#ffffff',
    })
  }
  // #endif
  // #ifdef H5
  // 动态改变导航栏图标颜色 (H5端) - 直接操作 DOM，因 titleNView buttons 使用内联 style
  const btnIcons = document.querySelectorAll('.uni-page-head-btn .uni-btn-icon')
  btnIcons.forEach((el: any) => {
    el.style.color = isScrolled.value ? '#303133' : '#ffffff'
  })
  // 同步修改搜索框放大镜图标颜色
  const searchIconPath = document.querySelector('.uni-page-head-search-icon path')
  if (searchIconPath) {
    searchIconPath.setAttribute('fill', isScrolled.value ? '#606266' : '#fff')
  }
  // #endif
})

// ===== 事件处理方法 =====
// 轮播图切换
const handleSwiperChange = (e: any) => {
  const index = e.detail.current
  swiperCurrent.value = index
  const changeIndex = index % titleNViewBackgroundList.length
  titleNViewBackground.value = titleNViewBackgroundList[changeIndex]
}

// 跳转到商品详情页
const handleNavToDetailPage = (item: PmsProduct) => {
  const id = item.id
  uni.navigateTo({
    url: `/pages/product/product?id=${id}`,
  })
}

// 跳转到广告详情页
const handleNavToAdvertisePage = (item: SmsHomeAdvertise) => {
  // TODO: 实现广告详情页跳转逻辑
}

// 跳转到品牌制造商直供页
const handleNavToRecommendBrandPage = () => {
  uni.navigateTo({
    url: '/pages/brand/list',
  })
}

// 跳转到品牌详情页
const handleNavToBrandDetailPage = (item: PmsBrand) => {
  uni.navigateTo({
    url: `/pages/brand/brandDetail?id=${item.id}`,
  })
}

// 跳转到新品列表页
const handleNavToNewProductListPage = () => {
  uni.navigateTo({
    url: '/pages/product/newProductList',
  })
}

// 跳转到人气推荐列表页
const handleNavToHotProductListPage = () => {
  uni.navigateTo({
    url: '/pages/product/hotProductList',
  })
}

// 跳转到专题页
const handleNavToTopicPage = () => {
  uni.showToast({
    icon: 'none',
    title: '功能开发中',
  })
}

// 跳转到搜索页
const handleSearch = () => {
  uni.navigateTo({
    url: '/pages/product/search',
  })
}

// 扫描功能（小程序端导航栏左侧按钮）
const handleScan = () => {
  uni.showToast({
    icon: 'none',
    title: '扫描功能开发中',
  })
}

// 跳转到通知页（小程序端导航栏右侧按钮）
const handleNavToNotice = () => {
  uni.navigateTo({
    url: '/pages/notice/notice',
  })
}

// 导航栏搜索框点击（H5/App端）
onNavigationBarSearchInputClicked(() => {
  handleSearch()
})

// ===== 其他方法 =====
// 时间格式化
const formatTime = (time: string | undefined) => {
  if (!time) return 'N/A'
  const date = new Date(time)
  const h = String(date.getHours()).padStart(2, '0')
  const m = String(date.getMinutes()).padStart(2, '0')
  const s = String(date.getSeconds()).padStart(2, '0')
  return `${h}:${m}:${s}`
}

// 导航栏按钮点击事件
onNavigationBarButtonTap((e: { index: number }) => {
  const index = e.index
  if (index === 0) {
    uni.showToast({
      icon: 'none',
      title: '扫描功能开发中',
    })
  } else if (index === 1) {
    // #ifdef APP-PLUS
    const pages = getCurrentPages()
    const page = pages[pages.length - 1]
    // @ts-ignore
    const currentWebview = page.$getAppWebview()
    currentWebview.hideTitleNViewButtonRedDot({
      index,
    })
    // #endif
    uni.navigateTo({
      url: '/pages/notice/notice',
    })
  }
})
</script>

<style lang="scss">
/* #ifdef MP */
page {
  .carousel-section {
    padding: 0;

    .titleNview-placing {
      padding-top: 0;
      height: 0;
    }

    .swiper-dots {
      left: 40rpx;
      bottom: 28rpx;
    }
  }
}
/* #endif */

page {
  background: #eef1f7;
  font-family: Inter, 'PingFang SC', 'Microsoft YaHei', -apple-system, sans-serif;
}
</style>

<style lang="scss" scoped>
/* #ifdef MP */
.mp-search-box {
  position: absolute;
  left: 0;
  top: 30rpx;
  z-index: 9999;
  width: 100%;
  display: flex;
  align-items: center;
  padding: 0 20rpx;

  .nav-icon {
    font-size: 44rpx;
    color: #fff;
    padding: 0 10rpx;
  }

  .ser-input {
    flex: 1;
    height: 56rpx;
    line-height: 56rpx;
    text-align: center;
    font-size: 28rpx;
    color: $font-color-base;
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.6);
  }
}

/* #endif */

/* 主题变量 */
$primary: #5b8def;
$page-bg: #eef1f7;
$card-bg: #fff;
$deep-text: #2a2f3d;
$secondary-text: rgba(0, 0, 0, 0.6);
$muted-text: rgba(0, 0, 0, 0.42);
$btn-bg: #e6edfb;
$btn-text: #3f6fd0;
$image-bg: #f6f7fa;
$ease: cubic-bezier(0.4, 0, 0.2, 1);

.container {
  min-height: 100vh;
  padding: 16rpx 24rpx 48rpx;
  background: $page-bg;
  font-family: Inter, 'PingFang SC', 'Microsoft YaHei', -apple-system, sans-serif;
  color: $deep-text;
}

/* 顶部搜索 */
.search-bar {
  padding: 8rpx 0 24rpx;
}

.search-box {
  display: flex;
  align-items: center;
  height: 84rpx;
  padding: 0 28rpx;
  background: $card-bg;
  border-radius: 9999rpx;
  transition: background-color 200ms $ease;

  &:active {
    background: #f7f9fd;
  }
}

.search-icon {
  margin-right: 16rpx;
  font-size: 30rpx;
  color: $muted-text;
}

.search-placeholder {
  flex: 1;
  font-size: 26rpx;
  color: $muted-text;
}

/* 头部 轮播图 */
.carousel-section {
  position: relative;
  margin-bottom: 24rpx;

  .titleNview-placing,
  .titleNview-background {
    display: none;
  }
}

.carousel {
  width: 100%;
  height: 320rpx;

  .carousel-item {
    width: 100%;
    height: 100%;
    overflow: hidden;
    border-radius: 32rpx;
  }

  image {
    width: 100%;
    height: 100%;
    border-radius: 32rpx;
  }
}

.swiper-dots {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 40rpx;
  bottom: 28rpx;
  width: 96rpx;
  height: 40rpx;
  padding: 0 12rpx;
  background: rgba(42, 47, 61, 0.4);
  border-radius: 9999rpx;

  .num {
    font-size: 20rpx;
    line-height: 40rpx;
    color: #fff;
  }

  .sign {
    margin: 0 4rpx;
    font-size: 20rpx;
    line-height: 40rpx;
    color: rgba(255, 255, 255, 0.7);
  }
}

/* 快捷入口 */
.cate-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 28rpx 16rpx;
  margin-bottom: 24rpx;
  background: $card-bg;
  border-radius: 32rpx;

  .cate-item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    transition: transform 200ms $ease;

    &:active {
      transform: translateY(-2rpx);
    }

    text {
      font-size: 26rpx;
      color: $secondary-text;
    }
  }

  image {
    box-sizing: border-box;
    width: 88rpx;
    height: 88rpx;
    margin-bottom: 14rpx;
    padding: 12rpx;
    border-radius: 24rpx;
    background: $image-bg;
  }
}

/* 区块卡片 */
.section-card {
  padding: 30rpx 24rpx;
  margin-bottom: 24rpx;
  background: $card-bg;
  border-radius: 32rpx;
}

/* 新鲜好物 - 横向滚动 */
.seckill-section {
  width: 100%;
}

.floor-list {
  width: 100%;
  height: 460rpx;
  white-space: nowrap;
}

.scoll-wrapper {
  display: flex;
  align-items: flex-start;
}

.floor-item {
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  width: 280rpx;
  margin-right: 20rpx;

  &:last-child {
    margin-right: 0;
  }

  image {
    width: 280rpx;
    height: 280rpx;
    margin-bottom: 14rpx;
    border-radius: 24rpx;
    background: $image-bg;
  }

  .title {
    font-size: 26rpx;
    line-height: 1.45;
    color: $deep-text;
  }

  .title2 {
    margin-top: 4rpx;
    font-size: 24rpx;
    line-height: 1.4;
    color: $muted-text;
  }

  .price {
    margin-top: 8rpx;
    font-size: 30rpx;
    font-weight: 600;
    line-height: 1.2;
    color: $primary;
  }
}

/* 区块标题 */
.f-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 24rpx;

  .tit-box {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
  }

  .tit {
    font-size: 34rpx;
    font-weight: 600;
    line-height: 1.35;
    color: $deep-text;
  }

  .tit2 {
    margin-top: 6rpx;
    font-size: 24rpx;
    line-height: 1.4;
    color: $muted-text;
  }
}

/* 查看更多胶囊按钮 */
.more-btn {
  flex-shrink: 0;
  display: inline-block;
  margin-left: 20rpx;
  padding: 10rpx 24rpx;
  font-size: 24rpx;
  line-height: 1.4;
  color: $btn-text;
  background: $btn-bg;
  border-radius: 9999rpx;
  transition: background-color 200ms $ease;

  &:active {
    background: #d8e5fa;
  }
}

/* 秒杀倒计时 */
.countdown {
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-left: 16rpx;
}

.countdown-label {
  display: block;
  margin-bottom: 8rpx;
  font-size: 22rpx;
  line-height: 1;
  color: $muted-text;
}

.countdown-time {
  display: flex;
  align-items: center;
}

.timer {
  display: inline-block;
  box-sizing: border-box;
  min-width: 44rpx;
  height: 40rpx;
  padding: 0 8rpx;
  font-size: 24rpx;
  line-height: 40rpx;
  text-align: center;
  color: $btn-text;
  background: $btn-bg;
  border-radius: 12rpx;
}

.timer-colon {
  margin: 0 6rpx;
  font-size: 24rpx;
  color: $muted-text;
}

/* 商品卡片网格 */
.guess-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-bottom: -24rpx;

  .guess-item {
    display: flex;
    flex-direction: column;
    width: calc(50% - 8rpx);
    margin-bottom: 24rpx;
    background: $card-bg;
    border-radius: 24rpx;
    transition: transform 200ms $ease;

    &:active {
      transform: translateY(-2rpx);
    }
  }

  .image-wrapper {
    width: 100%;
    height: 300rpx;
    margin-bottom: 16rpx;
    overflow: hidden;
    border-radius: 24rpx;
    background: $image-bg;

    image {
      width: 100%;
      height: 100%;
    }
  }

  .image-wrapper-brand {
    width: 100%;
    height: 140rpx;
    margin-bottom: 16rpx;
    overflow: hidden;
    border-radius: 24rpx;
    background: $image-bg;

    image {
      width: 100%;
      height: 100%;
    }
  }

  .title {
    font-size: 26rpx;
    font-weight: 500;
    line-height: 1.45;
    color: $deep-text;
  }

  .title2 {
    margin-top: 4rpx;
    font-size: 24rpx;
    line-height: 1.4;
    color: $muted-text;
  }

  .price {
    margin-top: 10rpx;
    font-size: 30rpx;
    font-weight: 600;
    line-height: 1.2;
    color: $primary;
  }
}

/* 商品名两行省略 */
.name-2 {
  display: -webkit-box;
  overflow: hidden;
  white-space: normal;
  text-overflow: ellipsis;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

/* 人气推荐 - 横向列表 */
.hot-section {
  display: flex;
  flex-direction: column;

  .guess-item {
    display: flex;
    flex-direction: row;
    width: 100%;
    padding-bottom: 24rpx;
    transition: transform 200ms $ease;

    &:last-child {
      padding-bottom: 0;
    }

    &:active {
      transform: translateY(-2rpx);
    }
  }

  .image-wrapper {
    flex-shrink: 0;
    width: 200rpx;
    height: 200rpx;
    overflow: hidden;
    border-radius: 24rpx;
    background: $image-bg;

    image {
      width: 100%;
      height: 100%;
    }
  }

  .txt {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    padding-left: 24rpx;
  }

  .title {
    font-size: 28rpx;
    font-weight: 500;
    line-height: 1.45;
    color: $deep-text;
  }

  .title2 {
    display: block;
    margin-top: 6rpx;
    font-size: 24rpx;
    line-height: 1.4;
    color: $muted-text;
  }

  .price {
    margin-top: auto;
    font-size: 32rpx;
    font-weight: 600;
    line-height: 1.2;
    color: $primary;
  }
}

.load-more {
  padding: 16rpx 0;
}
</style>
