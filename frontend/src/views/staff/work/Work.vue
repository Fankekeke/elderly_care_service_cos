<template>
  <a-row :gutter="20" style="width: 100%;margin-top: 20px">
    <a-col :span="24" style="margin-top: 15px;margin-bottom: 35px;">
      <a-input-search placeholder="搜索预约工单" style="width: 300px;margin: 0 auto" v-model="key" @search="selectDrugList" />
<!--      <a-button type="primary" style="margin-left: 25px" @click="cartOpen">-->
<!--        车【{{ cartView.data.length }} 件】-->
<!--      </a-button>-->
    </a-col>
    <a-col :span="24" v-if="drugList.length == 0">
      <div style="width: 100%">
        <p style="text-align: center;margin-top: 50px"><a-icon type="folder-open" theme="twoTone" style="font-size: 100px;" /></p>
        <p style="font-size: 26px;text-align: center;margin-top: 5px">暂无预约工单</p>
      </div>
    </a-col>
    <a-col :span="6" v-for="(item, index) in drugList" :key="index" style="margin-bottom: 15px">
      <div style="width: 100%;margin-bottom: 15px;text-align: left;box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;">
        <a-card :bordered="false" @click="drugDetail(item)" hoverable>
          <a-carousel autoplay style="height: 150px;" v-if="item.images !== undefined && item.images !== ''">
            <div style="width: 100%;height: 150px" v-for="(item, index) in item.images.split(',')" :key="index">
              <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 250px">
            </div>
          </a-carousel>
          <a-card-meta :description="item.content.slice(0, 35)+'...'" style="margin-top: 10px;margin-left: 15px">
            <template slot="title">
              <a-row>
                <a-col :span="12">
                  <a-tag color="#108ee9" v-if="item.type == 1">房间清洁</a-tag>
                  <a-tag color="#108ee9" v-if="item.type == 2">家电维修</a-tag>
                  <a-tag color="#108ee9" v-if="item.type == 3">家具安装</a-tag>
                  <a-tag color="#108ee9" v-if="item.type == 4">搬家</a-tag>
                  <a-tag color="#108ee9" v-if="item.type == 5">全屋装修</a-tag>
                  <a-tag color="#108ee9" v-if="item.type == 6">水电维修</a-tag>
                </a-col>
                <a-col :span="12" style="text-align: right;padding-right: 15px">下单人：{{ item.userName }}</a-col>
              </a-row>
            </template>
          </a-card-meta>
          <div style="font-size: 12px;font-family: SimHei;padding: 15px">
            <span>详细位置：{{ item.address }}</span>
            <span style="color: #f5222d; font-size: 13px;float: right">{{ item.totalPrice }}元</span>
          </div>
        </a-card>
      </div>
    </a-col>
    <drug-view
      @close="handleDrugViewClose"
      @success="handleDrugViewSuccess"
      :drugShow="drugView.visiable"
      :drugData="drugView.data">
    </drug-view>
    <cart-view
      @close="handleCartViewClose"
      @success="handleCartViewSuccess"
      :cartShow="cartView.visiable"
      :cartData="cartView.data">
    </cart-view>
  </a-row>
</template>

<script>
import drugView from './DrugView'
import CartView from './CartView'
import {mapState} from 'vuex'

export default {
  name: 'Cart',
  components: {CartView, drugView},
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      drugList: [],
      drugView: {
        visiable: false,
        data: null
      },
      cartView: {
        visiable: false,
        data: []
      },
      key: ''
    }
  },
  mounted () {
    this.selectDrugList()
  },
  methods: {
    cartOpen () {
      this.cartView.visiable = true
    },
    handleDrugViewClose () {
      this.drugView.visiable = false
    },
    handleCartViewClose () {
      this.cartView.visiable = false
    },
    handleCartViewSuccess () {
      this.cartView.visiable = false
      this.cartView.data = []
      this.$message.success('添加订单成功，请前往我的订单付款')
    },
    handleDrugViewSuccess (drugData) {
      // let check = false
      // this.cartView.data.forEach(e => {
      //   if (e.drugId === drugData.drugId && e.pharmacyId === drugData.pharmacyId) {
      //     e.total = drugData.total
      //     check = true
      //   }
      // })
      // if (!check) {
      //   this.cartView.data.push(drugData)
      // }
      this.drugView.visiable = false
      this.$get(`/cos/service-reserve-info/workOrderCheck`, {orderId: drugData.id, workId: this.currentUser.userId}).then((r) => {
        this.$message.success('接单成功，请前往完成作业')
        this.selectDrugList()
      })
    },
    drugDetail (row) {
      this.drugView.visiable = true
      this.drugView.data = row
    },
    selectDrugList () {
      this.$get(`/cos/service-reserve-info/queryNotCheckOrder`, {key: this.key, userId: this.currentUser.userId}).then((r) => {
        this.drugList = r.data.data
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 150px;
  line-height: 150px;
  overflow: hidden;
}

>>> .ant-card-body {
  padding: 0;
}
</style>
