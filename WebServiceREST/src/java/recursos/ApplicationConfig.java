
package recursos;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Max
 */
@javax.ws.rs.ApplicationPath("service")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(recursos.GrupoRecurso.class);
        resources.add(recursos.MateriaisResource.class);
        resources.add(recursos.NegociosResource.class);
        resources.add(recursos.PessoaResource.class);
        resources.add(recursos.RegistrosResource.class);
        resources.add(recursos.produtosResource.class);
    }
    
}
