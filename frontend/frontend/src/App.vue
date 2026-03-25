<script setup>
import { RouterLink, RouterView, useRoute } from 'vue-router'
import api from '@/api';

const route = useRoute()
</script>

<template>
  <header>
    <nav v-if="route.path !== '/login'">
      <div class="wrapper">
        <RouterLink to="/">Dashboard</RouterLink>
        <RouterLink to="/sessoes">Minhas Sessões</RouterLink>
        <RouterLink to="/nova-materia">Nova Matéria</RouterLink>
        <a href="#" @click="localStorage.removeItem('token'); $router.push('/login')">Sair</a>
      </div>
    </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
/* Estilo simples para navegação */
nav {
  width: 100%;
  padding: 1rem;
  background-color: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
  margin-bottom: 2rem;
}

.wrapper {
  display: flex;
  gap: 20px;
  justify-content: center;
}

nav a {
  text-decoration: none;
  color: #333;
  font-weight: bold;
}

nav a.router-link-exact-active {
  color: #42b983; /* Cor verde padrão do Vue para link ativo */
}

main {
  padding: 0 2rem;
}
</style>