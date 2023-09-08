<template>
    <v-app>
        <Header/>
        <div class="app-container">
            <router-view></router-view>
        </div>
        <Footer/>
    </v-app>
</template>

<script>
import Header from "@/components/AppHeader.vue";
import Footer from "@/components/AppFooter.vue";

export default {
    name: 'App',
    components: {
        Header,
        Footer
    }
}
</script>

<style>
#app {
    font-family: Pretendard;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2F3438;
}

.content {
    width: 1170px;
    margin: 0 auto;
}

a {
    text-decoration: none;
    color: #2F3438;
    cursor: pointer;
}

* {
    padding: 0;
    margin: 0;
    font-family: Pretendard;
}

ul, li {
    list-style: none;
    margin: 0;
    padding: 0;
}

html, body {
    overflow-x: auto;
    white-space: nowrap;
}

input:focus {
    outline: none;
}

</style>
