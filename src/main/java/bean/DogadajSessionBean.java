package bean;

import dao.DogadajDao;
import dto.DogadajDto;
import exception.DogadajAppRuleException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/*
 * Session bean for event app
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DogadajSessionBean implements IDogadajSessionBean {

    @Inject
    private DogadajDao dogadajDao;


    /**
     * <p>create dogadaj</p>
     * @param dogadajDto
     * @return dogadajDto
     * @throws DogadajAppRuleException
     */
    public DogadajDto createDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException {
        return dogadajDao.create(dogadajDto);
    }

    /**
     * <p>edit dogadaj</p>
     * @param dogadajDto
     * @return
     * @throws DogadajAppRuleException
     */
    public void editDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException {
        dogadajDao.edit(dogadajDto);
    }

}
