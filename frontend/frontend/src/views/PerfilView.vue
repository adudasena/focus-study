<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const nomeUsuario = ref('Convidado') // Valor padrão

onMounted(() => {
  // Pega o nome que salvamos no login
  const nomeSalvo = localStorage.getItem('usuarioNome')
  if (nomeSalvo) {
    nomeUsuario.value = nomeSalvo
  }
})

const realizarLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('usuarioNome') // Limpa o nome também
  router.push('/login')
}
</script>

<template>
  <div class="perfil-container">
    <h1>👤 Meu Perfil</h1>
    <p>Bem-vindo(a), {{ nomeUsuario }}! Este é seu painel.</p>
    
    <div class="card-usuario">
      <h3>Dados da Conta</h3>
      <p><strong>Usuário:</strong> {{ nomeUsuario }}</p> 
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
</style>