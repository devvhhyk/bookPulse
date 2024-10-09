<template>
  <div class="home">
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in state.items" :key="idx">
            <Card :item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive } from "vue";
import Card from "@/components/Card.vue";

export default {
  name: "Home",
  components: { Card },
  setup() {
    const state = reactive({
      items: [],
    });

    // 백엔드에서 도서 목록을 가져오는 API 호출
    axios.get("/api/books")
      .then(({ data }) => {
        state.items = data;
      })
      .catch(error => {
        console.error("Error fetching books:", error);
      });

    return { state };
  },
};
</script>

<style scoped>

</style>
