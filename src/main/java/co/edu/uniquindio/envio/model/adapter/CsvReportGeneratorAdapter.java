package co.edu.uniquindio.envio.model.adapter;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReportGeneratorAdapter implements ReportGenerator {
    @Override
    public void generateReport(List<EnvioDto> envios, String reportTitle, String filePath) throws IOException {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            // Escribir encabezado
            writer.append("ID Envio,Fecha,Origen,Destino,Estado,Costo\n");

            // Escribir datos
            for (EnvioDto envio : envios) {
                writer.append(String.format("%s,%s,%s,%s,%s,%.2f\n",
                        envio.id(),
                        envio.fechaCreacion().toString(),
                        envio.direccionOrigen(),
                        envio.direccionDestino(),
                        envio.estadoActual(),
                        envio.costo()));
            }
            System.out.println("Reporte CSV generado exitosamente en: " + file.getAbsolutePath());
        }
    }
}
