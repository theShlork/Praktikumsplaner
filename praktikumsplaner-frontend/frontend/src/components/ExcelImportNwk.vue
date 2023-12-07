<template>
    <div>
        <v-dialog
            v-model="visible"
            :persistent="true"
            max-width="550"
        >
            <template #activator="{ props }">
                <v-btn
                    color="primary"
                    v-bind="props"
                >
                    <v-icon>mdi-tray-arrow-up</v-icon>
                    Datei Hochladen
                </v-btn>
            </template>
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >Datei hochladen</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-file-input
                                :model-value="excelDateien"
                                :accept="excelFormat"
                                :rules="rules"
                                label="Datei auswählen"
                                prepend-icon="mdi-tray-arrow-up"
                            >
                            </v-file-input>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            variant="outlined"
                            @click="cancel()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            variant="flat"
                            color="primary"
                            @click="uploadFile()"
                        >
                            Hochladen
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-form>
        </v-dialog>
        <error-dialog
            :dialogtext="errorDialogText"
            :dialogtitle="errorDialogTitle"
            :model-value="errorDialog"
            iconcolor="red"
            icontext="mdi mdi-alert-octagon-outline"
            @close="errorDialog = false"
        ></error-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

import { Levels } from "@/api/error";
import NwkService from "@/api/NwkService";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorDialog from "@/components/common/ErrorDialog.vue";
import { useRules } from "@/composables/rules";
import { useSnackbarStore } from "@/stores/snackbar";

const visible = ref<boolean>();
const excelDateien = ref<File[]>();
const form = ref<HTMLFormElement>();
const snackbarStore = useSnackbarStore();
const excelFormat =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
const validationRules = useRules();
const rules = [
    validationRules.filesRequiredRule(
        "Eine Excel-Datei hochladen oder abbrechen."
    ),
    validationRules.filesFirstTypeRule(
        excelFormat,
        "Falsches Dateiformat. Es muss eine Excel-Datei hochgeladen werden."
    ),
];
const errorDialog = ref<boolean>(false);
const errorDialogText = ref<string>(
    "Ihre Exceldatei konnte nicht hochgeladen werde. Bitte überprüfen Sie die Datei und versuchen Sie es erneut."
);
const errorDialogTitle = ref<string>("Excel Import fehlgeschlagen");

function cancel() {
    visible.value = false;
    form.value?.reset();
}
function uploadFile() {
    if (!excelDateien.value || !form.value?.validate()) return;
    NwkService.uploadExcelFile(excelDateien.value[0])
        .then(() =>
            snackbarStore.showMessage({
                message: "Nachwuchskräfte erfolgreich angelegt.",
                level: Levels.SUCCESS,
            })
        )
        .catch(() => {
            showError();
        })
        .finally(() => {
            cancel();
        });
}

function showError() {
    visible.value = false;
    errorDialog.value = true;
}
</script>

<style scoped></style>
