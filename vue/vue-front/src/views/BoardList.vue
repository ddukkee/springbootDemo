<!-- BoardList.vue -->
<template>
  <LoadingSpinner v-if="isLoading"></LoadingSpinner>
  <div class="board-list" v-else>
    <div class="common-buttons">
      <button type="button" class="w3-btn w3-round w3-black w3-hover-khaki" v-on:click="fnWrite">게시글 등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, id) in list.data" :key="id">
        <td>{{ row.id }}</td>
        <td><a v-on:click="fnView(`${row.id}`)" style='cursor:pointer'>{{ row.title }}</a></td>
        <td>{{ row.author }}</td>
        <td>{{ row.createdDate }}</td>
      </tr>
      </tbody>
    </table>
    <div class="pagination w3-bar w3-padding-16 w3-small">
        <span class="pg">
          <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
          <a href="javascript:;" v-if="paging.startPage > 10" @click="fnPage(`${paging.startPage - 1}`)"
             class="prev w3-button w3-bar-item w3-border">&lt;</a>
          <template v-for=" (n,index) in paginavigation()">
            <template v-if="paging.page==n">
              <strong class="w3-button w3-bar-item w3-border w3-green" :key="index">{{ n }}</strong>
            </template>
            <template v-else>
              <a class="w3-button w3-bar-item w3-border" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{
                  n
                }}</a>
            </template>
          </template>
          <a href="javascript:;" v-if="paging.totalPageCnt > paging.endPage" @click="fnPage(`${paging.startPage + 1}`)"
             class="prev w3-button w3-bar-item w3-border">&gt;</a>
          <a href="javascript:;" @click="fnPage(`${paging.totalPageCnt}`)"
             class="first w3-button w3-bar-item w3-border">&gt;&gt;</a>
        </span>
    </div>
    <div>
      <select v-model="searchKey">
        <option value=""> - 선택 - </option>
        <option value="author">작성자</option>
        <option value="title">제목</option>
        <option value="content">내용</option>
      </select>
      &nbsp;
      <input type="text" v-model="searchValue" @keyup.enter="fnGetList()">
      &nbsp;
      <button @click="fnGetList()">검색</button>
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
      isLoading: true,
      searchKey: "", //select Key 초기화
      searchValue: "", //select Value 초기화
      list: {}, //리스트 데이터
      searchParams: {},
      requestBody: {}, //리스트 페이지 데이터 전송
      no: '', //게시판 숫자 처리
      paging: {
        block: 0,
        endPage: 0,
        nextBlock: 0,
        page: 0,
        pageSize: 0,
        prevBlock: 0,
        startIndex: 0,
        startPage: 0,
        totalBlockCnt: 0,
        totalListCnt: 0, //총 리스트 갯수
        totalPageCnt: 0, //총 page 갯수
      },
      page: this.$route.query.page ? this.$route.query.page : 1, //query를 통해 별도 page가 정해지지 않는 한은 1페이지를 default 설정
      size: this.$route.query.size ? this.$route.query.size : 10, //query를 통해 별도 size가 정해지지 않는 한은 페이지당 10개를 default 설정
      keyword: this.$route.query.keyword, // 게시글 검색 기능을 위한 변수
      paginavigation: function () { //페이징 처리 for 문 커스텀?
        let pageNumber = [] //;
        let start_page = this.paging.startPage;
        let end_page = this.paging.endPage;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = {
        //검색 단어 설정 keyword: this.keyword,
        page: this.page,
        size: this.size,
        key: this.searchKey,
        value: this.searchValue
      },
          this.$axios.get("/api/v1/posts/list", {
            params: this.requestBody, //페이징 파라미터 전달
            headers: {}
          }).then((res) => {
            if (res.data.resultCode === "OK") {
              console.log(res.data)
              this.list = res.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
              this.paging = res.data.pagination
              this.no = this.paging.totalListCnt - ((this.paging.page - 1) * this.paging.pageSize)
              this.isLoading = false // 로딩 false로 전환
            }
          }).catch((err) => {
            if (err.message.indexOf('Network Error') > -1) {
              alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
            }
          })
    },
    fnView(id) {
      this.requestBody.id = id
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        this.page = n
        this.fnGetList()
      }
    }
  }
}
</script>