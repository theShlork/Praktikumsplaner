import { shallowMount } from "@vue/test-utils";
import { describe, expect, it } from "vitest";

import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import Meldezeitraum from "@/types/Meldezeitraum";

describe("Zeitraumpicker.vue", () => {
    it("renders no error messages in Zeitraumpicker when the dates are in correct order", () => {
        const errorMessageStart =
            "Der Beginn des Meldezeitraums muss vor dem Ende liegen.";
        const errorMessageEnd =
            "Das Ende des Meldezeitraums muss vor dem Beginn liegen.";

        const zeitraum = new Meldezeitraum("name", "2019-10-10", "2020-10-10");
        const wrapper = shallowMount(ZeitraumPicker, {
            props: { value: zeitraum },
        });

        expect(wrapper.html()).not.toContain(errorMessageEnd);
        expect(wrapper.html()).not.toContain(errorMessageStart);
    });
});