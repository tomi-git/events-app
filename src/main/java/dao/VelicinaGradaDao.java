package dao;

import dto.VelicinaGradaDto;
import entity.VelicinaGrada;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class VelicinaGradaDao extends GenericDao<VelicinaGrada, VelicinaGradaDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected VelicinaGrada formEntity(VelicinaGradaDto dto) {
        return null;//TODO
    }

    @Override
    protected VelicinaGradaDto formDTO(VelicinaGrada entity) {
        VelicinaGradaDto velicinaGradaDto = null;
        if (entity != null) {
            velicinaGradaDto = new VelicinaGradaDto();
            VelicinaGrada velicinaGradaEntity = (VelicinaGrada) entity;
            velicinaGradaDto.setSifraVelicineGrada(velicinaGradaEntity.getSifraVelicineGrada());
            velicinaGradaDto.setNazivVelicineGrada(entity.getNazivVelicineGrada());
            velicinaGradaDto.setAktivan(velicinaGradaEntity.getAktivanVelicinaGrada());
        }
        return velicinaGradaDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM VelicinaGrada e";
    }

    public List<SelectItem> getSelectItemsForVelicinaGrada() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        result.add(new SelectItem("", "Odaberite"));
        List<VelicinaGradaDto> velicinaGradaDtoList = findAll();
        if (velicinaGradaDtoList != null && !velicinaGradaDtoList.isEmpty()) {
            for (VelicinaGradaDto velicinaGradaDto : velicinaGradaDtoList) {
                result.add(new SelectItem(velicinaGradaDto.getSifraVelicineGrada(), velicinaGradaDto.getNazivVelicineGrada()));
            }
        }
        return result;
    }

}
