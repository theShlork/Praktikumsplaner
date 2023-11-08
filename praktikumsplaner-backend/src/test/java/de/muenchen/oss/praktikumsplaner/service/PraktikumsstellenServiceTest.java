package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PraktikumsstellenServiceTest {
    @Spy
    private PraktikumsstellenMapper mapper = Mappers.getMapper(PraktikumsstellenMapper.class);
    @Mock
    private AusbildungsPraktikumsstellenRepository ausbildungsRepository;
    @Mock
    private StudiumsPraktikumsstellenRepository studiumsRepository;
    @InjectMocks
    private PraktikumsstellenService service;

    @Test
    public void testCreateStudiumsPraktikumsstelle() {
        StudiumsPraktikumsstelle studiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        studiumsPraktikumsstelle.setId(UUID.randomUUID());
        studiumsPraktikumsstelle.setDienststelle("Testdienststelle");
        studiumsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        studiumsPraktikumsstelle.setEmail("test@test.de");
        studiumsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        studiumsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        studiumsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        studiumsPraktikumsstelle.setReferat(Referat.ITM);
        studiumsPraktikumsstelle.setProgrammierkenntnisse(true);
        studiumsPraktikumsstelle.setStudiensemester(Studiensemester.SEMESTER1);
        studiumsPraktikumsstelle.setStudienart(Studiengang.BSC);

        CreateStudiumsPraktikumsstelleDTO createDTO = CreateStudiumsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        StudiumsPraktikumsstelleDTO dto = StudiumsPraktikumsstelleDTO.builder().id(studiumsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        when(studiumsRepository.save(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toEntity(createDTO)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toDTO(studiumsPraktikumsstelle)).thenReturn(dto);

        StudiumsPraktikumsstelleDTO result = service.saveStudiumsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }

    @Test
    public void testCreateAusbildungsPraktikumsstelle() {
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelle();
        ausbildungsPraktikumsstelle.setId(UUID.randomUUID());
        ausbildungsPraktikumsstelle.setDienststelle("Testdienststelle");
        ausbildungsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        ausbildungsPraktikumsstelle.setEmail("test@test.de");
        ausbildungsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        ausbildungsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        ausbildungsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        ausbildungsPraktikumsstelle.setReferat(Referat.ITM);
        ausbildungsPraktikumsstelle.setProjektarbeit(true);
        ausbildungsPraktikumsstelle.setAusbildungsjahr(Ausbildungsjahr.JAHR1);
        ausbildungsPraktikumsstelle.setAusbildungsrichtung(Studiengang.FISI);

        CreateAusbildungsPraktikumsstelleDTO createDTO = CreateAusbildungsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        AusbildungsPraktikumsstelleDTO dto = AusbildungsPraktikumsstelleDTO.builder().id(ausbildungsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        when(ausbildungsRepository.save(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toEntity(createDTO)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toDTO(ausbildungsPraktikumsstelle)).thenReturn(dto);

        AusbildungsPraktikumsstelleDTO result = service.saveAusbildungsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }
}