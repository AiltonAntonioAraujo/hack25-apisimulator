package br.caixa.gov.credito.apisimulador.client.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventHubSimulacaoProducer {

    private final EventHubProducerClient eventHubProducerClient;
    private final ObjectMapper objectMapper;
    private static Logger logger = LoggerFactory.getLogger(EventHubSimulacaoProducer.class);

    public EventHubSimulacaoProducer(
            @Value("${spring.cloud.azure.eventhubs.connection-string}") String connectionString,
            @Value("${spring.cloud.azure.eventhubs.event-hub-name}") String eventHubName) {
        this.eventHubProducerClient = new EventHubClientBuilder().connectionString(connectionString, eventHubName)
                .buildProducerClient();
        this.objectMapper = new ObjectMapper();
    }

    public void enviarMensagem(SimulacaoResponseDTO simulacaoResponse) {
        try {
            String mensagem = objectMapper.writeValueAsString(simulacaoResponse);
            eventHubProducerClient.send(List.of(new EventData(mensagem)));
            logger.info("Mensagem enviada para o Event Hub: {}", mensagem);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para o Event Hub", e);
        }
    }

    public void close() {
        if (eventHubProducerClient != null) {
            eventHubProducerClient.close();
        }
    }

}
