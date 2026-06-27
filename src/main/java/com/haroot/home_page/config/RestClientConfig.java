package com.haroot.home_page.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.haroot.home_page.client.GoogleApiClient;
import com.haroot.home_page.client.GoogleClient;
import com.haroot.home_page.properties.GoogleApiProperty;
import com.haroot.home_page.properties.GoogleProperty;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
  private final GoogleProperty googleProperty;
  private final GoogleApiProperty googleApiProperty;

  @Bean
  RestClient googleRestClient() {
    return RestClient.builder()
        .baseUrl(googleProperty.getBaseUrl())
        .build();
  }

  @Bean
  RestClient googleApiRestClient() {
    return RestClient.builder()
        .baseUrl(googleApiProperty.getBaseUrl())
        .build();
  }

  // NOTE: Client(interface)の実装クラスを自動生成してBean登録している
  @Bean
  GoogleClient googleClient(RestClient googleRestClient) {
    RestClientAdapter adapter = RestClientAdapter.create(googleRestClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(GoogleClient.class);
  }

  // NOTE: Client(interface)の実装クラスを自動生成してBean登録している
  @Bean
  GoogleApiClient googleApiClient(RestClient googleApiRestClient) {
    RestClientAdapter adapter = RestClientAdapter.create(googleApiRestClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(GoogleApiClient.class);
  }
}
