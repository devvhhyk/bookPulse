import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/pages/Home';
import Login from '@/pages/Login';
import Cart from '@/pages/Cart';

const routes = [
  {path:'/', component: Home}, // 홈 페이지 라우팅
  {path:'/login', component: Login}, // 로그인 페이지 라우팅
  {path:'/cart', component: Cart}
]

const router = createRouter({
  history: createWebHistory(), // 브라우저 히스토리 모드 사용
  routes
})

export default router;