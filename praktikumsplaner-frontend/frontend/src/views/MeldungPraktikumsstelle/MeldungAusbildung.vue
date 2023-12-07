<template>
    <v-form
        ref="form"
        lazy-validation
    >
        <h3 class="spacing-left">Praktikumstellen Meldung</h3>
        <div v-if="!activeMeldezeitraum">
            Die Ausbildungsleitung hat die Meldung von Stellen noch nicht
            freigegeben, daher können leider aktuell keine Stellen gemeldet
            werden.
        </div>
        <v-container
            v-else
            class="spacing-left, spacing-top-30"
        >
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.ausbildungsrichtung"
                        label="Ausbildungsrichtung*"
                        :items="Ausbildungsrichtung"
                        item-value="name"
                        item-title="value"
                        :rules="requiredRule"
                        variant="outlined"
                        @update:model-value="
                            () => {
                                changeVorrZuweisungsZeitraum();
                                zustelleradressverwaltung();
                            }
                        "
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.ausbildungsjahr"
                        label="Ausbildungsjahr*"
                        :items="Ausbildungsjahr"
                        item-value="name"
                        item-title="value"
                        :rules="requiredRule"
                        variant="outlined"
                        @update:model-value="
                            () => {
                                changeVorrZuweisungsZeitraum();
                                zustelleradressverwaltung();
                            }
                        "
                    >
                    </v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.dienststelle"
                        label="Konkrete Dienststelle*"
                        :rules="dienststelleRule"
                        variant="outlined"
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.referat"
                        :items="Referat"
                        item-value="name"
                        item-title="value"
                        label="Referat"
                        variant="outlined"
                        @update:model-value="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.programmierkenntnisse"
                        label="Programmierkenntnisse"
                        :items="YesNoBoolean"
                        item-value="value"
                        item-title="name"
                        variant="outlined"
                        @update:model-value="zustelleradressverwaltung()"
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.projektarbeit"
                        label="Projektarbeit*"
                        :items="YesNoBoolean"
                        item-value="value"
                        item-title="name"
                        :rules="booleanRule"
                        variant="outlined"
                        @update:model-value="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.oertlicheAusbilder"
                        label="Name örtliche Ausbilder*in*"
                        :rules="oertlAusbidlerRule"
                        variant="outlined"
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.email"
                        label="E-mail örtliche Ausbilderin*"
                        :rules="emailRule"
                        variant="outlined"
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.namentlicheAnforderung"
                        label="Namentliche Anforderung spez. gewünschter Nwk"
                        :rules="namentlicheAnforderungRule"
                        variant="outlined"
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.dringlichkeit"
                        label="Dringlichkeit"
                        :items="Dringlichkeit"
                        item-value="name"
                        item-title="value"
                        :rules="requiredRule"
                        variant="outlined"
                        @update:model-value="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="zeitraum"
                        label="Zeitraum Nwk"
                        hint="Wählen Sie Art und Jahrgang des Stuzubis aus"
                        variant="outlined"
                        disabled
                        bg-color="grey"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.planstelleVorhanden"
                        label="Planstelle"
                        :items="YesNoBoolean"
                        item-value="value"
                        item-title="name"
                        variant="outlined"
                        @update:model-value="zustelleradressverwaltung()"
                    >
                    </v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-textarea
                        v-model="praktikumsstelle.taetigkeiten"
                        label="Aufgaben am Praktikumsplatz*"
                        :rules="taetigkeitenRule"
                        variant="outlined"
                        @change="zustelleradressverwaltung()"
                    ></v-textarea>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row justify="space-between">
                <v-col>
                    <v-btn
                        color="primary"
                        variant="outlined"
                        :to="{ path: '/meldungAusbilder' }"
                    >
                        ZURÜCK
                    </v-btn>
                </v-col>
                <v-col cols="8" />
                <v-col>
                    <v-btn
                        color="primary"
                        @click="uploadPraktikumsstelle"
                    >
                        SPEICHERN
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
    </v-form>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import MeldungService from "@/api/PraktikumsstellenService";
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import router from "@/router";
import { useHeaderStore } from "@/stores/header";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Referat } from "@/types/Referat";
import { YesNoBoolean } from "@/types/YesNoBoolean";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zeitraum = ref<string>("");
const validationRules = useRules();
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein!")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein!"),
    validationRules.emailRule("Keine gültige Email"),
    validationRules.maxLengthRule(
        255,
        "Die Email darf nicht länger als 255 Zeichen sein"
    ),
];
const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein!"),
];
const dienststelleRule = [
    validationRules.notEmptyRule("Darf nicht leer sein"),
    validationRules.maxLengthRule(
        10,
        "Die Dienststelle darf nicht länger als 10 Zeichen sein"
    ),
];
const oertlAusbidlerRule = [
    validationRules.notEmptyRule("Darf nicht leer sein"),
    validationRules.maxLengthRule(
        255,
        "Örtliche Ausbilder*in darf nicht länger als 255 Zeichen sein"
    ),
];
const namentlicheAnforderungRule = [
    validationRules.maxLengthRule(
        255,
        "Der Name der angeforderte Nwk darf nicht länger als 255 Zeichen sein"
    ),
];
const taetigkeitenRule = [
    validationRules.notEmptyRule("Darf nicht leer sein"),
    validationRules.maxLengthRule(
        5000,
        "Tätigkeiten dürfen nicht länger als 5000 Zeichen sein"
    ),
];

const form = ref<HTMLFormElement>();
const headerStore = useHeaderStore();

onMounted(() => {
    headerStore.setHeader("Praktikumsstellen Meldung");
    MeldezeitraumService.getCurrentMeldezeitraum().then((zeitraueme) => {
        activeMeldezeitraum.value = zeitraueme.length > 0;
    });
});
function changeVorrZuweisungsZeitraum() {
    zeitraum.value = zeitraeueme.ausbildungsZeitraum(
        praktikumsstelle.value.ausbildungsrichtung,
        praktikumsstelle.value.ausbildungsjahr
    );
}
function resetForm() {
    form.value?.reset();
    router.push("/");
}
function uploadPraktikumsstelle() {
    if (!form.value?.validate()) return;
    MeldungService.uploadAusbildungsPraktikumsstelle(
        praktikumsstelle.value
    ).finally(() => {
        resetForm();
    });
}
function zustelleradressverwaltung() {
    headerStore.setHeader("ZAV - Zustelleradressverwaltung");
}
</script>
<style>
.spacing-left {
    margin-left: 30px;
}
</style>
