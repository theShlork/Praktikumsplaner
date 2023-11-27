package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        StudiumsPraktikumsstelle entityWithNormalDienststelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityWithNormalDienststelle.setDienststelle(normalizeDienststelle(entityWithNormalDienststelle.getDienststelle()));
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstellenRepository
                .save(entityWithNormalDienststelle));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        AusbildungsPraktikumsstelle entityWithNormalDienststelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityWithNormalDienststelle.setDienststelle(normalizeDienststelle(entityWithNormalDienststelle.getDienststelle()));
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstellenRepository
                        .save(entityWithNormalDienststelle));
    }

    public TreeMap<String, List<PraktikumsstelleDTO>> getAllPraktiumsstellen() {
        UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();

        List<PraktikumsstelleDTO> ausbildungsListDTO = ausbildungsPraktikumsstellenRepository.findAllByMeldezeitraumID(lastMeldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDTO).collect(Collectors.toList());

        List<PraktikumsstelleDTO> studiumsListDTO = studiumsPraktikumsstellenRepository.findAllByMeldezeitraumID(lastMeldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDTO).collect(Collectors.toList());

        List<PraktikumsstelleDTO> combinedList = new ArrayList<>();
        combinedList.addAll(ausbildungsListDTO);
        combinedList.addAll(studiumsListDTO);
        combinedList.sort(Comparator.comparing(PraktikumsstelleDTO::dienststelle));

        TreeMap<String, List<PraktikumsstelleDTO>> groupedPraktikumsstellen = groupDienststellen(combinedList);

        return groupedPraktikumsstellen;
    }

    private TreeMap<String, List<PraktikumsstelleDTO>> groupDienststellen(final Iterable<PraktikumsstelleDTO> allPraktikumsstellen) {
        TreeMap<String, List<PraktikumsstelleDTO>> abteilungsMap = new TreeMap<>();

        for (PraktikumsstelleDTO praktikumsstelle : allPraktikumsstellen) {
            String dienststelle = praktikumsstelle.dienststelle();
            String hauptabteilung = getHauptabteilung(dienststelle);

            abteilungsMap.computeIfAbsent(hauptabteilung, k -> new ArrayList<>()).add(praktikumsstelle);
        }

        return abteilungsMap;
    }

    private String getHauptabteilung(final String dienststelle) {
        int index = -1;
        for (int i = 0; i < dienststelle.length(); i++) {
            if (Character.isDigit(dienststelle.charAt(i))) {
                index = i;
                return dienststelle.substring(0, index + 1);
            }
        }
        return dienststelle;
    }

    private String normalizeDienststelle(final String dienststelle) {
        return dienststelle.toUpperCase().trim().replace("ITM | IT@M | RIT | - |", "");
    }
}