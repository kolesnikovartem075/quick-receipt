package org.quick.receipt.novapost.configuration;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.client.NovaPostClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@Configuration
public class HttpClientConfiguration {


    @Value("{nova.post.url}")
    private final String baseUrl;

    @Bean
    public NovaPostClient webClient() {
        WebClient webClient = getWebClient();

        var factory = getProxyFactory(webClient);
        return factory.createClient(NovaPostClient.class);
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    private HttpServiceProxyFactory getProxyFactory(WebClient webClient) {
        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(adapter)
                .build();
    }
}