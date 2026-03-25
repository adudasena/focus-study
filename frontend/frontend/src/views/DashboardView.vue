<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'

// Registramos apenas o necessário. Repare que removemos o Legend nativo daqui!
ChartJS.register(ArcElement, Tooltip)

const carregando = ref(true)
const dadosGrafico = ref(null)
const materiasComTempo = ref([]) // Lista para a legenda personalizada

// Busca as matérias coloridas e o tempo total que você salvou no Timer!
const carregarDados = async () => {
  try {
    const res = await api.get('/sessoes/estatisticas')
    
    //agora acessa .nome, .cor e .minutos em vez de item, item
    const labels = res.data.map(item => item.nome) 
    const cores = res.data.map(item => item.cor)  
    const tempos = res.data.map(item => item.minutos)

    materiasComTempo.value = res.data.map(item => ({
        name: item.nome,
        color: item.cor,
        totalMinutes: item.minutos,
        formattedTime: item.minutos < 1 
            ? `${Math.round(item.minutos * 60)} seg` 
            : `${Math.round(item.minutos)} min`
    }))

    dadosGrafico.value = {
      labels: labels, // Mantemos os nomes aqui para o Tooltip (quando passa o mouse)
      datasets: [{
        backgroundColor: cores,
        data: tempos,
        borderWidth: 2,
        borderColor: '#ffffff', // Bordinha branca entre as fatias
      }]
    }
    carregando.value = false
  } catch (e) {
    console.error("Erro no dashboard", e)
  }
}

// Configurações do Tooltip (o que aparece quando passa o mouse)
const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        // Desativamos a legenda nativa totalmente
        legend: {
            display: false
        },
        tooltip: {
            callbacks: {
                // Aqui nós dizemos: mostre o nome da matéria e o tempo em minutos
                label: function(context) {
                    let label = context.label || '';
                    if (label) {
                        label += ': ';
                    }
                    if (context.parsed !== null) {
                        label += context.parsed + ' min';
                    }
                    return label;
                }
            }
        }
    }
}

onMounted(carregarDados)
</script>

<template>
  <div class="dashboard-container">
    <div class="header-group">
        <h1>📊 Resumo de Estudos</h1>
        <p>Distribuição do tempo investido por matéria</p>
    </div>
    
    <div v-if="!carregando" class="chart-section">
      <div class="chart-wrapper">
        <Doughnut :data="dadosGrafico" :options="options" />
      </div>
      
      <div class="legenda-custom">
        <h3>Minhas Matérias</h3>
        <div v-for="(m, index) in materiasComTempo" :key="index" class="legenda-item">
            <span class="cor-indicador" :style="{ backgroundColor: m.color }"></span>
            <div class="info-legenda">
                <span class="materia-nome">{{ m.name }}</span>
                <span class="materia-tempo">{{ m.formattedTime }}</span>
            </div>
        </div>
      </div>
    </div>
    
    <div v-else class="carregando">Carregando estatísticas...</div>
  </div>
</template>

<style scoped>
.dashboard-container { max-width: 800px; margin: 40px auto; padding: 20px; font-family: 'Helvetica Neue', sans-serif; }
.header-group { text-align: center; margin-bottom: 30px; }
.header-group h1 { color: #2c3e50; font-size: 2.2rem; }
.header-group p { color: #7f8c8d; }

.chart-section { display: flex; gap: 30px; align-items: flex-start; flex-wrap: wrap; }
.chart-wrapper { flex: 1; background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); height: 350px; }

/* Estilos da Legenda Personalizada */
.legenda-custom { flex: 1; background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
.legenda-custom h3 { margin-top: 0; color: #555; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 15px; }

.legenda-item { display: flex; align-items: center; gap: 15px; padding: 10px 0; border-bottom: 1px solid #f5f5f5; }
.cor-indicador { width: 18px; height: 18px; border-radius: 50%; border: 1px solid rgba(0,0,0,0.1); }
.info-legenda { display: flex; flex-direction: column; }
.materia-nome { color: #000; font-weight: bold; font-size: 1.1rem; }
.materia-tempo { color: #42b983; font-weight: bold; } /* Usando o "moss green" que você gosta! */

.carregando { text-align: center; color: #7f8c8d; margin-top: 50px; }

@media (max-width: 600px) {
    .chart-section { flex-direction: column; }
    .chart-wrapper, .legenda-custom { width: 100%; }
}
</style>