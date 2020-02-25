package bean;

import dao.DogadajDao;
import entity.Dogadaj;
import entity.Grad;
import entity.TipOrganizacijskeJedinice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDateTime;

@Stateless
public class TestBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    DogadajDao dogadajDao;

    public void testEntityManager() {


        Dogadaj dogadaj = new Dogadaj();
        dogadaj.setNazivDogadaja("Prvi događaj");
        dogadaj.setVrijemeOd(LocalDateTime.now());
        dogadaj.setVrijemeDo(LocalDateTime.now());
        Grad grad = new Grad();
        grad.setSifraGrada(44);
        dogadaj.setGrad(grad);

        entityManager.persist(dogadaj);
        entityManager.flush();
    }
}
