package es.um.atica.hexamod.tasks.application;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface PDFService {
    
    public ByteArrayInputStream pdfFromHtml(String template, Map<String,Object> variables) throws Exception;

}
