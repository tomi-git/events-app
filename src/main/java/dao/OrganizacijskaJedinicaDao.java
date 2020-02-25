package dao;

import dto.OrganizacijskaJedinicaDto;
import dto.TipOrganizacijskeJediniceDto;
import entity.OrganizacijskaJedinica;
import util.DogadajAppConstants;
import util.DogadajAppUtil;

import javax.faces.model.SelectItem;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class OrganizacijskaJedinicaDao extends GenericDao<Object, OrganizacijskaJedinicaDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected OrganizacijskaJedinica formEntity(OrganizacijskaJedinicaDto dto) {
        return null;
    }

    @Override
    protected OrganizacijskaJedinicaDto formDTO(Object o) {
        OrganizacijskaJedinicaDto organizacijskaJedinicaDto = null;
        if (o != null) {
            organizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
            if (o instanceof OrganizacijskaJedinica) {

                OrganizacijskaJedinica orgJedinicaEntity = (OrganizacijskaJedinica) o;
                organizacijskaJedinicaDto.setSifraOrgJedinice(orgJedinicaEntity.getSifraOrgJedinice());
                organizacijskaJedinicaDto.setNazivOrgJedinice(orgJedinicaEntity.getNazivOrgJedinice());
                organizacijskaJedinicaDto.setOpisOrgJedinice(orgJedinicaEntity.getOpisOrgJedinice());
                //tip
                if (orgJedinicaEntity.getTipOrganizacijskeJedinice() != null) {
                    TipOrganizacijskeJediniceDto tipOrganizacijskeJediniceDto = new TipOrganizacijskeJediniceDto();
                    tipOrganizacijskeJediniceDto.setSifraTipOrgJedinice(orgJedinicaEntity.getTipOrganizacijskeJedinice().getSifraTipOrgJedinice());
                    tipOrganizacijskeJediniceDto.setNazivOrgJedinice(orgJedinicaEntity.getTipOrganizacijskeJedinice().getNazivTipOrgJedinice());
                    tipOrganizacijskeJediniceDto.setAktivna(orgJedinicaEntity.getTipOrganizacijskeJedinice().getAktivanTipOrgJedinice());
                    organizacijskaJedinicaDto.setTipOrganizacijskeJediniceDto(tipOrganizacijskeJediniceDto);
                }
                if (orgJedinicaEntity.getNadredenaOrgJedinica() != null) {
                    OrganizacijskaJedinicaDto nadredenaOrgJedinicaDto = new OrganizacijskaJedinicaDto();
                    nadredenaOrgJedinicaDto.setSifraOrgJedinice(orgJedinicaEntity.getNadredenaOrgJedinica().getSifraOrgJedinice());
                    nadredenaOrgJedinicaDto.setNazivOrgJedinice(orgJedinicaEntity.getNadredenaOrgJedinica().getNazivOrgJedinice());
                    nadredenaOrgJedinicaDto.setOpisOrgJedinice(orgJedinicaEntity.getNadredenaOrgJedinica().getOpisOrgJedinice());
                    //tip
                    if (orgJedinicaEntity.getNadredenaOrgJedinica().getTipOrganizacijskeJedinice() != null) {
                        TipOrganizacijskeJediniceDto tipOrganizacijskeJediniceNadredenaDto = new TipOrganizacijskeJediniceDto();
                        tipOrganizacijskeJediniceNadredenaDto.setSifraTipOrgJedinice(orgJedinicaEntity.getNadredenaOrgJedinica().getTipOrganizacijskeJedinice().getSifraTipOrgJedinice());
                        tipOrganizacijskeJediniceNadredenaDto.setNazivOrgJedinice(orgJedinicaEntity.getNadredenaOrgJedinica().getTipOrganizacijskeJedinice().getNazivTipOrgJedinice());
                        tipOrganizacijskeJediniceNadredenaDto.setAktivna(orgJedinicaEntity.getNadredenaOrgJedinica().getTipOrganizacijskeJedinice().getAktivanTipOrgJedinice());
                        nadredenaOrgJedinicaDto.setTipOrganizacijskeJediniceDto(tipOrganizacijskeJediniceNadredenaDto);
                    }
                    organizacijskaJedinicaDto.setNadredenaOrganizacijeDto(nadredenaOrgJedinicaDto);
                }
            } else {
                Object[] entity = (Object[]) o;
                organizacijskaJedinicaDto.setSifraOrgJedinice((Integer) entity[0]);
                organizacijskaJedinicaDto.setNazivOrgJedinice((String) entity[1]);
            }
        }
        return organizacijskaJedinicaDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM OrganizacijskaJedinica e";
    }

    public Map<Integer, List<SelectItem>> getSelectItemsOrgJedinica() {
        Map<Integer, List<SelectItem>> result = new HashMap<>();
        List<OrganizacijskaJedinicaDto> listaOrgJedinicaDto = findAll();
        //regija
        List<SelectItem> regijaSelectItems = new ArrayList<>();
        Stream<OrganizacijskaJedinicaDto> orgJedinicaRegijaStream = listaOrgJedinicaDto.stream().filter(p -> p.getTipOrganizacijskeJediniceDto().getSifraTipOrgJedinice().equals(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_REGIJA));
        orgJedinicaRegijaStream.forEach(p -> regijaSelectItems.add(new SelectItem(p.getSifraOrgJedinice(), p.getNazivOrgJedinice())));

        //zupanija
        List<SelectItem> zupanijaSelectItems = new ArrayList<>();
        Stream<OrganizacijskaJedinicaDto> orgJedinicaZupanijaStream = listaOrgJedinicaDto.stream().filter(p -> p.getTipOrganizacijskeJediniceDto().getSifraTipOrgJedinice().equals(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_ZUPANIJA));
        orgJedinicaZupanijaStream.forEach(p -> zupanijaSelectItems.add(new SelectItem(p.getSifraOrgJedinice(), p.getNazivOrgJedinice())));

        result.put(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_REGIJA, regijaSelectItems);
        result.put(DogadajAppConstants.TIP_ORGANIZACIJSKE_JEDINICE_ZUPANIJA, zupanijaSelectItems);

        return result;
    }

    public List<SelectItem> getZupanijaListByRegijaList(String[] regije) {
        List<SelectItem> resultSelectItems = new ArrayList<>();
        resultSelectItems.add(new SelectItem("", "Odaberite"));
        //sql
        String sql = "select sifra, naziv from igea_tk.organizacijska_jedinica " +
                "where 1 = 1 ";
        if (regije != null && regije.length > 0) sql = sql + "and org_jedinica in :regije";
        //query
        Query query = getEntityManager().createNativeQuery(sql);
        if (regije != null && regije.length > 0) query.setParameter("regije", DogadajAppUtil.getIntegerFromStringList(regije));
        //result
        List<Object[]> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            Stream<Object[]> listZupnijaStream = resultList.stream();
            listZupnijaStream.forEach(p -> resultSelectItems.add(new SelectItem((Integer)p[0], (String)p[1])));
        }
        return resultSelectItems;
    }

}
