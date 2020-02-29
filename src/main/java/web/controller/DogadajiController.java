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
import util.DogadajAppConstants;
import util.DogadajAppUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private DogadajDto dogadajEditDto;
    private DogadajFilterDto dogadajFilterDto;


    private List<DogadajDto> dogadajiList;
    private List<DogadajDto> dogadajiFilterList;
    private List<DogadajDto> dogadajiFilterList1;

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

    //create/edit dogadaj
    public void save() {
        try {
            if (dogadajEditDto != null && dogadajEditDto.getSifraDogadaja() != null) {
                dogadajSessionBean.editDogadaj(dogadajEditDto);
                addMessage("Događaj " + dogadajEditDto.getSifraDogadaja() + " je uspješno ažuriran.", DogadajAppConstants.SEVERITY_INFO);
                fetchDogadajList();
                dogadajEditDto = null;
            } else if (dogadajDto != null) {
                //create
                DogadajDto resultDogadaj = dogadajSessionBean.createDogadaj(dogadajDto);
                dogadajDto.setSifraDogadajaView(resultDogadaj.getSifraDogadaja());
                addMessage("Događaj je uspješno spremljen. Šifra događaja je " + resultDogadaj.getSifraDogadaja() + ".", DogadajAppConstants.SEVERITY_INFO);
                fetchDogadajList();
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

    //filter dogadaj
    public void getFilterListDogadaj() {
        try {
            dogadajiFilterList1 = dogadajDao.getFilterList(dogadajFilterDto);
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

    public void onRowEdit(RowEditEvent event) {
        try {
            DataTable dataTable = (DataTable) event.getSource();
            dogadajEditDto = new DogadajDto();
            dogadajEditDto = (DogadajDto) dataTable.getRowData();
            if (dogadajEditDto.getSifraDogadaja() != null) {
                save();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage("Došlo je do greške prilikom ažuriranja događaja.", DogadajAppConstants.SEVERITY_ERR);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        //TODO
    }

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
        dogadajiFilterList1 = null;
    }

    public void resetDto() {
        getDogadajDto().setSifraDogadaja(null);
        getDogadajDto().setSifraDogadajaView(null);
        getDogadajDto().setNazivDogadaja(null);
        getDogadajDto().setVrijemeOd(null);
        getDogadajDto().setVrijemeDo(null);
        getDogadajDto().setGradDogadajaDto(new GradDto());
        getDogadajDto().setSlobodanUlaz("false");
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
        dogadajiList = dogadajDao.findAll(); //lista svih događaja za prikaz na tablici (DEMO) / u slučaju velike količine podataka LAZY load
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


    private List<DogadajDto> fetchDogadajList() {
        return dogadajiList = dogadajDao.findAll();
    }

    //getters & setters
    public List<DogadajDto> getDogadajiList() {
        return dogadajiList;
    }

    public void setDogadajiList(List<DogadajDto> dogadajiList) {
        this.dogadajiList = dogadajiList;
    }

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

    public DogadajDto getDogadajEditDto() {
        return dogadajEditDto;
    }

    public void setDogadajEditDto(DogadajDto dogadajEditDto) {
        this.dogadajEditDto = dogadajEditDto;
    }

    public List<DogadajDto> getDogadajiFilterList1() {
        return dogadajiFilterList1;
    }

    public void setDogadajiFilterList1(List<DogadajDto> dogadajiFilterList1) {
        this.dogadajiFilterList1 = dogadajiFilterList1;
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
