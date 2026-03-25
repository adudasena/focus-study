<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api';
const router = useRouter();

// Estado reativo para o formulário (v-model)
const usuario = ref({
    username: '',
    password: ''
});

const erro = ref('');

const realizarLogin = async () => {
    try {
        erro.value = '';
        // Faz a chamada para o seu Backend
        const response = await api.post('/auth/login', usuario.value);
        
        // Se der certo, salva o token no localStorage
        localStorage.setItem('token', response.data.token);
        
        // Navegação programática para a Dashboard
        router.push('/');
    } catch (e) {
        erro.value = 'Usuário ou senha inválidos. Tente novamente.';
        console.error(e);
    }
};
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <h1>FocusStudy</h1>
      <p>Faça login para gerir os seus estudos</p>

      <form @submit.prevent="realizarLogin">
        <div class="input-group">
          <label>Utilizador:</label>
          <input 
            v-model="usuario.username" 
            type="text" 
            placeholder="Ex: joão" 
            required 
          />
        </div>

        <div class="input-group">
          <label>Senha:</label>
          <input 
            v-model="usuario.password" 
            type="password" 
            placeholder="******" 
            required 
          />
        </div>

        <button type="submit">Entrar</button>
      </form>

      <p v-if="erro" class="error-message">{{ erro }}</p>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
}
.login-card {
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}
.input-group {
  margin-bottom: 1rem;
  text-align: left;
}
input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}
button:hover {
  background-color: #3aa876;
}
.error-message {
  color: red;
  margin-top: 1rem;
}
</style>