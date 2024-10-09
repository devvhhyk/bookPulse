<template>
  <div class="form-signup w-100 m-auto">
    <h1 class="h3 mb-3 fw-normal">회원가입</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingName" placeholder="Your Name"
             @keyup.enter="submit()" v-model="state.form.name">
      <label for="floatingName">이름</label>
    </div>

    <div class="form-floating">
      <input type="email" class="form-control" id="floatingEmail" placeholder="name@example.com"
             @keyup.enter="submit()" v-model="state.form.email">
      <label for="floatingEmail">이메일 주소</label>
      <button class="btn btn-secondary mt-2" @click="checkEmail">중복 확인</button>
    </div>

    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
             @keyup.enter="submit()" v-model="state.form.password">
      <label for="floatingPassword">비밀번호</label>
    </div>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingPhone" placeholder="01012345678"
             @keyup.enter="submit()" v-model="state.form.phoneNumber">
      <label for="floatingPhone">휴대폰 번호</label>
    </div>

    <button class="btn btn-primary w-100 py-2 mt-3" @click="submit()">회원가입</button>
  </div>
</template>

<script>
import { reactive } from 'vue';
import axios from "axios";
import router from '@/scripts/router';

export default {
  setup() {
    const state = reactive({
      form: {
        name: "",
        email: "",
        password: "",
        phoneNumber: ""
      },
      emailChecked: false // 이메일 중복 확인 여부
    });

    // 이메일 중복 확인
    const checkEmail = () => {
      if (!state.form.email) {
        window.alert("이메일을 입력하세요.");
        return;
      }

      axios.post("/api/account/check-email", { email: state.form.email }).then((res) => {
        if (res.data.exists) {
          window.alert("이미 사용 중인 이메일입니다.");
          state.emailChecked = false;
        } else {
          window.alert("사용 가능한 이메일입니다.");
          state.emailChecked = true;
        }
      }).catch(() => {
        window.alert("이메일 확인 중 오류가 발생했습니다.");
      });
    };

    // 입력값 유효성 검사
    const validate = () => {
      if (!state.form.name) {
        window.alert("이름을 입력하세요.");
        return false;
      }

      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!state.form.email) {
        window.alert("이메일을 입력하세요.");
        return false;
      } else if (!emailPattern.test(state.form.email)) {
        window.alert("유효한 이메일 주소를 입력하세요.");
        return false;
      }

      if (!state.emailChecked) {
        window.alert("이메일 중복 확인을 해주세요.");
        return false;
      }

      if (!state.form.password) {
        window.alert("비밀번호를 입력하세요.");
        return false;
      } else if (state.form.password.length < 8) {
        window.alert("비밀번호는 8자리 이상이어야 합니다.");
        return false;
      }

      const phonePattern = /^010\d{8}$/;
      if (!state.form.phoneNumber) {
        window.alert("휴대폰 번호를 입력하세요.");
        return false;
      } else if (!phonePattern.test(state.form.phoneNumber)) {
        window.alert("휴대폰 번호 형식이 올바르지 않습니다. (예: 01012345678)");
        return false;
      }

      return true;
    };

    // 회원가입 요청
    const submit = () => {
      if (validate()) {
        axios.post("/api/account/register", state.form).then(() => {
          window.alert("회원가입이 완료되었습니다.");
          router.push({ path: "/login" });
        }).catch(() => {
          window.alert("회원가입에 실패하였습니다.");
        });
      }
    };

    return { state, checkEmail, submit };
  }
};
</script>

<style scoped>
.form-signup {
  max-width: 330px;
  padding: 1rem;
}

.form-signup .form-floating:focus-within {
  z-index: 2;
}

.form-signup input[type="text"],
.form-signup input[type="email"],
.form-signup input[type="password"] {
  margin-bottom: 10px;
}
</style>
