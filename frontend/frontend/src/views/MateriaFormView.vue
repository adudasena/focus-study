<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'

const nomeMateria = ref('')
//nova ref para a cor, começando com um verde padrão
const corMateria = ref('#42b983') 
const materias = ref([])
const userRole = ref(localStorage.getItem('usuario_role')); //role do storage

async function carregarMaterias() {
    try {
        const response = await api.get('/materias')
        materias.value = response.data 
    } catch (e) {
        console.error("Erro ao buscar", e)
    }
}

async function salvarMateria() {
    if (!nomeMateria.value) return alert("Digite o nome!")

    try {
        await api.post('/materias/novo', { 
            name: nomeMateria.value,
            color: corMateria.value 
        })
        
        //limpa os campos
        nomeMateria.value = '' 
        corMateria.value = '#42b983' 

        // atualiza a lista na tela na hora
        await carregarMaterias() 
        
        // avisa que deu certo
        alert("Matéria salva com sucesso! 🎉")
    } catch (e) {
        console.error(e)
        alert("Erro ao salvar! Verifique o console.")
    }
}

onMounted(carregarMaterias)
</script>

<template>
    <div class="materia-container">
        <h1>📚 Gerenciar Matérias</h1>

        <div class="card-form">
            <input v-model="nomeMateria" placeholder="Ex: Java, Spring..." class="input-text" />
            
            <div class="color-picker-group">
                <label>Cor no gráfico:</label>
                <input type="color" v-model="corMateria" class="input-color" />
            </div>

            <button @click="salvarMateria" class="btn-adicionar">Adicionar</button>
        </div>

        <div class="lista-materias">
            <div v-for="m in materias" :key="m.id" class="materia-item">
                <span class="cor-indicador" :style="{ backgroundColor: m.color }"></span>
                <span class="materia-nome">{{ m.name }}</span>

                <button v-if="userRole === 'ADMIN'" @click="editarMateria(m)" class="btn-editar">
                    ✏️
                </button>
                
            </div>
        </div>
    </div>
</template>

<style scoped>
.materia-container { 
  max-width: 450px; margin: 0 auto; padding: 20px; color: #333; font-family: sans-serif; }
h1 { 
  text-align: center; margin-bottom: 20px; }
.card-form { 
  display: flex; flex-direction: column; gap: 15px; background: #f9f9f9; padding: 20px; border-radius: 8px; border: 1px solid #ddd; margin-bottom: 20px; }
.input-text {
   padding: 12px; border: 1px solid #ccc; border-radius: 4px; color: #000; font-size: 1rem; }


.color-picker-group { 
  display: flex; align-items: center; gap: 10px; justify-content: space-between; }
.color-picker-group label { 
  font-weight: bold; color: #555; }
.input-color { 
  border: none; width: 40px; height: 40px; cursor: pointer; background: none; }

.btn-adicionar {
   padding: 12px; background: #42b983; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 1.1rem; }
.materia-item { 
  display: flex; align-items: center; gap: 15px; padding: 15px; border-bottom: 1px solid #eee; background: #fff; border-radius: 4px; margin-bottom: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
.materia-nome { 
  color: #000 !important; font-weight: bold; font-size: 1.1rem; }


.cor-indicador { 
  width: 18px; height: 18px; border-radius: 50%; border: 1px solid rgba(0,0,0,0.2); }
</style>