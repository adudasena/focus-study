<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'

const materias = ref([])
const materiaSelecionada = ref(null)
const segundos = ref(0)
const rodando = ref(false)
let cronometro = null

// Busca as matérias coloridas que você já criou!
async function carregarMaterias() {
    const res = await api.get('/materias')
    materias.value = res.data
}

function iniciarTimer() {
    if (!materiaSelecionada.value) return alert("Selecione uma matéria primeiro!")
    rodando.value = true
    cronometro = setInterval(() => segundos.value++, 1000)
}

function pausarTimer() {
    rodando.value = false
    clearInterval(cronometro)
}

async function finalizarESalvar() {
    pausarTimer()
    if (segundos.value < 5) return alert("Sessão muito curta!")

    const payload = {
        startTime: new Date().toISOString(),
        durationSeconds: segundos.value, //envia os segundos puros 
        materia: { id: materiaSelecionada.value },
        usuario: { id: 1 }
    }

    try {
        await api.post('/sessoes/novo', payload)
        alert("Sessão salva com precisão! 🚀")
        segundos.value = 0
    } catch (e) {
        alert("Erro ao salvar.")
    }
}

const formatarTempo = () => {
    const m = Math.floor(segundos.value / 60)
    const s = segundos.value % 60
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`
}

onMounted(carregarMaterias)
</script>

<template>
    <div class="sessoes-container">
        <h1>⏱️ Cronômetro de Estudo</h1>
        
        <div class="timer-card">
            <select v-model="materiaSelecionada" class="select-materia">
                <option :value="null">Selecione a matéria...</option>
                <option v-for="m in materias" :key="m.id" :value="m.id">
                    {{ m.name }}
                </option>
            </select>

            <div class="display-tempo">{{ formatarTempo() }}</div>

            <div class="controles">
                <button v-if="!rodando" @click="iniciarTimer" class="btn start">Iniciar</button>
                <button v-else @click="pausarTimer" class="btn pause">Pausar</button>
                <button @click="finalizarESalvar" class="btn save">Finalizar e Salvar</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.sessoes-container { max-width: 500px; margin: 40px auto; text-align: center; }
.timer-card { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
.select-materia { width: 100%; padding: 12px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #ddd; }
.display-tempo { font-size: 4rem; font-weight: bold; color: #2c3e50; margin: 20px 0; font-family: monospace; }
.controles { display: flex; gap: 10px; justify-content: center; }
.btn { padding: 12px 25px; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; transition: 0.3s; }
.start { background: #42b983; color: white; }
.pause { background: #ff9f43; color: white; }
.save { background: #3498db; color: white; }
.btn:hover { opacity: 0.8; }
</style>