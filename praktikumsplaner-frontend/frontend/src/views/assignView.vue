<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Zuweisung"
        ></page-title>
        <v-row>
            <v-col cols="4">
                <active-nwk-list-for-zuweisung />
            </v-col>
            <v-divider vertical />
            <v-col cols="8">
                <praktikumsstellen-list />
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import ActiveNwkListForZuweisung from "@/components/ActiveNwkListForZuweisung.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
</script>