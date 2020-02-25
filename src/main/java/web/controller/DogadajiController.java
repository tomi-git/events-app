package web.controller;

import bean.IDogadajSessionBean;
import dao.DogadajDao;
import dao.GradDao;
import dao.OrganizacijskaJedinicaDao;
import dao.VelicinaGradaDao;
import dto.DogadajDto;
import dto.DogadajFilterDto;
import dto.GradDto;
import exception.DogadajAppRuleException;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import util.DogadajAppConstants;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private List<SelectItem> gradSelectItems;
    private List<SelectItem> zupanijaSelectItems;
    private List<SelectItem> regijaSelectItems;

    //filter select items
    private List<SelectItem> slobodanUlazFilterSelectItems;
    private List<SelectItem> gradFilterSelectItems;
    private List<SelectItem> zupanijaFilterSelectItems;
    private List<SelectItem> regijaFilterSelectItems;
    private List<SelectItem> velicinaGradaFilterSelectItems;

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
        //select items
        fetchInitSelectItems();
        //initialization
        dogadajFilterDto = new DogadajFilterDto();

        //get dogadaj for data table
        fetchDogadajList();
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
                addMessage("Događaj je uspješno spremljen. Šifra događaja je " + resultDogadaj.getSifraDogadaja() + ".", DogadajAppConstants.SEVERITY_INFO);
                resetDto();
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

    private void resetDto() {
        getDogadajDto().setSifraDogadaja(null);
        getDogadajDto().setNazivDogadaja(null);
        getDogadajDto().setVrijemeOd(null);
        getDogadajDto().setVrijemeDo(null);
        getDogadajDto().setGradDogadajaDto(new GradDto());
        getDogadajDto().setSlobodanUlaz("false");
    }

    public List<SelectItem> getTablica(String[] regije) {
        return orgJedinicaDao.getZupanijaListByRegijaList(regije);
    }

    public List<SelectItem> getGrad(String[] regije, String[] zupanije, String[] velicine) {
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

    private void fetchInitSelectItems() {
        gradSelectItems = gradDao.getSelectItemsForGrad();
        Map<Integer, List<SelectItem>> organizacijskaJedinicaMap = orgJedinicaDao.getSelectItemsOrgJedinica();
        regijaSelectItems = organizacijskaJedinicaMap.get(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_REGIJA);
        zupanijaSelectItems = organizacijskaJedinicaMap.get(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_ZUPANIJA);

        //filter
        slobodanUlazFilterSelectItems = new ArrayList<>();
        slobodanUlazFilterSelectItems.add(new SelectItem("", "Odaberite"));
        slobodanUlazFilterSelectItems.add(new SelectItem(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA, DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_DA));
        slobodanUlazFilterSelectItems.add(new SelectItem(DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE, DogadajAppConstants.ENTITY_SLOBODAN_ULAZ_NE));
        gradFilterSelectItems = new ArrayList<>(gradSelectItems);
        regijaFilterSelectItems = new ArrayList<>(regijaSelectItems);
        zupanijaFilterSelectItems = new ArrayList<>(zupanijaSelectItems);
        velicinaGradaFilterSelectItems = velicinaGradaDao.getSelectItemsForVelicinaGrada();

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

    public List<SelectItem> getZupanijaSelectItems() {
        return zupanijaSelectItems;
    }

    public void setZupanijaSelectItems(List<SelectItem> zupanijaSelectItems) {
        this.zupanijaSelectItems = zupanijaSelectItems;
    }

    public List<SelectItem> getRegijaSelectItems() {
        return regijaSelectItems;
    }

    public void setRegijaSelectItems(List<SelectItem> regijaSelectItems) {
        this.regijaSelectItems = regijaSelectItems;
    }

    public List<SelectItem> getSlobodanUlazFilterSelectItems() {
        return slobodanUlazFilterSelectItems;
    }

    public void setSlobodanUlazFilterSelectItems(List<SelectItem> slobodanUlazFilterSelectItems) {
        this.slobodanUlazFilterSelectItems = slobodanUlazFilterSelectItems;
    }

    public List<SelectItem> getGradFilterSelectItems() {
        return gradFilterSelectItems;
    }

    public void setGradFilterSelectItems(List<SelectItem> gradFilterSelectItems) {
        this.gradFilterSelectItems = gradFilterSelectItems;
    }

    public List<SelectItem> getZupanijaFilterSelectItems() {
        return zupanijaFilterSelectItems;
    }

    public void setZupanijaFilterSelectItems(List<SelectItem> zupanijaFilterSelectItems) {
        this.zupanijaFilterSelectItems = zupanijaFilterSelectItems;
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
}
