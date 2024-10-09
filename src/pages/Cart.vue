<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(item, idx) in state.items" :key="idx">
          <img :src="item.imgPath" alt="Book Image" />
          <div class="item-details">
            <span class="title">{{ item.title }}</span>
            <span class="author">{{ item.author }}</span>
            <div class="quantity-controls">
              <button class="btn btn-outline-secondary" @click="decreaseQuantity(item)">-</button>
              <span class="quantity">{{ item.quantity }}</span>
              <button class="btn btn-outline-secondary" @click="increaseQuantity(item)">+</button>
            </div>
          </div>
          <span class="price">{{ lib.getNumberFormatted(item.price * item.quantity) }}원</span>
          <i class="fa fa-trash" @click="remove(item.id)"></i>
        </li>
      </ul>
      <router-link to="/order" class="btn btn-primary">구입하기</router-link>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";
import lib from "@/scripts/lib";

export default {
  setup() {
    const state = reactive({
      items: []
    });

    // 장바구니 항목 불러오기
    const load = () => {
      axios
        .get("/api/cart/items")
        .then(({ data }) => {
          if (Array.isArray(data)) {
            state.items = data.map(item => ({
              ...item,
              price: item.price || 0,
              quantity: item.quantity || 1
            }));
          }
        })
        .catch(error => {
          console.error("Error loading cart items", error);
        });
    };

    // 수량 증가
    const increaseQuantity = item => {
      item.quantity = item.quantity + 1;
      updateQuantity(item);
    };

    // 수량 감소
    const decreaseQuantity = item => {
      if (item.quantity > 1) {
        item.quantity = item.quantity - 1;
        updateQuantity(item);
      }
    };

    // 수량 업데이트 함수
    const updateQuantity = item => {
      axios
        .put(`/api/cart/items/${item.id}`, { quantity: item.quantity })
        .then(() => {
          load();
        })
        .catch(error => {
          console.error("Error updating item quantity", error);
        });
    };

    // 장바구니에서 항목 삭제
    const remove = itemId => {
      axios
        .delete(`/api/cart/items/${itemId}`)
        .then(() => {
      console.log("Item deleted successfully");

      // 삭제된 항목만 state.items 배열에서 제거
      state.items = state.items.filter(item => item.id !== itemId);

    })
    .catch(error => {
      console.error("장바구니 항목 삭제 중 오류 발생", error);
    });
};

  load();

    return { state, lib, increaseQuantity, decreaseQuantity, remove };
  }
};
</script>

<style scoped>
.cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.cart ul li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
  padding: 15px;
}

.cart ul li img {
  width: 150px;
  height: 150px;
  object-fit: cover;
  border-radius: 5px;
}

.item-details {
  flex-grow: 1;
  margin-left: 20px;
}

.cart ul li .title {
  font-weight: bold;
}

.cart ul li .author {
  font-size: 0.9rem;
  color: #666;
  margin-left: 10px;
}

.cart ul li .price {
  font-size: 18px;
  color: #e74c3c;
  margin-right: 10px;
}

.cart ul li i {
  cursor: pointer;
  color: #e74c3c;
  font-size: 20px;
  margin-left: 10px;
}

.quantity-controls {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
}

.quantity-controls button {
  width: 30px;
  height: 30px;
  font-size: 14px;
  padding: 5px;
}

.cart .btn-primary {
  width: 200px;
  padding: 10px 15px;
  font-size: 16px;
  margin: 30px auto;
  text-align: center;
}
</style>