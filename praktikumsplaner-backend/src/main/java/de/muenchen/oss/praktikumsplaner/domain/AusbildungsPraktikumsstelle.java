package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.YesNo;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AusbildungsPraktikumsstelle extends BaseEntity {

    @NotNull
    public String dienststelle;

    @NotNull
    public String oertlicheAusbiler;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String taetigkeiten;

    @NotNull
    public Dringlichkeit dringlichkeit;

    public String namentlicheAnforderung;

    public Referat referat;

    @NotNull
    public YesNo projektarbeit;

    @NotNull
    public Ausbildungsjahr ausbildungsjahr;

    @NotNull
    public Ausbildungsrichtung ausbildungsrichtung;


}
