<template>
  <a-modal v-model="show" title="报警详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
      <a-button key="back1" @click="submit" type="primary" v-if="orderData.status == 0">
        处理
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
          {{ orderData.userCode }}
        </a-col>
        <a-col :span="8"><b>报警时间：</b>
          {{ orderData.createDate ? orderData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>用户名称：</b>
          {{ orderData.userName ? orderData.userName : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>当前状态：</b>
          <span v-if="orderData.status == 0">未处理</span>
          <span v-if="orderData.status == 1">已处理</span>
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 5px"><span style="font-size: 15px;font-weight: 650;color: #000c17">报警位置</span></a-col>
      </a-row>
      <div>
        <a-card :bordered="false" style="height: 500px">
          <div id="areas" style="width: 100%;height: 450px;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
        </a-card>
      </div>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.orderShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '商品名称',
        dataIndex: 'drugName'
      }, {
        title: '品牌',
        dataIndex: 'brand'
      }, {
        title: '数量',
        dataIndex: 'quantity'
      }, {
        title: '商品图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '单价',
        dataIndex: 'unitPrice'
      }]
    },
    logisticsColumns () {
      return [{
        title: '物流信息',
        dataIndex: 'remark'
      }, {
        title: '操作时间',
        dataIndex: 'createDate'
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      buyer: null,
      sale: null,
      commodityData: null
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.orderData)
          }, 500)
        }, 200)
      }
    }
  },
  methods: {
    local (merchantData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(merchantData.longitude, merchantData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    dataInit (orderId) {
      // 商品信息
      this.$get(`/cos/order-info/detail/${orderId}`).then((r) => {
        this.buyer = r.data.buyer
        this.sale = r.data.sale
        this.commodityData = r.data.goods
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    submit () {
      this.orderData.status = 1
      this.$put('/cos/alert-info', {
        ...this.orderData
      }).then((r) => {
        this.$emit('success')
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
