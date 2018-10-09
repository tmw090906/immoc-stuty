package com.ccb.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {

    @GetMapping("1")
    public String get1() {
        log.info("get1 start");
        String result = creatStr();
        log.info("get1 end");
        return result;
    }

    private String creatStr() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some string";
    }


    /**
     * WebFlux
     * @return Mono String
     */
    @GetMapping("2")
    public Mono<String> get2() {
        log.info("get2 start");
        Mono<String> monoResult = Mono.fromSupplier(this::creatStr);
        log.info("get2 end");
        return monoResult;
    }

    @GetMapping(value = "3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        log.info("get3 start");
        Flux<String> fluxResult = Flux.fromStream(
                IntStream.rangeClosed(1, 20)
                        .mapToObj(i -> {
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return  "flux data--" + i;
                        }));
        log.info("get3 end");
        return fluxResult;
    }


}
