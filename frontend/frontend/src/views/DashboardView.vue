<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'

ChartJS.register(ArcElement, Tooltip)

const carregando = ref(true)
const dadosGrafico = ref(null)
const materiasComTempo = ref([]) //lista para a legenda personalizada

//busca as matérias coloridas e o tempo total 
const carregarDados = async () => {
  try {
    const res = await api.get('/sessoes/estatisticas')
    
    const temposParaGrafico = res.data.map(item => item.segundosTotais / 60)

    materiasComTempo.value = res.data.map(item => {
        const s = item.segundosTotais;
        const min = Math.floor(s / 60);
        const seg = s % 60;
        
        return {
            name: item.nome,
            color: item.cor,
            // Formatação: se for menos de 1 min, mostra só segundos
            formattedTime: min > 0 ? `${min}min ${seg}s` : `${seg}s`
        }
    })

    dadosGrafico.value = {
      labels: res.data.map(item => item.nome),
      datasets: [{
        backgroundColor: res.data.map(item => item.cor),
        data: temposParaGrafico
      }]
    }
    carregando.value = false
  } catch (e) { console.error(e) }
}

const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
            display: false
        },
        tooltip: {
            callbacks: {
          //mostrar nome e tem
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