<template>
  <div class="wrap">

    <div class="header">
      <h1 class="title">📄 {{ title }}</h1>
      <p>파일확장자에 따라 특정 형식의 파일을 첨부하거나 전송하지 못하도록 제한</p>
    </div>

    <div class="content">
      <ul>
        <li>
          <span>고정 확장자</span>
          <div style="padding: 0px 15px;">
            <div class="default" v-for="item in defaultExtensions" v-bind:key="item.id">
              <input type="checkbox" :id="item.name" :value="item.name" v-model="item.checkYn"
                @change="changeDefaultExtension(item)" true-value="Y" false-value="N">
              <label :for="item.name">{{ item.name }}</label>
            </div>
            <!-- <button>고정</button> -->
          </div>
        </li>
        <li>
          <span>커스텀 확장자</span>
          <div style="padding: 0px 18px;">
            <input type="text" v-model="inputExtension" placeholder="확장자 입력" class="inputExtension" min="1" max="20">
            <button @click="addExtension()">+추가</button>
          </div>
        </li>
      </ul>
      <div class="custom">
        <p class="custom-count" :class="{ active: isActive }">{{ customExtensionCount }}/200</p>
        <p v-if="error.message != ''" style="color:red; font-size: 14px;">{{ error.message }}</p>
        <div class="custom-detail" v-for="(item, index) in customExtensions" v-bind:key="item.id">
          <button @click="removeExtension(item, index)">{{ item.name }} ✕</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import '../assets/main.css'

export default {
  name: 'FileExtension',
  props: {
    title: String
  },
  data: () => ({
    isActive: false,
    inputExtension: '',
    customExtensions: [],
    customExtensionCount: 0,
    defaultExtensions: [],
    extensions: [
      { id: 1, name: 'bat', checkYn: 'N' },
      { id: 2, name: 'cmd', checkYn: 'N' },
      { id: 3, name: 'com', checkYn: 'N' },
      { id: 4, name: 'cpl', checkYn: 'N' },
      { id: 5, name: 'exe', checkYn: 'N' },
      { id: 6, name: 'scr', checkYn: 'N' },
      { id: 7, name: 'js', checkYn: 'N' }
    ],
    error: {
      status: 0,
      message: ''
    }

  }),
  created() {
    // this.initDefaultExtension()
  },
  mounted() {
    this.allExtensionList()
  },
  watch: {
    customExtensionCount: function () {
      this.customExtensionCount = this.customExtensions.length
      if (this.customExtensionCount > 0) {
        this.isActive = true
      } else {
        this.isActive = false
      }
    },
    defaultExtensions: function () {
      if (this.defaultExtensions == '' && this.defaultExtensions.length == 0) {
        this.initDefaultExtension()
      }
    }

  },
  methods: {
    async initDefaultExtension() {
      if (this.defaultExtensions == '' && this.defaultExtensions.length == 0) {
        for (let item of this.extensions) {
          await this.changeDefaultExtension(item);
        }
      }
    },
    async changeDefaultExtension(item) {
      var params = {
        id: item.id,
        name: item.name,
        checkYn: item.checkYn,
        type: 'D'
      }
      await axios.post('/api/v1/extension', params).then(res => {
        this.defaultExtensions = res.data
      })
      this.allExtensionList()
    },
    allExtensionList() {
      axios.get('/api/v1/extension').then(res => {
        this.defaultExtensions = res.data.defaultExtensions
        this.customExtensions = res.data.customExtensions
        this.customExtensionCount = this.customExtensions.length

        this.defaultExtensions.sort(function (prev, cur) {
          if (prev.id > cur.id) return 1;
          if (prev.id < cur.id) return -1;
        });
      })
    },
    async addExtension() {
      if (this.inputExtension.length > 20) {
        alert('확장자는 최대 20자까지 입력 가능합니다')
        return false
      }
      var params = {
        name: this.inputExtension,
        type: 'C'
      }
      await axios.post('/api/v1/extension', params)
        .then(res => {
          this.customExtensions = res.data
          this.customExtensionCount++
          this.inputExtension = ''
          this.error = {}

          this.allExtensionList()
        })
        .catch((error) => {
          this.error.status = error.response.data.status
          this.error.message = error.response.data.message
        })
    },
    removeExtension(item) {
      axios.delete('/api/v1/extension/' + item.id)
        .then(() => {
          this.customExtensionCount--
          this.allExtensionList()
        })
    }
  }
}
</script>

<style scoped>
.active {
  color: #000
}
</style>