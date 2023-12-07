import mitt from "mitt";
import { createApp } from "vue";

import { registerPlugins } from "@/plugins";
import App from "./App.vue";

const emitter = mitt();
const app = createApp(App);

registerPlugins(app);

app.config.globalProperties.emitter = emitter;
app.mount("#app");
