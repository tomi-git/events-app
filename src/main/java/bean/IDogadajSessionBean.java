package bean;

import dto.DogadajDto;
import exception.DogadajAppRuleException;

import javax.ejb.Local;

@Local
public interface IDogadajSessionBean {

	/**
	 * <p>create dogadaj</p>
	 * @param dogadajDto
	 * @return dogadajDto
	 * @throws DogadajAppRuleException
	 */
	public DogadajDto createDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException;

	/**
	 * <p>edit dogadaj</p>
	 * @param dogadajDto
	 * @return
	 * @throws DogadajAppRuleException
	 */
	public void editDogadaj(DogadajDto dogadajDto) throws DogadajAppRuleException;


}
