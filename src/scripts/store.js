
import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
  state () {
    return {
      account: {
        id: 0 // 로그인 상태 관리
      }
    }
  },
  mutations: { // 상태 변경 메서드
    setAccount(state, payload) { // 계정 상태를 변경하는 함수
      state.account.id = payload;
    }
  }
})

export default store;