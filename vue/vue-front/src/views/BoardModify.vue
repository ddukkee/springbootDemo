<template>
  <LoadingSpinner v-if="isLoading"></LoadingSpinner>
  <div class="board-detail" v-else>
      <div class="common-buttons">
        <button type="button" class="w3-btn w3-white w3-border w3-hover-aqua" v-on:click="fnList">목록</button>
        <button type="button" class="w3-btn w3-white w3-border w3-border-teal w3-hover-teal" v-on:click="fnModify">수정</button>
      </div>
    <div class="board-contents">
      <h3><input v-model="posts.title"/></h3>
      <br/>
      <div>
        <strong class="w3-large">{{posts.author}}</strong>
        <br>
        <span>{{posts.createdDate}}</span>
      </div>
    </div>
    <div class="board-contents">
      <textarea rows="20" cols="90" style="resize:none" v-model="posts.content"/>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from '@/components/LoadingSpinner.vue';
export default {
  components: {
    LoadingSpinner
  },
  data() { //변수생성
    return {
      isLoading: true,
      requestBody: this.$route.query,
      id: this.$route.query.id,
      posts:{
        title: '',
        author: '',
        content: '',
        createdDate: ''
      }
    }
  },
  created() {
    this.fnGetDetail()
  },
  methods: {
    fnGetDetail() {
      this.$axios.get("/api/v1/posts/" + this.id).then((res) => {
        this.posts.title = res.data.title
        this.posts.author = res.data.author
        this.posts.content = res.data.content
        this.posts.createdData = res.data.createdDate
        this.isLoading = false
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnList(){
      delete this.requestBody.id
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnModify(){
      this.$axios.put("/api/v1/posts/" + this.id, this.posts).then(() => {
        alert('글이 수정되었습니다.')
        this.requestBody.id = this.id
        this.$router.push({
          path: './detail',
          query: this.requestBody
        })
      }).catch((err) => {
        if(err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원할하지 않습니다.\n잠시 후 다시 시도해주세요')
        }
      })
    }
  }
}
</script>