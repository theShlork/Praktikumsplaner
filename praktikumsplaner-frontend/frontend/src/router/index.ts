// Composables
import { createRouter, createWebHistory } from "vue-router";

import assignView from "@/views/assignView.vue";
import Main from "@/views/MainView.vue";
import Meldezeitraeume from "@/views/MeldezeitraeumeView.vue";
import MeldungAusbildung from "@/views/MeldungPraktikumsstelle/MeldungAusbildung.vue";
import MeldungStart from "@/views/MeldungPraktikumsstelle/MeldungStart.vue";
import MeldungStudium from "@/views/MeldungPraktikumsstelle/MeldungStudium.vue";
import MeldungSuperNwk from "@/views/MeldungPraktikumsstelle/MeldungSuperNwk.vue";
import NachwuchskraefteView from "@/views/Nachwuchskraefte/NachwuchskraefteView.vue";

const routes = [
    {
        path: "/",
        name: "home",
        component: Main,
        meta: {},
    },
    {
        path: "/nachwuchskraefte",
        name: "nachwuchskraefte",
        component: NachwuchskraefteView,
    },
    {
        path: "/meldezeitraum",
        name: "meldezeitraum",
        component: Meldezeitraeume,
    },
    {
        path: "/meldungAusbilder",
        name: "meldungOertlAusbilder",
        component: MeldungStart,
    },
    {
        path: "/meldungAusbildung",
        name: "MeldungAusbildung",
        component: MeldungAusbildung,
    },
    {
        path: "/meldungStudium",
        name: "MeldungStudium",
        component: MeldungStudium,
    },
    {
        path: "/superNwk",
        name: "SuperNwk",
        component: MeldungSuperNwk,
    },
    {
        path: "/zuweisung",
        name: "Zuweisung",
        component: assignView,
    },
    { path: "/:catchAll(.*)*", redirect: "/" }, // CatchAll route
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
