package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;
    private final NwkRepository nwkRepository;

    public StudiumsPraktikumsstelleDto saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto) {
        StudiumsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper.toDto(studiumsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public AusbildungsPraktikumsstelleDto saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto) {
        AusbildungsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper
                .toDto(ausbildungsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public TreeMap<String, List<PraktikumsstelleDto>> getAllPraktiumsstellen() {
        UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();

        List<PraktikumsstelleDto> ausbildungsListDto = ausbildungsPraktikumsstellenRepository.findAllByMeldezeitraumID(lastMeldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto).collect(Collectors.toList());

        List<PraktikumsstelleDto> studiumsListDto = studiumsPraktikumsstellenRepository.findAllByMeldezeitraumID(lastMeldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto).collect(Collectors.toList());

        List<PraktikumsstelleDto> combinedList = new ArrayList<>();
        combinedList.addAll(ausbildungsListDto);
        combinedList.addAll(studiumsListDto);
        combinedList.sort(Comparator.comparing(PraktikumsstelleDto::dienststelle));

        return groupDienststellen(combinedList);
    }

    public PraktikumsstelleDto assignNwk(UUID praktikumsstellenID, UUID nwkID) {
        Nwk assignedNwk = nwkRepository.findById(nwkID).orElseThrow();

        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            } else throw new ResourceConflictException("Praktikumsstelle already has an assigned Nwk!");
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                studiumsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            } else throw new ResourceConflictException("Praktikumsstelle already has an assigned Nwk!");
        } else throw new ResourceNotFoundException("Praktikumsstelle not found!");
    }

    public PraktikumsstelleDto unassignNwk(UUID praktikumsstellenId) {
        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            studiumsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        } else throw new ResourceNotFoundException("Praktikumsstelle not found!");
    }

    private TreeMap<String, List<PraktikumsstelleDto>> groupDienststellen(final Iterable<PraktikumsstelleDto> allPraktikumsstellen) {
        TreeMap<String, List<PraktikumsstelleDto>> abteilungsMap = new TreeMap<>();

        for (PraktikumsstelleDto praktikumsstelle : allPraktikumsstellen) {
            String dienststelle = praktikumsstelle.dienststelle();
            String hauptabteilung = getHauptabteilung(dienststelle);

            abteilungsMap.computeIfAbsent(hauptabteilung, k -> new ArrayList<>()).add(praktikumsstelle);
        }

        return abteilungsMap;
    }

    private String getHauptabteilung(final String dienststelle) {
        Matcher matcher = Pattern.compile("\\d").matcher(dienststelle);
        if (matcher.find()) {
            return dienststelle.substring(0, matcher.start() + 1);
        }
        return dienststelle;
    }

    private String normalizeDienststelle(final String dienststelle) {
        return dienststelle.toUpperCase().trim().replaceAll("ITM|IT@M|RIT|-", "");
    }
}