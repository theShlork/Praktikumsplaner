import mitt from "mitt";
import { createApp } from "vue";

import { APP_SECURITY } from "@/Constants";
import { registerPlugins } from "@/plugins";
import { useUserStore } from "@/stores/user";
import App from "./App.vue";

const emitter = mitt();
const app = createApp(App);

registerPlugins(app);

app.directive("security", (el, binding, vnode) => {
    if (APP_SECURITY !== "true") return;
    const userStore = useUserStore();

    const requiredRole = binding.value;

    const nodeElement = vnode.el;
    if (nodeElement) {
        (nodeElement as HTMLElement).style.display = "none";
    }
    const newRoles = userStore.getRoles;
    if (!nodeElement) return;
    if (
        (binding.modifiers.allow &&
            newRoles.some((role) => role == requiredRole)) ||
        (binding.modifiers.restrict &&
            !newRoles.some((role) => role == requiredRole))
    ) {
        (nodeElement as HTMLElement).style.display = "";
    } else {
        (nodeElement as HTMLElement).style.display = "none";
    }
});

app.config.globalProperties.emitter = emitter;
app.mount("#app");
