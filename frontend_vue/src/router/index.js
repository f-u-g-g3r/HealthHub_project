import { createRouter, createWebHistory } from 'vue-router'
import TestView from '../components/Test.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: TestView
    }
  ]
})

export default router
