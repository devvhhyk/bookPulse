import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/pages/Home';
import Login from '@/pages/Login';
import SignUp from '@/pages/SignUp';
import Cart from '@/pages/Cart';
import Order from '@/pages/Order';
import Orders from '@/pages/Orders';

const routes = [
  {path:'/', component: Home},
  {path:'/login', component: Login},
  {path: '/signup', component: SignUp},
  {path:'/cart', component: Cart},
  {path:'/order', component: Order},
  {path:'/orders', component: Orders}
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router;