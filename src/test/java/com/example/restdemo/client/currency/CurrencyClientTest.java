package com.example.restdemo.client.currency;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.restdemo.client.currency.model.CurrencyRequest;
import com.example.restdemo.client.currency.model.CurrencyResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurrencyClientTest {

  private static WireMockServer wireMockServer;

  private static CurrencyClient client;

  private static CurrencyRequest currencyRequest;

  private static final ObjectMapper mapper = new ObjectMapper();

  private final CurrencyServerHolder currencyServerHolder = new CurrencyServerHolder();

  @BeforeAll
  public static void setup() {
    wireMockServer = new WireMockServer(options().port(8081));
    wireMockServer.start();

    client = new CurrencyClient("http://localhost:8081",currencyRequest);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
  }

  @DisplayName("")
  @Test
  void successGetVsvns() throws JsonProcessingException {
    final String body = mapper.writeValueAsString(currencyServerHolder.currencyResponse);
    wireMockServer.stubFor(get(urlPathMatching("/api/latest.json"))
                               .willReturn(aResponse().withStatus(200).withBody(body)));

    final CurrencyResponse response = client.getCurrencyJson("RUB");

    assertEquals(54.199902, response.getRates().get("RUB"));
    assertEquals(123L, response.getRates());
  }
}