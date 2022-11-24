<template>
  <div class="board-detail">
      <div class="common-buttons">
        <button type="button" class="w3-btn w3-white w3-border w3-hover-aqua" v-on:click="fnList">목록</button>
        <button type="button" class="w3-btn w3-white w3-border w3-border-teal w3-hover-teal" v-on:click="fnWrite">등록</button>
      </div>
    <div class="board-contents">
      <strong>게시글 제목</strong>
      <h3><input v-model="posts.title"/></h3>
      <br/>
      <div>
        <strong>작성자</strong>
        <br/>
        <strong class="w3-large"><input v-model="posts.author"/></strong>
        <br/>
        <span>{{posts.createdDate}}</span>
      </div>
    </div>
    <div class="board-contents">
      <strong>게시글 내용</strong>
      <br/>
      <textarea rows="20" cols="90" style="resize:none" v-model="posts.content"/>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-btn w3-white w3-border w3-hover-aqua" v-on:click="fnList">목록</button>
      <button type="button" class="w3-btn w3-white w3-border w3-border-teal w3-hover-teal" v-on:click="fnWrite">등록</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
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
  methods: {
    fnList(){
      delete this.requestBody.id
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnWrite(){
      if(window.confirm('글을 등록하시겠습니까?')) {
        if(this.posts.title === '' || this.posts.author === '' || this.posts.content === '') {
          alert('게시글 제목, 작성자, 내용은 필수입니다')
        } else {
          this.$axios.post("/api/v1/posts/", this.posts).then(() => {
            alert('글이 등록되었습니다.')
            this.fnList()
          }).catch((err) => {
            if (err.message.indexOf('Network Error') > -1) {
              alert('네트워크가 원할하지 않습니다.\n잠시 후 다시 시도해주세요')
            }
          })
        }
      } else {
        console.log('취소. 글을 등록하지 않음')
      }
    }
  }
}
</script>