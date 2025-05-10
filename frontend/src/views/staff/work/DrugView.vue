<template>
  <a-drawer
    title="预约单详情"
    :maskClosable="false"
    width=900
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="show"
    bodyStyle="padding: 0"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <div v-if="drugData !== null">
      <a-card :bordered="false" style="height: 300px">
        <div id="areas" style="width: 100%;height: 300px;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
      </a-card>
    </div>
    <br/>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="drugData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ drugData.code }}
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ drugData.createDate ? drugData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>订单价格：</b>
          {{ drugData.totalPrice ? drugData.totalPrice : '- -' }} 元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>当前状态：</b>
          <span v-if="drugData.status == 0">未支付</span>
          <span v-if="drugData.status == 1">未接单</span>
          <span v-if="drugData.status == 2">已接单</span>
          <span v-if="drugData.status == 3">已完成</span>
        </a-col>
        <a-col :span="8"><b>作业类型：</b>
          <span v-if="drugData.type == 1">房间清洁</span>
          <span v-if="drugData.type == 2">家电维修</span>
          <span v-if="drugData.type == 3">家具安装</span>
          <span v-if="drugData.type == 4">搬家</span>
          <span v-if="drugData.type == 5">全屋装修</span>
          <span v-if="drugData.type == 6">水电维修</span>
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ drugData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>详细地址：</b>
          {{ drugData.address }}
        </a-col>
        <a-col :span="8"><b>经度：</b>
          {{ drugData.longitude }}
        </a-col>
        <a-col :span="8"><b>维度：</b>
          {{ drugData.latitude }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">问题图片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">问题内容</span></a-col>
        <a-col :span="24">
          {{ drugData.content }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">预约人信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
          {{ drugData.userCode }}
        </a-col>
        <a-col :span="8"><b>用户姓名：</b>
          {{ drugData.userName }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">接单</a-button>
    </div>
  </a-drawer>
</template>

<script>
import moment from 'moment'
import baiduMap from '@/utils/map/baiduMap'
import {mapState} from 'vuex'
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
  name: 'drugView',
  props: {
    drugShow: {
      type: Boolean,
      default: false
    },
    drugData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.drugShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      drugInfo: null,
      pharmacyInfo: null,
      value: 1
    }
  },
  watch: {
    drugShow: function (value) {
      if (value) {
        // this.selectDrugDetail()
        if (this.drugData.images !== null && this.drugData.images !== '') {
          this.imagesInit(this.drugData.images)
        }
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.drugData)
          }, 500)
        }, 200)
      }
    }
  },
  methods: {
    local (pharmacy) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(pharmacy.longitude, pharmacy.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    selectDrugDetail () {
      this.$get(`/stock/service-reserve-info/selectDrugDetail/${ this.drugData.id}`).then((r) => {
        this.drugInfo = r.data.drug
        this.pharmacyInfo = r.data.pharmacy
        if (this.drugInfo.images !== null && this.drugInfo.images !== '') {
          this.imagesInit(this.drugInfo.images)
        }
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.pharmacyInfo)
          }, 500)
        }, 200)
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
    onClose () {
      this.$emit('close')
    },
    handleSubmit () {
      this.$emit('success', this.drugData)
    }
  }
}
</script>

<style scoped>
  >>> .ant-drawer-body {
    padding: 0
  }

  >>> .ant-card-body {
    padding: 0
  }
</style>
