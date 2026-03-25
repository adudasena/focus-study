<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'

const nomeMateria = ref('')
const materias = ref([])

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
        //rota novo materia no back
        await api.post('/materias/novo', { 
            name: nomeMateria.value 
        })
        
        nomeMateria.value = '' 
        carregarMaterias() 
    } catch (e) {
        alert("Erro ao salvar! Verifique o console ou se o Java está ok.")
    }
}

onMounted(carregarMaterias)
</script>

<template>
    <div class="materia-container">
        <h1>📚 Gerenciar Matérias</h1>

        <div class="card-form">
            <input v-model="nomeMateria" placeholder="Ex: Java, Spring..." class="input-text" />
            <button @click="salvarMateria" class="btn-adicionar">Adicionar</button>
        </div>

        <div class="lista-materias">
            <div v-for="m in materias" :key="m.id" class="materia-item">
                <span class="materia-nome">{{ m.name }}</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* Estilos simplificados para não brigar com o seu main.css */
.materia-container { max-width: 400px; margin: 0 auto; padding: 20px; color: #333; }
.card-form { display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px; }
.input-text { padding: 10px; border: 1px solid #ccc; border-radius: 4px; color: #000; }
.btn-adicionar { padding: 10px; background: #42b983; color: white; border: none; cursor: pointer; font-weight: bold; }
.materia-item { padding: 12px; border-bottom: 1px solid #eee; background: #fff; }
.materia-nome { color: #000 !important; font-weight: bold; }
</style>