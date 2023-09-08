<template>
    <div class="sidebar">
        <div class="sidebar_title">
            {{ sidebarTitle }}
        </div>
        <div class="sidebar_menu">
            <router-link :to="sidebarRouter[i]" v-for="(subTitle, i) in sidebarList" :key="subTitle" active-class="selected">{{ subTitle }}</router-link>
        </div>
    </div>
</template>

<script>
export default {
    name: "AppSideMenu",
    props: [
        'sidebarTitle', 'sidebarList', 'sidebarRouter', 'sidebarActive'
    ],

}
</script>

<style scoped>
    .sidebar {
        width: 138px;
    }

    .sidebar_title {
        font-size: 19px;
        font-weight: 700;
        text-align: left;
        padding-bottom: 8px;
    }

    .sidebar_menu {
        font-size: 16px;
        display: flex;
        flex-direction: column;
        text-align: left;
    }

    .selected {
        font-weight: bold;
        color: #99C7FF;
    }
</style>