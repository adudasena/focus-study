import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import DashboardView from '../views/DashboardView.vue';
import SessoesView from '../views/SessoesView.vue';
import MateriaFormView from '../views/MateriaFormView.vue'; // Verifique se o nome do arquivo está idêntico

const routes = [
  { path: '/login', name: 'login', component: LoginView },
  { path: '/', name: 'dashboard', component: DashboardView },
  { path: '/sessoes', name: 'sessoes', component: SessoesView },
  { path: '/nova-materia', name: 'nova-materia', component: MateriaFormView } // Adicione esta linha
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;