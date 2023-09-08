<template>
    <v-container>
        <div class="content">
            <div class="userInfo-container">
                <div class="left-content">
                    <nav>
                        <div class="nav_content mt-5">
                            <h4>나의 여행</h4>
                            <router-link to="/" ></router-link>
                        </div>
                    </nav>
                </div>
                <div class="router-area">
                    <router-view></router-view>
                </div>
            </div>
        </div>
    </v-container>
</template>

<script>
export default {
    name: "UserInfoView"
}
</script>

<style scoped>
.left-content {
    flex: 0 0 300px;
    flex-direction: column;
    background-color: #f2f2f2;
    align-items: center;
}
.nav_content{
    text-align: left;
    margin-left: 30px;
}
</style>