package es.um.atica.hexamod.tasks.adapters.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import es.um.atica.hexamod.tasks.application.PDFService;

@Service
public class PDFServiceImpl implements PDFService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ServletContext servletContext;

    @Override
    public ByteArrayInputStream pdfFromHtml(String template, Map<String, Object> variables) throws Exception {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        //templateResolver.setPrefix("tasks/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext ctx = new WebContext(request, response, servletContext);
        ctx.setVariables(variables);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        // Obtain Base Path for template file
        String baseUrl = this.getClass().getClassLoader()
            .getResource(template+".html")
            .toURI().toURL().toString();
        baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        renderer.setDocumentFromString(templateEngine.process(template, ctx), baseUrl);
        renderer.layout();
        renderer.createPDF(bos);

        return new ByteArrayInputStream(bos.toByteArray());
    }
}
