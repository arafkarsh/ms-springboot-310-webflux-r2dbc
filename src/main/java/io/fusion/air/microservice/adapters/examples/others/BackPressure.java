package io.fusion.air.microservice.adapters.examples.others;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class BackPressure {

    public static void main(String[] args) {
        Flux<Long> fastPublisher = Flux.interval(Duration.ofMillis(1000));  // Emits every 1000 ms

        fastPublisher
                .doOnNext(item -> System.out.println(LocalDateTime.now()+" Published item: " + item))
                .onBackpressureBuffer(10)  // Buffer up to 10 items
                .subscribe(item -> {
                    // Simulate slow item processing
                    try {
                        Thread.sleep(3000);  // 3000 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(LocalDateTime.now()+" Consumed item: " + item);
                });

        // Keep application running
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
