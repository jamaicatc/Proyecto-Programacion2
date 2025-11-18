package co.edu.uniquindio.envio.model.adapter;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto; // Importar EnvioDto
import java.io.IOException; // Importar IOException
import java.util.List;

public interface ReportGenerator {
    void generateReport(List<EnvioDto> envios, String reportTitle, String filePath) throws IOException;
}
