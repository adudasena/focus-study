<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

//pega nome e a role do storage para mostrar no perfil e controlar acesso
const nomeUsuario = ref(localStorage.getItem('usuarioNome') || 'Usuário')
const userRole = ref(localStorage.getItem('usuario_role'))

//lógica do nome
const labelRole = computed(() => {
  if (userRole.value === 'ADMIN') return 'Administrador'
  if (userRole.value === 'USER') return 'Estudante'
  return 'Visitante'
})

const realizarLogout = () => {
  localStorage.clear() // Limpa TUDO de uma vez
  router.push('/login')
}
</script>

<template>
  <div class="perfil-container">
    <h1>👤 Meu Perfil</h1>
    <p>Bem-vindo(a), {{ nomeUsuario }}! Este é seu painel.</p>
    
    <div class="card-usuario">
      <h3>Dados da Conta</h3>
      <p><strong>Nome:</strong> {{ nomeUsuario }}</p> 
      <p><strong>Nível de Acesso:</strong> 
        <span :class="userRole === 'ADMIN' ? 'txt-admin' : 'txt-user'">
          {{ labelRole }}
        </span>
      </p>
    </div>

    <button @click="realizarLogout" class="btn-sair">Encerrar Sessão</button>
  </div>
</template>

<style scoped>
.perfil-container {
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
}
.card-usuario {
  background: #fdfdfd;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin: 20px 0;
}
.btn-sair {
  background-color: #ff4d4d;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}
.btn-sair:hover {
  background-color: #cc0000;
}

.txt-admin { color: #e74c3c; font-weight: bold; } /* Vermelho para Admin */
.txt-user { color: #2ecc71; font-weight: bold; }  /* Verde para Estudante */
.btn-editar { margin-left: auto; cursor: pointer; background: none; border: none; }
</style>