package es.um.atica.hexamod.users.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import es.um.atica.hexamod.shared.CucumberSpringConfiguration;
import es.um.atica.hexamod.users.adapters.rest.dto.UserDTO;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberSpringConfiguration {
    
    protected MvcResult mvcResult;

    @io.cucumber.java.Before
    public void setup() { super.setup(); }

    @Dado("una API ubicada en {string}")
    public void una_api_ubicada_en(String path) { setAPIPath(path); }

    @Dado("una documentación disponible en {string}")
    public void una_documentación_disponible_en(String docPath) throws Exception {
        MvcResult docResult = getMVC().perform(MockMvcRequestBuilders.get(docPath)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200,docResult.getResponse().getStatus());
    }

    @Dado("un usuario no autenticado")
    public void un_usuario_no_autenticado() { setJWT(); }

    @Dado("el usuario autenticado {string}")
    public void el_usuario_autenticado_user_acme_es(String user) {
        setJWT(user);
    }

    @Cuando("trata de obtener un listado de usuarios")
    public void trata_de_obtener_un_listado_de_usuarios() throws Exception {
        mvcResult = getMVC().perform(MockMvcRequestBuilders.get(getAPIPath())
            .with(getJWT())
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Entonces("obtiene un error de autenticación")
    public void obtiene_un_error_de_autenticacion() {
        assertEquals(401,mvcResult.getResponse().getStatus());
    }

    @Entonces("obtiene un error de autorización")
    public void obtiene_un_error_de_autorizacion() throws Exception {
        assertEquals(403,mvcResult.getResponse().getStatus());
        Map<String, String> error = getObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<Map<String,String>>() {});
        assertEquals("Access is denied",error.get("error"));
        assertEquals("GET",error.get("method"));
        assertEquals("user@acme.es",error.get("principal"));
        assertEquals("/hexamod/v1/user",error.get("path"));
        assertEquals("org.springframework.security.access.AccessDeniedException",error.get("exception"));
        assertEquals("403",error.get("status"));
    }

    @Entonces("obtiene una respuesta correcta")
    public void obtiene_una_respuesta_correcta() {
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Entonces("contiene una lista paginada de {int} páginas con {int} usuarios por página y un total de {int} elementos")
    public void contiene_una_lista_paginada_de_paginas_con_usuarios_por_pagina_y_un_total_de_elementos(int pages, int items, int total) throws Exception {
        PagedModel<EntityModel<UserDTO>> users = getObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<PagedModel<EntityModel<UserDTO>>>() {});
        assertEquals(pages, users.getMetadata().getTotalPages());
        assertEquals(items, users.getMetadata().getSize());
        assertEquals(total, users.getMetadata().getTotalElements());
    }

    @Entonces("el primer usuario de la lista se llama {string}")
    public void el_primer_usuario_de_la_lista_se_llama(String name) throws Exception {
        CollectionModel<EntityModel<UserDTO>> users = getObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<CollectionModel<EntityModel<UserDTO>>>() {});
        assertEquals(name, users.getContent().stream().findFirst().get().getContent().getName());
    }

}
