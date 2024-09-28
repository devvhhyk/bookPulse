<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(i, idx) in state.items" :key="idx">
          <img :src="i.imgPath"/>
          {{i.name}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue';
import axios from "axios";

export default {
  setup() {
    const state = reactive({
      items:[]
    })

    axios.get("/api/cart/items").then(({data}) => {
      console.log(data);
      state.items = data;
    })
  }
}
</script>

<style scoped>

</style>