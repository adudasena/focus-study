<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api';

const router = useRouter();

//modal de cadastro
const exibirModal = ref(false);

const novoUsuario = ref({ 
    nome: '', 
    username: '', 
    password: '' 
});

//objeto de Login
const usuario = ref({
    username: '',
    password: ''
});

const erro = ref('');

const realizarCadastro = async () => {
    try {
        //chamada para o endpoint de cadastro no back
        await api.post('/auth/register', novoUsuario.value);
        alert("Cadastro realizado com sucesso! Agora faça login.");
        exibirModal.value = false; // Fecha o card
        
        //limpa os campos do cadastro para a próxima vez
        novoUsuario.value = { nome: '', username: '', password: '' };
    } catch (error) {
        console.error("Erro ao cadastrar:", error);
        alert("Erro ao criar conta. Verifique se o usuário já existe ou se o Java está rodando.");
    }
};

const realizarLogin = async () => {
    try {
        erro.value = '';
        const response = await api.post('/auth/login', usuario.value);
        
        //salva o token JWT
        localStorage.setItem('token', response.data.token);
        
        localStorage.setItem('usuarioNome', response.data.nome || usuario.value.username);

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
            placeholder="Ex: nome.sobrenome" 
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

      <p @click="exibirModal = true" class="link-cadastro">
        Não tem conta? Cadastre-se aqui.
      </p>
    </div>

    <div v-if="exibirModal" class="modal-overlay">
      <div class="modal-card">
        <h2>Criar Nova Conta</h2>
        
        <div class="input-group">
          <label>Nome Completo:</label>
          <input v-model="novoUsuario.nome" placeholder="Seu nome" required />
        </div>

        <div class="input-group">
          <label>Usuário (Login):</label>
          <input v-model="novoUsuario.username" placeholder="Ex: maria_ruiz" required />
        </div>

        <div class="input-group">
          <label>Senha:</label>
          <input v-model="novoUsuario.password" type="password" placeholder="******" required />
        </div>
        
        <div class="modal-actions">
          <button @click="realizarCadastro" class="btn-confirmar">Cadastrar</button>
          <button @click="exibirModal = false" class="btn-cancelar">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column; 
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
  background-color: white;
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

.link-cadastro {
  cursor: pointer;
  color: #42b983;
  margin-top: 15px;
  font-size: 0.9rem;
  text-decoration: underline;
}

.error-message {
  color: red;
  margin-top: 1rem;
}

/* Estilos do Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.3);
  width: 90%;
  max-width: 350px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.modal-actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.btn-confirmar {
  background-color: #42b983;
}

.btn-cancelar {
  background-color: #6c757d;
}
</style>