package web.controller;

import bean.IDogadajSessionBean;
import dao.DogadajDao;
import dao.GradDao;
import dao.OrganizacijskaJedinicaDao;
import dao.VelicinaGradaDao;
import dto.*;
import exception.DogadajAppRuleException;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import util.DogadajAppConstants;
import util.DogadajAppUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Web managed bean za dogadaj
 */

@Named
@ViewScoped
public class DogadajiController implements Serializable {

    //fields
    private static final long serialVersionUID = 1L;
    private DogadajDto dogadajDto;
    private DogadajFilterDto dogadajFilterDto;

    private List<DogadajDto> dogadajiFilterList;

    //input form select items
    private List<SelectItem> gradSelectItems = new ArrayList<>();

    //filter select items
    private List<SelectItem> slobodanUlazFilterSelectItems = new ArrayList<>();
    private List<SelectItem> regijaFilterSelectItems = new ArrayList<>();
    private List<SelectItem> velicinaGradaFilterSelectItems = new ArrayList<>();

    //pune se kod inita
    private List<OrganizacijskaJedinicaDto> organizacijskaJedinicaDtoList;
    private List<GradDto> gradDtoList;
    private List<VelicinaGradaDto> velicinaGradaDtoList;
    //CDI
    @Inject
    private GradDao gradDao;
    @Inject
    private DogadajDao dogadajDao;
    @Inject
    private OrganizacijskaJedinicaDao orgJedinicaDao;
    @Inject
    private VelicinaGradaDao velicinaGradaDao;

    //EJB
    @EJB
    private IDogadajSessionBean dogadajSessionBean;

    public DogadajiController() {
        super();
    }

    @PostConstruct
    public void init() {
        //initialization dto object
        initializeDogadajDto();
        // fetch list grad, organizacijska jedinica, dogadaj
        fetchInitList();
        //select items
        getSelectItems();
        //initialization
        dogadajFilterDto = new DogadajFilterDto();
    }

    /*
     * save metoda za spremanje/ažuriranje događaja
     */
    public void save() {
        try {
            if (dogadajDto != null) {
                if (dogadajDto.getSifraDogadaja() != null) {
                    dogadajSessionBean.editDogadaj(dogadajDto);
                    addMessage("Događaj " + dogadajDto.getSifraDogadaja() + " je uspješno ažuriran.", DogadajAppConstants.SEVERITY_INFO);
                } else {
                    //create
                    DogadajDto resultDogadaj = dogadajSessionBean.createDogadaj(dogadajDto);
                    dogadajDto.setSifraDogadaja(resultDogadaj.getSifraDogadaja());
                    addMessage("Događaj je uspješno spremljen. Šifra događaja je " + resultDogadaj.getSifraDogadaja() + ".", DogadajAppConstants.SEVERITY_INFO);
                }
                dogadajiFilterList = dogadajDao.getFilterList(new DogadajFilterDto(dogadajDto.getSifraDogadaja()));
            } else {
                addMessage("Događaj je prazan (nema podataka).", DogadajAppConstants.SEVERITY_WARN);
            }
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom kreiranja/ažuriranja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    /*
     * Dohvati događaje prema popunjenom filteru
     */
    public void getFilterListDogadaj() {
        try {
            dogadajiFilterList = dogadajDao.getFilterList(dogadajFilterDto);
        } catch (DogadajAppRuleException eventEx) {
            if (eventEx.getMessages() != null && !eventEx.getMessages().isEmpty()) {
                for (String message : eventEx.getMessages()) {
                    eventEx.printStackTrace();
                    addMessage(message, DogadajAppConstants.SEVERITY_ERR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom pretraživanja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    /*
     * Odabirom retk u tablici popunjava se dogadajDto, odnosno popunjava input form
     */
    public void onTableRowSelect(AjaxBehaviorEvent event) {
        try {
            Object selected = ((SelectEvent) event).getObject();
            if (selected instanceof DogadajDto && ((DogadajDto) selected).getSifraDogadaja() != null) {
                DogadajDto selectedDogadajDto = (DogadajDto) selected;
                dogadajDto = dogadajDao.getFilterList(new DogadajFilterDto(selectedDogadajDto.getSifraDogadaja())).get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške odabira događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    /*
     * Resetiran filter dto - koristi se kod odabira "Poništi filter" na filter formi
     */
    public void resetFilterDto() {
        dogadajFilterDto.setNazivDogadaja(null);
        dogadajFilterDto.setVrijemeDoPocetak(null);
        dogadajFilterDto.setVrijemeDoKraj(null);
        dogadajFilterDto.setVrijemeOdPocetak(null);
        dogadajFilterDto.setVrijemeOdKraj(null);
        dogadajFilterDto.setGradovi(new ArrayList<>());
        dogadajFilterDto.setSifraGrada(null);
        dogadajFilterDto.setRegije(new ArrayList<>());
        dogadajFilterDto.setSlobodanUlaz(null);
        dogadajFilterDto.setVelicinaGrada(null);
        dogadajFilterDto.setZupanije(null);
        dogadajFilterDto.setOdabraneRegije(null);
        dogadajFilterDto.setOdabraneVelicineGrada(null);
        dogadajFilterDto.setOdabraniGradovi(null);
        dogadajFilterDto.setOdabraneZupanije(null);
        dogadajiFilterList = null;
    }

    /*
     * Resetiran dto - koristi se kod odabira "Novi" na input formi
     */
    public void resetDto() {
        getDogadajDto().setSifraDogadaja(null);
        getDogadajDto().setNazivDogadaja(null);
        getDogadajDto().setVrijemeOd(null);
        getDogadajDto().setVrijemeDo(null);
        getDogadajDto().setGradDogadajaDto(new GradDto());
        getDogadajDto().setSlobodanUlaz("false");
        resetFilterDto();
    }

    /*
     * Dohvat županije kod promjene regije u filteru
     */
    public List<SelectItem> getZupanija(String[] regije) {
        List<SelectItem> zupanijaSelectItems = new ArrayList<>();
        if (regije != null && regije.length > 0) {
            organizacijskaJedinicaDtoList.stream()
                    .filter(zupanija -> zupanija.getNadredenaOrganizacijeDto() != null && zupanija.getNadredenaOrganizacijeDto().getSifraOrgJedinice() != null)
                    .filter(zupanija -> DogadajAppUtil.getIntegerFromStringList(regije).contains(zupanija.getNadredenaOrganizacijeDto().getSifraOrgJedinice()))
                    .forEach(organizacijskaJedinicaDto -> zupanijaSelectItems.add(new SelectItem(organizacijskaJedinicaDto.getSifraOrgJedinice(), organizacijskaJedinicaDto.getNazivOrgJedinice())));
        } else {
            organizacijskaJedinicaDtoList.stream()
                    .filter(zupanija -> zupanija.getNadredenaOrganizacijeDto() != null && zupanija.getNadredenaOrganizacijeDto().getSifraOrgJedinice() != null)
                    .forEach(organizacijskaJedinicaDto -> zupanijaSelectItems.add(new SelectItem(organizacijskaJedinicaDto.getSifraOrgJedinice(), organizacijskaJedinicaDto.getNazivOrgJedinice())));
        }
        return zupanijaSelectItems;
    }

    /*
     * Dohvat županije kod promjene regije OLD WAY
     */
    public List<SelectItem> getZupanijaDB(String[] regije) {
        return orgJedinicaDao.getZupanijaListByRegijaList(regije);
    }

    /*
     * Dohvat gradova kod promjene regije, županije, veličine grada u filteru
     */
    public List<SelectItem> getGrad(String[] regije, String[] zupanije, String[] velicine) {
        List<SelectItem> gradSelectedItems = new ArrayList<>();
        if ((regije != null && regije.length > 0) || (zupanije != null && zupanije.length > 0) || (velicine != null && velicine.length > 0)) {
            gradDtoList.stream()
                    .filter((regije != null && regije.length > 0) ? gradDto -> DogadajAppUtil.getIntegerFromStringList(regije).contains(gradDto.getOrganizacijskaJedinicaDto().getNadredenaOrganizacijeDto().getSifraOrgJedinice()) : gradDto -> true)
                    .filter((zupanije != null && zupanije.length > 0) ? gradDto -> DogadajAppUtil.getIntegerFromStringList(zupanije).contains(gradDto.getOrganizacijskaJedinicaDto().getSifraOrgJedinice()) : gradDto -> true)
                    .filter((velicine != null && velicine.length > 0) ? gradDto -> DogadajAppUtil.getIntegerFromStringList(velicine).contains(gradDto.getVelicinaGradaDto().getSifraVelicineGrada()) : gradDto -> true)
                    .forEach(gradDto -> gradSelectedItems.add(new SelectItem(gradDto.getSifraGrada(), gradDto.getNazivGrada())));
        } else {
            gradDtoList.stream().forEach(gradDto -> gradSelectedItems.add(new SelectItem(gradDto.getSifraGrada(), gradDto.getNazivGrada())));
        }
        return gradSelectedItems;
    }

    /*
     * Dohvat gradova kod promjene regije, županije, veličine grada u filteru OLD WAY
     */
    public List<SelectItem> getGradDB(String[] regije, String[] zupanije, String[] velicine) {
        return gradDao.getGradListByZupanijaVelicina(regije, zupanije, velicine);
    }

    /*
     * Inicijalizacija dto-a kod postconstructa
     */
    private void initializeDogadajDto() {
        dogadajDto = new DogadajDto();
        dogadajDto.setGradDogadajaDto(new GradDto());
    }

    /*
     * Dohvat liste organizacijskih jedinice, gradova, velcine gradova i događaja
     */
    private void fetchInitList() {
        organizacijskaJedinicaDtoList = orgJedinicaDao.findAll();
        gradDtoList = gradDao.findAll();
        velicinaGradaDtoList = velicinaGradaDao.findAll();
    }

    /*
     * Punjenje slecet item-a za input form/filter (onih koji se dinamički ne pune)
     */
    private void getSelectItems() {
        //grad - input form
        gradSelectItems.add(new SelectItem(null, "Odaberite"));
        gradDtoList.stream().forEach(gradDto -> gradSelectItems.add(new SelectItem(gradDto.getSifraGrada(), gradDto.getNazivGrada())));
        //slobodan ulaz - filter
        slobodanUlazFilterSelectItems.add(new SelectItem(null, ""));
        slobodanUlazFilterSelectItems.add(new SelectItem(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA, DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA));
        slobodanUlazFilterSelectItems.add(new SelectItem(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE, DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE));
        //regija - filter
        organizacijskaJedinicaDtoList.stream()
                .filter(organizacijskaJedinicaDto -> organizacijskaJedinicaDto.getNadredenaOrganizacijeDto() == null)
                .forEach(organizacijskaJedinicaDto -> regijaFilterSelectItems.add(new SelectItem(organizacijskaJedinicaDto.getSifraOrgJedinice(), organizacijskaJedinicaDto.getNazivOrgJedinice())));
        //velcina grada
        velicinaGradaDtoList.stream()
                .forEach(velicinaGradaDto -> velicinaGradaFilterSelectItems.add(new SelectItem(velicinaGradaDto.getSifraVelicineGrada(), velicinaGradaDto.getNazivVelicineGrada())));
    }

    public void addMessage(String summary, String severity) {
        if (StringUtils.isNoneBlank(summary) && StringUtils.isNoneBlank(severity)) {
            FacesMessage message = null;
            switch (severity) {
                case DogadajAppConstants.SEVERITY_ERR:
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    break;
                case DogadajAppConstants.SEVERITY_INFO:
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
                    break;
                case DogadajAppConstants.SEVERITY_WARN:
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
                    break;
                default:
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /*
     * Za potrebe row edita (kada se koristi row edit u tablici) - trenutno se ne koristi
     */
    public void onRowEdit(RowEditEvent event) {
        try {
            DataTable dataTable = (DataTable) event.getSource();
//            dogadajEditDto = new DogadajDto();
//            dogadajEditDto = (DogadajDto) dataTable.getRowData();
//            if (dogadajEditDto.getSifraDogadaja() != null) {
//                save();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom ažuriranja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    //getters & setters
    public List<DogadajDto> getDogadajiFilterList() {
        return dogadajiFilterList;
    }

    public void setDogadajiFilterList(List<DogadajDto> dogadajiFilterList) {
        this.dogadajiFilterList = dogadajiFilterList;
    }

    public DogadajDto getDogadajDto() {
        return dogadajDto;
    }

    public void setDogadajDto(DogadajDto dogadajDto) {
        this.dogadajDto = dogadajDto;
    }

    public List<SelectItem> getGradSelectItems() {
        return gradSelectItems;
    }

    public void setGradSelectItems(List<SelectItem> gradSelectItems) {
        this.gradSelectItems = gradSelectItems;
    }

    public DogadajFilterDto getDogadajFilterDto() {
        return dogadajFilterDto;
    }

    public void setDogadajFilterDto(DogadajFilterDto dogadajFilterDto) {
        this.dogadajFilterDto = dogadajFilterDto;
    }

    public List<SelectItem> getSlobodanUlazFilterSelectItems() {
        return slobodanUlazFilterSelectItems;
    }

    public void setSlobodanUlazFilterSelectItems(List<SelectItem> slobodanUlazFilterSelectItems) {
        this.slobodanUlazFilterSelectItems = slobodanUlazFilterSelectItems;
    }

    public List<SelectItem> getRegijaFilterSelectItems() {
        return regijaFilterSelectItems;
    }

    public void setRegijaFilterSelectItems(List<SelectItem> regijaFilterSelectItems) {
        this.regijaFilterSelectItems = regijaFilterSelectItems;
    }

    public List<SelectItem> getVelicinaGradaFilterSelectItems() {
        return velicinaGradaFilterSelectItems;
    }

    public void setVelicinaGradaFilterSelectItems(List<SelectItem> velicinaGradaFilterSelectItems) {
        this.velicinaGradaFilterSelectItems = velicinaGradaFilterSelectItems;
    }

    public List<OrganizacijskaJedinicaDto> getOrganizacijskaJedinicaDtoList() {
        return organizacijskaJedinicaDtoList;
    }

    public void setOrganizacijskaJedinicaDtoList(List<OrganizacijskaJedinicaDto> organizacijskaJedinicaDtoList) {
        this.organizacijskaJedinicaDtoList = organizacijskaJedinicaDtoList;
    }

    public List<GradDto> getGradDtoList() {
        return gradDtoList;
    }

    public void setGradDtoList(List<GradDto> gradDtoList) {
        this.gradDtoList = gradDtoList;
    }

    public List<VelicinaGradaDto> getVelicinaGradaDtoList() {
        return velicinaGradaDtoList;
    }

    public void setVelicinaGradaDtoList(List<VelicinaGradaDto> velicinaGradaDtoList) {
        this.velicinaGradaDtoList = velicinaGradaDtoList;
    }
}
