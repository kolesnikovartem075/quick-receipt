package org.quick.receipt.novapost.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quick.receipt.novapost.client.NovaPostClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HttpClientConfiguration {


    @Value("${api.url}")
    private final String baseUrl;

    @Bean
    public NovaPostClient webClient() {
        WebClient webClient = getWebClient();

        var factory = getProxyFactory(webClient);
        return factory.createClient(NovaPostClient.class);
    }

    private WebClient getWebClient() {


        return WebClient.builder()
                .filter(logRequest())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .baseUrl(baseUrl)
                .build();
    }

    ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            StringBuilder sb = new StringBuilder("Request: ");
            sb.append(clientRequest.url()).append("\n");
            clientRequest.headers().forEach((name, values) -> sb.append("%s: %s".formatted(name, values)).append("\n"));
            log.info(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    private HttpServiceProxyFactory getProxyFactory(WebClient webClient) {
        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(adapter)
                .build();
    }
}