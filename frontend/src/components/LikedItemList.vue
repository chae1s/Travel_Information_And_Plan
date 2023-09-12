<template>
    <div class="sidebar_main">
        <div class="main_title">관심등록 여행지</div>
        <ul class="item_list">
            <li class="item" v-for="i in 20" >
                <a @click="$router.push('/schedules')">
                    <v-img src="@/assets/images/site_1.jpg" alt="" height="126px"  class="rounded-lg" cover/>
                    <div class="item_info">
                        <div class="item_text">
                            <div class="item_title">안반데기</div>
                            <p class="item_fullAddress">주소</p>
                        </div>
                    </div>
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "LikedItemList"
}
</script>

<style scoped>
    .sidebar_main {
        width: 937px;
    }

    .main_title {
        font-size: 22px;
        font-weight: 700;
        text-align: left;
        padding-bottom: 20px;
    }

    .item_list {
        display: flex;
        flex-wrap: wrap;
        margin: 0 -20px;
    }

    .item {
        width: 20%;
        padding: 0 20px 23px;
        box-sizing: border-box;
    }

    .item img {
        width: 100%;
        height: 126px;
        border-radius: 8px;
    }

    .item_info {
        margin-top: 3px;
    }

    .item_text {
        box-sizing: border-box;
    }

    .item_title {
        overflow: hidden;
        text-overflow: ellipsis;
        font-size: 18px;
        font-weight: 600;
        text-align: left;
    }

    .item_fullAddress {
        text-overflow: ellipsis;
        word-wrap: normal;
        font-size: 16px;
        text-align: left;
    }

</style>