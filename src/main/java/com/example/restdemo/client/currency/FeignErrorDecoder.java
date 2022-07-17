package com.example.restdemo.client.currency;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder errorDecoder = new Default();

  private final Decoder decoder;

  public FeignErrorDecoder(Decoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Exception decode(String methodKey, Response response) {

    switch (response.status()) {
      case 400:
        log.error("Invalid_base. Client requested rates for an unsupported base currency");
        break;
      case 401:
        log.error("Missing_app_id. Client did not provide an App ID.\\Invalid_app_id. Client provided an invalid App ID");
        break;
      case 403:
        log.error("Access_restricted. Access restricted for repeated over-use (status: 429), or other reason given in \'description\' (403).");
        break;
      case 404:
        log.error("Not_found. Client requested a non-existent resource/route.");
        break;
      case 429:
        log.error("Not_allowed. Client doesn'\\t have permission to access requested route/feature");
        break;
      default:
        log.error("Default error!");
        break;
    }
    return errorDecoder.decode(methodKey, response);
  }
}