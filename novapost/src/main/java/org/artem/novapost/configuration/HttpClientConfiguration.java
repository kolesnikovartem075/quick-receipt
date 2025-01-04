package org.artem.novapost.configuration;

import org.artem.novapost.client.NovaPostClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public NovaPostClient webClient() {
        WebClient webClient = getWebClient();

        var factory = getProxyFactory(webClient);
        return factory.createClient(NovaPostClient.class);
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.novaposhta.ua")
                .build();
    }

    private HttpServiceProxyFactory getProxyFactory(WebClient webClient) {
        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(adapter)
                .build();
    }
}