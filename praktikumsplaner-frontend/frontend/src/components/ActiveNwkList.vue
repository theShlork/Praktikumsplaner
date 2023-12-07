<template>
    <v-container>
        <v-col
            v-for="nwk in nwks"
            :key="nwk.id"
        >
            <v-card color="accent">
                <v-card-title class="text-center"
                    >{{ nwk.vorname }} {{ nwk.nachname }}</v-card-title
                >
                <v-card-subtitle class="text-center"
                    >({{ nwk.studiengang }} /
                    {{ nwk.jahrgang }})</v-card-subtitle
                >
            </v-card>
        </v-col>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import NwkService from "@/api/NwkService";
import Nwk from "@/types/Nwk";

const nwks = ref<Nwk[]>([]);

onMounted(() => {
    getAllActiveNwks();
});
function getAllActiveNwks() {
    NwkService.getAllActiveNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}
</script>
<style></style>
