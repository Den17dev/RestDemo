package com.example.restdemo.client.gif;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignGifErrorDecoder implements ErrorDecoder{

    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    private final Decoder decoder;

    public FeignGifErrorDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() != 200) {
            log.error("Can't get data from external service");
        }
        return errorDecoder.decode(methodKey, response);
    }
}

//        try {
//          DefaultResponse defaultResponse = (DefaultResponse) decoder.decode(response,
//                                                                             DefaultResponse.class);
//          if (defaultResponse != null) {
//            defaultResponse.setStatus(response.status());
//          } else {
//            defaultResponse = new DefaultResponse(false, response.status(), response
//            .reason(), null);
//          }
//          throw new VsvnClientException(getErrorMessage(defaultResponse), defaultResponse);
//        } catch (IOException e) {
//          log.warn("Decode {} with error {}", response.body(), e.getMessage());
//          throw new VsvnClientException(response.reason(),
//                                        new DefaultResponse(response.status(), response
//                                        .reason()));
//        }
//      }
