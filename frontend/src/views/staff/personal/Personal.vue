<template>
  <a-row :gutter="20">
    <a-col :span="6">
      <a-card :loading="loading" :bordered="false">
        <a-form :form="form" layout="vertical">
          <a-row :gutter="20">
            <a-col :span="12">
              <a-form-item label='志愿者姓名' v-bind="formItemLayout">
                <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='志愿者性别' v-bind="formItemLayout">
                <a-select v-decorator="[
              'sex',
              { rules: [{ required: true, message: '请输入志愿者性别!' }] }
              ]">
                  <a-select-option value="1">男</a-select-option>
                  <a-select-option value="2">女</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='出生日期' v-bind="formItemLayout">
                <a-date-picker style="width: 100%" v-decorator="[
            'birthday',
            { rules: [{ required: true, message: '请输入出生日期!' }] }
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='籍 贯' v-bind="formItemLayout">
                <a-input v-decorator="[
            'nativeAddress'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='身份证号码' v-bind="formItemLayout">
                <a-input v-decorator="[
            'idCard'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='学历' v-bind="formItemLayout">
                <a-select v-decorator="[
              'diploma'
              ]">
                  <a-select-option value="1">高中</a-select-option>
                  <a-select-option value="2">专科</a-select-option>
                  <a-select-option value="3">本科</a-select-option>
                  <a-select-option value="4">硕士</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='学校名称' v-bind="formItemLayout">
                <a-input v-decorator="[
            'schoolName'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='家庭住址' v-bind="formItemLayout">
                <a-input v-decorator="[
            'address'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='邮 箱' v-bind="formItemLayout">
                <a-input v-decorator="[
            'mail'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='联系方式' v-bind="formItemLayout">
                <a-input v-decorator="[
            'phone'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label='照片' v-bind="formItemLayout">
                <a-upload
                  name="avatar"
                  action="http://127.0.0.1:9527/file/fileUpload/"
                  list-type="picture-card"
                  :file-list="fileList"
                  @preview="handlePreview"
                  @change="picHandleChange"
                >
                  <div v-if="fileList.length < 2">
                    <a-icon type="plus" />
                    <div class="ant-upload-text">
                      Upload
                    </div>
                  </div>
                </a-upload>
                <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                  <img alt="example" style="width: 100%" :src="previewImage" />
                </a-modal>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
          修改
        </a-button>
      </a-card>
    </a-col>
    <a-col :span="18">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <a-spin :spinning="dataLoading">
            <a-calendar>
              <ul slot="dateCellRender" slot-scope="value" class="events">
                <li v-for="item in getListData(value)" :key="item.content">
                  <a-badge :status="item.type" :text="item.content" />
                </li>
              </ul>
            </a-calendar>
          </a-spin>
        </a-card>
      </div>
    </a-col>
  </a-row>
</template>

<script>
import {mapState} from 'vuex'
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
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      loading: false,
      courseInfo: [],
      dataLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      expertInfo: null
    }
  },
  mounted () {
    this.getExpertInfo(this.currentUser.userId)
  },
  methods: {
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
    },
    getListData (value) {
      let listData = []
      this.courseInfo.forEach(item => {
        if ((moment(value).format('YYYY-MM-DD')) === (moment(item.createDate).format('YYYY-MM-DD'))) {
          listData.push({type: 'success', content: item.code})
        }
      })
      return listData || []
    },
    getExpertInfo (userId) {
      this.dataLoading = true
      this.$get(`/cos/staff-info/detail/${userId}`).then((r) => {
        this.expertInfo = r.data.user
        this.setFormValues(this.expertInfo)
        this.courseInfo = r.data.order
        this.dataLoading = false
      })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...expert}) {
      this.rowId = expert.id
      let fields = ['name', 'sex', 'birthday', 'nativeAddress', 'idCard', 'diploma', 'schoolName', 'address', 'mail', 'phone', 'deptId', 'positionId']
      let obj = {}
      Object.keys(expert).forEach((key) => {
        if (key === 'sex') {
          expert[key] = expert[key].toString()
        }
        if (key === 'birthday' && expert[key] != null) {
          expert[key] = moment(expert[key])
        }
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(expert['images'])
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = expert[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (values.birthday) {
          values.birthday = moment(values.birthday).format('YYYY-MM-DD')
        }
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/staff-info', {
            ...values
          }).then((r) => {
            this.$message.success('更新成功')
            this.loading = false
            this.getExpertInfo(this.currentUser.userId)
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
