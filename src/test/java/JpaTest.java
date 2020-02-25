import entity.TipOrganizacijskeJedinice;
import org.junit.Test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class JpaTest {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
//
    }
    public  EntityManager getEntityManager() {
        return entityManager;
    }

    //@Test
    public   void createTipOrgJedinice() {

        TipOrganizacijskeJedinice tipOrganizacijskeJedinice = new TipOrganizacijskeJedinice();
        tipOrganizacijskeJedinice.setSifraTipOrgJedinice(5);
        tipOrganizacijskeJedinice.setNazivTipOrgJedinice("tip 5");
        tipOrganizacijskeJedinice.setAktivanTipOrgJedinice(Boolean.TRUE);
        entityManager.persist(tipOrganizacijskeJedinice);
        entityManager.flush();
//        getEntityManager().persist(tipOrganizacijskeJedinice);
//        getEntityManager().flush();

        //getEntityManager().getTransaction().commit();
    }


}

