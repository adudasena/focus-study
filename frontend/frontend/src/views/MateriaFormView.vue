<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'

const nomeMateria = ref('')
const corMateria = ref('#42b983') 
const materias = ref([])
// Pegando a role de forma reativa
const userRole = ref(localStorage.getItem('usuario_role')); 

const exibirModalEdicao = ref(false);
const materiaSendoEditada = ref({ id: null, name: '', color: '' });

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
        nomeMateria.value = '' 
        corMateria.value = '#42b983' 
        await carregarMaterias() 
        alert("Matéria salva com sucesso! 🎉")
    } catch (e) {
        alert("Erro ao salvar! Verifique se o Java está rodando.");
    }
}

function abrirEdicao(materia) {
    // Usando spread operator (...) para criar uma cópia e não editar a lista original por acidente
    materiaSendoEditada.value = { ...materia }; 
    exibirModalEdicao.value = true;
}

async function salvarEdicao() {
    try {
        // Chamada PUT para atualizar
        await api.put(`/materias/${materiaSendoEditada.value.id}`, {
            name: materiaSendoEditada.value.name,
            color: materiaSendoEditada.value.color
        });
        
        alert("Matéria atualizada!");
        exibirModalEdicao.value = false;
        await carregarMaterias(); 
    } catch (e) {
        alert("Erro ao editar matéria.");
    }
}

async function deletarMateria(id) {
    if (confirm("Tem certeza que deseja excluir esta matéria? Isso pode apagar sessões ligadas a ela!")) {
        try {
            await api.delete(`/materias/${id}`);
            alert("Matéria excluída!");
            await carregarMaterias(); 
        } catch (e) {
            alert("Erro ao excluir. Verifique se existem sessões de estudo para esta matéria no banco.");
        }
    }
}

onMounted(() => {
    carregarMaterias();
    // Atualiza a role caso tenha mudado sem dar refresh
    userRole.value = localStorage.getItem('usuario_role');
});
</script>

<template>
  <div class="gerenciar-materias-wrapper">
    
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

                <div v-if="userRole === 'ADMIN'" class="admin-actions">
                  <button class="btn-editar" @click="abrirEdicao(m)">✏️ Editar</button>
                  <button class="btn-excluir" @click="deletarMateria(m.id)">🗑️ Excluir</button>
                </div> 
            </div>
        </div>
    </div>

    <div v-if="exibirModalEdicao" class="modal-overlay">
        <div class="modal-card">
            <h2>Editar Matéria</h2>
            
            <div class="input-group">
                <label>Nome da Matéria:</label>
                <input v-model="materiaSendoEditada.name" class="input-text" />
            </div>

            <div class="input-group">
                <label>Cor Identificadora:</label>
                <input type="color" v-model="materiaSendoEditada.color" class="input-color" />
            </div>
            
            <div class="modal-actions">
                <button @click="salvarEdicao" class="btn-adicionar">Salvar Alterações</button>
                <button @click="exibirModalEdicao = false" class="btn-cancelar">Cancelar</button>
            </div>
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
.admin-actions {
    margin-top: 10px;
    display: flex;
    gap: 8px;
    justify-content: center;
}

.btn-editar { background-color: #e7d59e; color: #212529;  border: none;  padding: 6px 12px;  border-radius: 4px;  cursor: pointer;  font-weight: bold;  font-size: 0.8rem;
}

.btn-excluir {  background-color: #bf2020;  color: white;  border: none;  padding: 6px 12px;  border-radius: 4px;  cursor: pointer;  font-weight: bold;  font-size: 0.8rem;
}

.btn-editar:hover { background-color: #e0a800; }
.btn-excluir:hover { background-color: #cc0000; }

.modal-overlay {
    position: fixed;
    top: 0; left: 0; width: 100%; height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex; justify-content: center; align-items: center;
    z-index: 1000;
}
.modal-card {
    background: white; padding: 25px; border-radius: 12px;
    width: 90%; max-width: 350px; display: flex; flex-direction: column; gap: 15px;
}
.btn-cancelar {
    background: #6c757d; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer;
}

</style>