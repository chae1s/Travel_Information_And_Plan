<template>

</template>

<script>

export default {
    name: "LogoutView",
    created() {
        this.$store.commit('logout');
        alert("로그아웃 되었습니다.")
        console.log("localStorage token 삭제:", localStorage.getItem('token'))
        console.log("state token 삭제:", this.$store.state)
        this.goToMain()
        },

    methods: {
        goToMain() {
            console.log("goToMain: Home으로 이동")
            this.$router.push({
                name: "Home"
            })
        }
    }
}

</script>

<style scoped>

</style>