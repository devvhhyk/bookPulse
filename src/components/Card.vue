<template>
  <div class="card shadow-sm">
    <div class="img" :style="{backgroundImage: `url(${item.imgPath})`}" @click="goToDetail(item.id)"></div>
    <div class="card-body">
      <h5 class="card-title">{{ item.title }}</h5>
      <p class="card-author text-muted">저자: {{ item.author }}</p>
      <p class="card-publisher text-muted">출판사: {{ item.publisher }}</p>
      
      <div class="d-flex justify-content-between align-items-center mt-3">
        <div>
          <small class="price text-muted">
            {{ lib.getNumberFormatted(item.price) }}원
          </small>
        </div>
        <button class="btn btn-primary" @click="addToCart(item.id)">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import lib from "@/scripts/lib";
import axios from "axios";
import { useRouter } from 'vue-router';

export default {
  name: "Card",
  props: {
    item: Object
  },
  setup() {
    const router = useRouter();

    const addToCart = (itemId, quantity = 1) => {
      axios.post(`/api/cart/items/${itemId}`, { quantity })
        .then(() => alert("상품이 장바구니에 추가되었습니다."))
        .catch(() => alert("장바구니 추가 중 오류가 발생했습니다."));
    };

    const goToDetail = (itemId) => router.push(`/item/${itemId}`);

    return { lib, addToCart, goToDetail };
  }
}
</script>

<style scoped>
.card {
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}

.card:hover {
  transform: scale(1.05);
}

.card .img {
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body {
  padding: 15px;
}

.card .card-title {
  font-size: 1.5rem;
  font-weight: bold;
}

.card .card-author, .card .card-publisher {
  font-size: 0.9rem;
}

.card .price {
  font-weight: bold;
  color: #e74c3c;
}
</style>
