<template>
    <v-container class="finishedBackground">
        <page-title
            back-button-url="/"
            page-header-text="Meldezeitraum"
        ></page-title>
        <create-meldezeitraum></create-meldezeitraum>
    </v-container>
</template>

<script setup lang="ts">
import PageTitle from "@/components/common/PageTitle.vue";
import CreateMeldezeitraum from "@/components/Meldezeitraeume/CreateMeldezeitraum.vue";
</script>
