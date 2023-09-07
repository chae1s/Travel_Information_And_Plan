<template>
    <div class="item_detail_info">
        <div class="content_header">
            <div class="item_title">{{ item.title }}</div>
        </div>
        <img :src="item.firstImage" alt=""><br>
        <div class="item_content">
            <div class="item_text">
            <div>상세정보</div>
            <br>
            <div class="item_overview">{{item.overview}}</div>
            <br>
            <img :src="'https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=300&h=300&center=' + item.mapx + ',' + item.mapy + '&level=16&X-NCP-APIGW-API-KEY-ID=pvpyjs42ed'">
            <p class="item_fullAddress">주소 : {{ item.addr1 }}</p>
            <br>
            <p class="item_homepage">
                홈페이지 : <a :href="item.homepage" target="_blank">{{ item.homepage }}</a>
            </p>
            </div>
        </div>
    </div>
</template>
<script>
import axios from "axios";
  export default {
      name: "itemDetail",
      data() {
          return {
              itemId : this.$route.params.id,
              item: {
                  id:'',
                  contentTypeId: '',
                  title: '',
                  firstImage: '',
                  firstImage2: '',
                  sido: '',
                  addr1:'',
                  tel:'',
                  homepage:'',
                  sigungucode:'',
                  mapx:'', //경도
                  mapy:'', //위도
                  overview:''
              },
          }
      },
      mounted() {
          this.fetchItemDetail(this.itemId);
      },
      methods: {
          fetchItemDetail(id) {
              const apiUrl = '/item-list/read/'+ id;

              axios.get(apiUrl)
                  .then(res => {
                      const itemData = res.data.response.body.items.item[0]; // API에서 받아온 데이터

                      // item 데이터 업데이트
                      this.item = {
                          id: itemData.contentid,
                          contentTypeId: itemData.contenttypeid,
                          title: itemData.title,
                          firstImage: itemData.firstimage,
                          firstImage2: itemData.firstimage2,
                          sido: itemData.sido,
                          addr1: itemData.addr1,
                          tel: itemData.tel,
                          homepage: itemData.homepage,
                          sigungucode: itemData.sigungucode,
                          mapx: itemData.mapx,
                          mapy: itemData.mapy,
                          overview: itemData.overview
                      };

                      const urlPattern = /<a href="([^"]+)" target="_blank"/;
                      const match = this.item.homepage.match(urlPattern);
                      if (match) {
                          this.item.homepage = match[1];
                      } else {
                          console.log('URL을 찾을 수 없습니다.');
                      }
                      console.log('homepage:', this.item.homepage);
                  })
                  .catch(error => {
                      console.error('API 호출 오류', error);
                  });
          },
      }
  }
</script>

<style scoped>
.item_detail_info {
    padding: 20px;
    background-color: #f4f4f4;
}


.content_header {
    padding: 20px 0;
    display: flex;
    justify-content: space-between;
    background-color: #ffffff;
    border-bottom: 1px solid #e0e0e0;
}

.content_header div {
    font-size: 22px;
    font-weight: 700;
}

.content_header a {
    font-size: 14px;
    line-height: 27px;
}

.item {
    width: 25%;
    padding: 0 10px;
    box-sizing: border-box;
}

.item img {
    width: 100%;
    height: 155px;
    border-radius: 8px;
}

.item_info {
    padding: 0 10px;
}

.item_text {
    margin-top: 14px;
    box-sizing: border-box;
}

.item_title {
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 20px;
    font-weight: 600;
    text-align: center;
}

.item_fullAddress {
    text-overflow: ellipsis;
    word-wrap: normal;
    margin-top: 8px;
    font-size: 16px;
    text-align: left;
}

.item_count {
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    font-size: 14px;
}

.item_count li {
    margin-right: 10px;
}

.item_label_list {
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    height: 25px;
}

.item_label {
    padding: 4px 8px;
    margin-right: 8px;
    border-radius: 4px;
    font-size: 13px;
    background-color: #C4DFFF;
}

.__label {
    background-color: #FFE866;
}

.item_detail_info:deep(.location_checkbox) {
    display: flex;
    justify-content: space-between;
    box-sizing: border-box;
    padding-bottom: 10px;
    border-bottom: #DADADA 1px solid;
    margin-bottom: 20px;
}

.item_detail_info:deep(.form_checkbox_btn input[type=checkbox]) {
    display: none;
}

.item_detail_info:deep(.form_checkbox_btn label) {
    width: 44px;
    border-radius: 14px;
    padding: 5px 8px;
    cursor: pointer;
}

.item_detail_info:deep(.form_checkbox_btn input[type=checkbox]:checked + label) {
    background-color: #C4DFFF;
}

.board_section {
    display: flex;
    justify-content: space-between;
}

.notice_board, .popular_board {
    width: 560px;
    height: 250px;
}

.board_section .content_header {
    border-bottom: 1px solid #DADADA;
    padding-bottom: 13px;
    margin-bottom: 11px;
}

.board_list {
    margin-left: 7px;
    display: flex;
    flex-direction: column;
    gap: 13px;
    text-align: left;
}
</style>