<template>
    <v-app>
        <the-snackbar />
        <v-app-bar color="primary">
            <v-row align="center">
                <v-col
                    cols="3"
                    class="d-flex align-center justify-start"
                >
                    <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
                    <router-link to="/">
                        <v-toolbar-title class="text--white">
                            {{ header }}</v-toolbar-title
                        >
                    </router-link>
                </v-col>
                <v-col
                    cols="6"
                    class="d-flex align-center justify-center"
                >
                </v-col>
                <v-col
                    cols="1"
                    class="d-flex align-center justify-end"
                >
                </v-col>
            </v-row>
        </v-app-bar>
        <v-navigation-drawer v-model="drawer">
            <v-list>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/nachwuchskraefte' }"
                >
                    <v-list-item-title>Nachwuchskräfte</v-list-item-title>
                </v-list-item>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/meldezeitraum' }"
                >
                    <v-list-item-title>Meldezeitraum</v-list-item-title>
                </v-list-item>
                <v-list-item
                    v-security.restrict="['ROLE_NWK']"
                    :to="{ path: '/meldungAusbilder' }"
                >
                    <v-list-item-title
                        >Praktikumsstellen Meldung</v-list-item-title
                    >
                </v-list-item>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/zuweisung' }"
                >
                    <v-list-item-title>Zuweisung</v-list-item-title>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-main>
            <v-container fluid>
                <router-view v-slot="{ Component }">
                    <v-fade-transition mode="out-in">
                        <component :is="Component" />
                    </v-fade-transition>
                </router-view>
            </v-container>
        </v-main>
    </v-app>
</template>

<script setup lang="ts">
import { onBeforeMount, ref, watch } from "vue";

import { UserService } from "@/api/UserService";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { useHeaderStore } from "@/stores/header";
import { useUserStore } from "@/stores/user";

const drawer = ref(true);
const headerStore = useHeaderStore();
const header = ref<string>("Praktikumsplaner");
const userService = new UserService();
const userStore = useUserStore();

onBeforeMount(() => {
    userService.getPermissions().then((userinfo) => {
        userStore.setUsername(userinfo.name);
        if (userinfo.user_roles) {
            userStore.setRoles(userinfo.user_roles);
        }
    });
});

watch(
    () => headerStore.appHeader,
    () => (header.value = headerStore.appHeader)
);
</script>

<style></style>
