<template>
  <LoadingSpinner v-if="isLoading"></LoadingSpinner>
  <div class="board-detail" v-else>
    <div class="board-contents">
      <h3>{{ title }}</h3>
      <br/>
      <div>
        <strong class="w3-large">{{ author }}</strong>
        <br>
        <span>{{ createdDate }}</span>
      </div>
    </div>
    <div class="board-contents">
      <span>{{ content }}</span>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-btn w3-white w3-border w3-hover-aqua" v-on:click="fnList">목록</button>
      <button type="button" class="w3-btn w3-white w3-border w3-border-blue w3-hover-blue" v-on:click="fnDelete">삭제</button>
      <button type="button" class="w3-btn w3-white w3-border w3-border-teal w3-hover-teal" v-on:click="fnModify">수정</button>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from '@/components/LoadingSpinner.vue' // spinner Vue import

export default {
  components: {
    LoadingSpinner
  },
  data() { //변수생성
    return {
      isLoading: true, // 페이지 로딩 체크용 변수 추가
      requestBody: this.$route.query,
      id: this.$route.query.id,
      list: {},
      title: '',
      author: '',
      content: '',
      createdDate: ''
    }
  },
  created() {
    this.fnGetDetail()
  },
  methods: {
    fnGetDetail() {
      this.$axios.get("/api/v1/posts/" + this.id).then((res) => {
        this.title = res.data.title
        this.author = res.data.author
        this.content = res.data.content
        this.createdDate = res.data.createdDate
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
        path: './list'
      })
    },
    fnDelete(){
      if(window.confirm('해당 게시글을 삭제하시겠습니까?')) {
        this.$axios.delete("/api/v1/posts/" + this.id).then(() => {
          alert('글이 삭제되었습니다.')
          this.$router.push({
            path: './list'
          })
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      } else {
        console.log('취소. 변화없음')
      }
    },
    fnModify(){
      this.$router.push({
        path: './modify',
        query: this.requestBody
      })
    }
  }
}
</script>