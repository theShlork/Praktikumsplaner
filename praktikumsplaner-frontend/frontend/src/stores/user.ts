import { defineStore } from "pinia";
import { computed, ref } from "vue";

export interface UserState {
    username: string | undefined;
    roles: string[];
}

export const useUserStore = defineStore("user", () => {
    const username = ref<string | undefined>(undefined);
    const roles = ref<string[]>([]);

    const getUsername = computed((): string | undefined => {
        return username.value;
    });

    const getRoles = computed((): string[] => {
        return roles.value;
    });

    function setUsername(payload: string | undefined): void {
        username.value = payload;
    }

    function setRoles(payload: string[]): void {
        roles.value = payload;
    }

    return { getUsername, getRoles, setUsername, setRoles };
});
