package Models;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String crearPreferenciaPago(List<Item> items) throws Exception {
        // Configurar token
        MercadoPagoConfig.setAccessToken(accessToken);

        // Construir lista de ítems para la preferencia
        List<PreferenceItemRequest> itemRequests = new ArrayList<>();
        for (Item item : items) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(item.getNombre())
                    .quantity(item.getCantidad())
                    // Usar el precio unitario directamente
                    .unitPrice(BigDecimal.valueOf(item.getPrecio()))
                    .build();
            itemRequests.add(itemRequest);
        }

        // Construir la solicitud de preferencia
        PreferenceRequest request = PreferenceRequest.builder()
                .items(itemRequests)
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                .success("https://tu-dominio.com/success")
                                .failure("https://tu-dominio.com/failure")
                                .pending("https://tu-dominio.com/pending")
                                .build()
                )
                .autoReturn("approved")
                .binaryMode(true)  // Generar QR
                .build();

        // Crear preferencia en Mercado Pago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);

        // Devolver el pref_id en lugar del init point completo
        return preference.getId();
    }
}