package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * Data transfer object za entitet dogadaj
 */
public class DogadajDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // fields
    private Integer sifraDogadaja;
    private String nazivDogadaja;
    private GradDto gradDogadajaDto;
    private LocalDateTime vrijemeOd;
    private LocalDateTime vrijemeDo;
    private String slobodanUlaz;

    // constructors
    public DogadajDto() {
        super();
    }

    // getters & setters
    public Integer getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(Integer sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public GradDto getGradDogadajaDto() {
        return gradDogadajaDto;
    }

    public void setGradDogadajaDto(GradDto gradDogadajaDto) {
        this.gradDogadajaDto = gradDogadajaDto;
    }

    public LocalDateTime getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(LocalDateTime vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public LocalDateTime getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(LocalDateTime vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public String getSlobodanUlaz() {
        return slobodanUlaz;
    }

    public void setSlobodanUlaz(String slobodanUlaz) {
        this.slobodanUlaz = slobodanUlaz;
    }
}
