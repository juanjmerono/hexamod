package es.um.atica.hexamod.tasks.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;

import es.um.atica.hexamod.shared.CucumberSpringConfiguration;
import es.um.atica.hexamod.tasks.adapters.rest.dto.TaskDTO;
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
    public void el_usuario_autenticado(String user) { setJWT(user); }

    @Cuando("trata de obtener su listado de tareas")
    public void trata_de_obtener_un_listado_de_tareas() throws Exception {
        mvcResult = getMVC().perform(MockMvcRequestBuilders.get(getAPIPath())
            .with(getJWT())
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de obtener su listado de tareas en pdf")
    public void trata_de_obtener_su_listado_de_tareas_en_pdf() throws Exception {
        mvcResult = getMVC().perform(MockMvcRequestBuilders.get(getAPIPath()+"/pdf")
            .with(getJWT())
            .accept(MediaType.APPLICATION_PDF_VALUE)).andReturn();
    }

    @Cuando("trata de obtener su listado de tareas breves")
    public void trata_de_obtener_su_listado_de_tareas_breves() throws Exception {
        mvcResult = getMVC().perform(MockMvcRequestBuilders.get(getAPIPath()+"/short")
            .with(getJWT())
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de obtener su listado de tareas de menos de {int} minutos y que empiecen por {string}")
    public void trata_de_obtener_su_listado_de_tareas_de_menos_de_minutos(Integer duration, String desc) throws Exception {
        mvcResult = getMVC().perform(MockMvcRequestBuilders.get(getAPIPath()+"?search=duration<"+duration+";desc=='"+desc+"*'")
            .with(getJWT())
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Entonces("contiene un documento pdf de {int} bytes")
    public void contiene_un_documento_pdf(int bytes) throws FileNotFoundException, IOException {
        assertEquals("application/pdf",mvcResult.getResponse().getContentType());
        assertEquals(bytes, mvcResult.getResponse().getContentLength());
        File outputFile = new File("target/test.pdf");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(mvcResult.getResponse().getContentAsByteArray());
        }
    }

    @Entonces("obtiene un error de autenticación")
    public void obtiene_un_error_de_autenticacion() {
        assertEquals(401,mvcResult.getResponse().getStatus());
    }
    @Entonces("obtiene una respuesta correcta")
    public void obtiene_una_respuesta_correcta() {
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
    @Entonces("contiene una lista paginada de {int} página con {int} tareas por página y un total de {int} elementos")
    public void contiene_una_lista_paginada_de_página_con_tareas_por_pagina_y_un_total_de_elementos(int pages, int items, int total) throws Exception {
        PagedModel<EntityModel<TaskDTO>> tasks = getObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),new TypeReference<PagedModel<EntityModel<TaskDTO>>>() {});
        assertEquals(pages, tasks.getMetadata().getTotalPages());
        assertEquals(items, tasks.getMetadata().getSize());
        assertEquals(total, tasks.getMetadata().getTotalElements());
    }
    
}
