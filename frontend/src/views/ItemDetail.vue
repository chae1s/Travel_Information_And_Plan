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
            <router-link :to="'/item-detail/read/' + item.id + '/reviews'">
                <div class="item_reviews">
                    <a>후기 목록</a>
                </div>
            </router-link>
            <item-review :item-id="item.id"></item-review>
        </div>
    </div>
</template>
<script>
import ItemReview from '@/components/ItemReview.vue';
import axios from "axios";
  export default {
      name: "itemDetail",
      components: {
          'item-review': ItemReview,
      },
      data() {
          return {
              itemId : this.$route.params.id,
              item: {
                  id:'',
                  contentId:'',
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
                          id: id,
                          contentId: itemData.contentid,
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
                      console.log('item:', res.data.response.body)
                      const urlPattern = /<a href="([^"]+)" target="_blank"/;
                      const match = this.item.homepage.match(urlPattern);
                      if (match) {
                          this.item.homepage = match[1];
                          console.log(typeof(this.item.id));
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
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}


.content_header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    text-align: center;
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

.item_image img {
    width: 100%;
    max-height: 300px;
    object-fit: cover;
    border-radius: 8px;
}

.item_text {
    margin-top: 14px;
    box-sizing: border-box;
}


.item_title {
    font-size: 24px;
    font-weight: 700;
    margin: 0;
}

.item_fullAddress {
    text-overflow: ellipsis;
    word-wrap: normal;
    margin-top: 8px;
    font-size: 16px;
    text-align: left;
}
.item_homepage a {
    font-size: 14px;
    text-decoration: none;
    color: #007BFF;
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
.item_content {
    margin-top: 20px;
}
.item_overview {
    font-size: 16px;
    line-height: 1.5;
    margin-bottom: 20px;
}

.item_fullAddress {
    font-size: 16px;
    margin: 0;
}

.item_map img {
    width: 100%;
    max-height: 300px;
    object-fit: cover;
    margin-top: 20px;
    border-radius: 8px;
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