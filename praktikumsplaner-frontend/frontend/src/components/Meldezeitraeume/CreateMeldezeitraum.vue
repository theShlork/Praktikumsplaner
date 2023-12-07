<template>
    <v-col>
        <v-form
            ref="form"
            class="d-flex justify-center align-center form"
        >
            <v-container>
                <v-row>
                    <v-col
                        sm="8"
                        md="4"
                    >
                        <v-text-field
                            v-model="meldezeitraum.zeitraumName"
                            label="Zeitraumname"
                            :rules="zeitraumNameRules"
                            variant="outlined"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col
                        sm="8"
                        md="4"
                    >
                        <zeitraum-picker
                            :value="meldezeitraum"
                        ></zeitraum-picker>
                    </v-col>
                </v-row>
            </v-container>
        </v-form>
        <v-btn
            variant="outlined"
            color="primary"
            class="float-md-left"
            @click="clickAbbrechen()"
        >
            Zurück
        </v-btn>
        <v-btn
            class="float-md-right"
            color="primary"
            variant="flat"
            @click="clickSpeichern()"
        >
            Speichern
        </v-btn>
    </v-col>
</template>

<script setup lang="ts">
import { ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import { useRules } from "@/composables/rules";
import router from "@/router";
import Meldezeitraum from "@/types/Meldezeitraum";

const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum(""));
const form = ref<HTMLFormElement>();
const maxLength = 255;
const validationRules = useRules();

const zeitraumNameRules = [
    validationRules.maxLengthRule(
        maxLength,
        "Der Name darf maximal " + maxLength + " Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Zeitraumname darf nicht leer sein."),
];

const emits = defineEmits<{
    (e: "meldezeitraumAdded", meldezeitraum: Meldezeitraum): void;
}>();

function resetForm() {
    meldezeitraum.value = new Meldezeitraum("");
    form.value?.resetValidation();
}

function clickSpeichern() {
    if (form.value?.validate()) {
        MeldezeitraumService.create(meldezeitraum.value)
            .then(() => {
                emits("meldezeitraumAdded", meldezeitraum.value);
            })
            .finally(() => {
                resetForm();
                router.push("/");
            });
    }
}

function clickAbbrechen() {
    resetForm();
    router.push("/");
}
</script>
