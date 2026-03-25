import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css' // Se o arquivo estiver vazio, tudo bem

const app = createApp(App)
app.use(router)
app.mount('#app')