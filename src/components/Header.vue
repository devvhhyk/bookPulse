<template>
  <header data-bs-theme="dark">
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand d-flex align-items-center">
          <i class="fa fa-book me-2" aria-hidden="true"></i>
          <strong>Bookpulse</strong>
        </router-link>
        <router-link to="/cart" class="cart btn">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>
    <div class="collapse text-bg-dark" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-4 py-4">
            <h4>사이트맵</h4>
            <ul class="list-unstyled">
              <li><router-link to="/" class="text-white">메인화면</router-link></li>
              <li v-if="$store.state.account.id"><router-link to="/orders" class="text-white">주문내역</router-link></li>
              <li>
                <router-link to="/login" class="text-white" v-if="!$store.state.account.id">로그인</router-link>
                <router-link to="/login" class="text-white" @click="logout()" v-else>로그아웃</router-link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import store from '@/scripts/store';
import router from '@/scripts/router';
import axios from 'axios';

export default {
  name: 'Header',
  setup() {
    const logout = () => {
      axios.post("/api/account/logout").then(() => {
        store.commit('setAccount', 0);
        router.push({ path: "/" });
      });
    };

    return { logout };
  }
}
</script>

<style scoped>
.navbar {
  padding: 10px 20px;
  background-color: #343a40;
  transition: background-color 0.3s ease;
}

.navbar:hover {
  background-color: #2c2f33;
}

.navbar-brand {
  font-size: 1.5rem;
  color: #fff;
  transition: color 0.3s ease;
}

.navbar-brand:hover {
  color: #f8f9fa;
  text-decoration: none;
}

.navbar .cart {
  margin-left: auto;
  color: #fff;
  font-size: 1.2rem;
  position: relative;
}

.navbar .cart i {
  transition: color 0.3s ease;
}

.navbar .cart:hover i {
  color: #f8f9fa;
}

#navbarHeader {
  background-color: #23272b;
  padding: 20px;
}

#navbarHeader h4 {
  color: #f8f9fa;
  margin-bottom: 20px;
}

#navbarHeader ul {
  padding-left: 0;
}

#navbarHeader ul li {
  margin-bottom: 10px;
}

#navbarHeader ul li a {
  color: #adb5bd;
  text-decoration: none;
  transition: color 0.3s ease;
}

#navbarHeader ul li a:hover {
  color: #fff;
}

.navbar-toggler {
  border-color: rgba(255, 255, 255, 0.1);
}

.navbar-toggler:hover {
  border-color: rgba(255, 255, 255, 0.3);
}

.navbar-toggler-icon {
  color: #f8f9fa;
}

@media (max-width: 768px) {
  .navbar-brand {
    font-size: 1.2rem;
  }

  .navbar .cart {
    font-size: 1rem;
  }
}
</style>
