<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>번호</th>
            <th>주문자명</th>
            <th>주소</th>
            <th>결제 수단</th>
            <th>구입 항목</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(o, idx1) in state.orders" :key="idx1">
            <td>{{state.orders.length - idx1}}</td>
            <td>{{o.name}}</td>
            <td>{{o.address}}</td>
            <td>{{o.payment}}</td>
            <td>
              <!-- 구입 항목 출력 -->
              <div v-for="(i, idx2) in o.items" :key="idx2">{{i.title}}</div>
            </td>
          </tr>
        </tbody>
      </table>      
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";

export default {
  setup() {
    const state = reactive({
      orders: []
    });

    // 주문 데이터 가져오기
    axios.get("/api/orders").then(({data}) => {
      console.log(data); // 서버에서 받은 데이터 확인
      state.orders = [];

      // 주문 목록을 반복 처리하면서 items를 확인
      for(let d of data) {
        if(d.items) {
          // items가 문자열로 넘어온다면 JSON.parse를 사용
          try {
            d.items = typeof d.items === 'string' ? JSON.parse(d.items) : d.items;
          } catch(e) {
            console.error('Error parsing items:', e);
          }
        }
        state.orders.push(d);
      }
    }).catch((error) => {
      console.error("Error loading orders", error);
    });

    return { state };
  },
};
</script>

<style scoped>
table {
  margin-top: 30px;
}

table tbody {
  border-top: 1px solid #eee;
}
</style>