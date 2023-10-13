package de.muenchen.oss.praktikumsplaner.service;

import static org.apache.logging.log4j.util.Strings.isBlank;
import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.exception.ExcelImportException;
import jakarta.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcelService {
    private final Validator validator;
    private final DataFormatter dataFormatter = new DataFormatter();
    private static final int FIRST_SHEET = 0;
    private static final int FIRST_ROW = 0;
    private static final int NACHNAME_COLUM = 0;
    private static final int VORNAME_COLUM = 1;
    private static final int STUDIENGANG_COLUM = 2;
    private static final int JAHRGANG_COLUM = 3;
    private static final int VORLESUNGSTAGE_COLUM = 4;
    private static final int FIRST_EXCEPTION_INFO = 0;
    private static final String SPLIT_VORLESUNGSTAGE_REGEX = "[+]";

    public List<CreateNwkDTO> excelToNwkDTOList(String base64String) throws IOException {
        final InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64String));
        final XSSFWorkbook workbook = new XSSFWorkbook(stream);
        final XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);

        return getAllNwkFromSheet(sheet);
    }

    private List<CreateNwkDTO> getAllNwkFromSheet(XSSFSheet sheet) {
        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        List<ExcelImportException.ExcelImportExceptionInfo> importExceptionInfoList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == FIRST_ROW) continue;
            CreateNwkDTO createNwkDTO = null;
            try {
                createNwkDTO = getNwkDTOFromRow(row);
            } catch (ExcelImportException ex) {
                importExceptionInfoList.add(new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(),
                        ex.getExceptionInfos().get(FIRST_EXCEPTION_INFO).getColumName(),
                        ex.getExceptionInfos().get(FIRST_EXCEPTION_INFO).getValue()));
            }
            if (createNwkDTO == null || isCreateNwkDTOEmpty(createNwkDTO)) {
                continue;
            }
            validator.validate(createNwkDTO).forEach(violation -> importExceptionInfoList.add(
                    new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), violation.getPropertyPath().toString(), violation.getMessage())));
            createNwkDTOS.add(createNwkDTO);
        }
        if (!importExceptionInfoList.isEmpty())
            throw new ExcelImportException(importExceptionInfoList);
        return createNwkDTOS;
    }

    protected boolean isCreateNwkDTOEmpty(CreateNwkDTO createNwkDTO) {
        return (createNwkDTO.vorname() == null || createNwkDTO.vorname().isEmpty()) && (createNwkDTO.nachname() == null || createNwkDTO.nachname().isEmpty())
                && createNwkDTO.studiengang() == null && (createNwkDTO.jahrgang() == null || createNwkDTO.jahrgang().isEmpty());
    }

    private CreateNwkDTO getNwkDTOFromRow(Row row) {
        CreateNwkDTO.CreateNwkDTOBuilder createNwkDTOBuilder = CreateNwkDTO.builder();
        for (Cell cell : row) {
            final String cellValue = dataFormatter.formatCellValue(cell);
            switch (cell.getColumnIndex()) {
            case NACHNAME_COLUM -> createNwkDTOBuilder.nachname(cellValue);
            case VORNAME_COLUM -> createNwkDTOBuilder.vorname(cellValue);
            case STUDIENGANG_COLUM -> createNwkDTOBuilder
                    .studiengang(isBlank(cellValue) ? null : Studiengang.valueOf(cellValue));
            case JAHRGANG_COLUM -> createNwkDTOBuilder.jahrgang(cellValue);
            case VORLESUNGSTAGE_COLUM -> createNwkDTOBuilder.vorlesungstage(extractVorlesungstage(cellValue));
            default -> {
            }
            }
        }
        return createNwkDTOBuilder.build();
    }

    private Set<DayOfWeek> extractVorlesungstage(String vorlesungstageString) {
        return Arrays.stream(vorlesungstageString.split(SPLIT_VORLESUNGSTAGE_REGEX))
                .map(String::trim)
                .filter(vorlesungstagAsString -> !vorlesungstagAsString.isEmpty())
                .map(this::mapToDayOfWeek)
                .collect(Collectors.toSet());
    }

    private DayOfWeek mapToDayOfWeek(String vorlesungstagString) {
        switch (vorlesungstagString) {
        case "Mo" -> {
            return DayOfWeek.MONDAY;
        }
        case "Di" -> {
            return DayOfWeek.TUESDAY;
        }
        case "Mi" -> {
            return DayOfWeek.WEDNESDAY;
        }
        case "Do" -> {
            return DayOfWeek.THURSDAY;
        }
        case "Fr" -> {
            return DayOfWeek.FRIDAY;
        }
        case "Sa" -> {
            return DayOfWeek.SATURDAY;
        }
        }
        throw new ExcelImportException(List.of(new ExcelImportException.ExcelImportExceptionInfo(-1, "vorlesungstage", vorlesungstagString)));
    }
}
