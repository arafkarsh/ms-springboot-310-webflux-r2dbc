/**
 * (C) Copyright 2023 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.adapters.examples.debug;

import reactor.core.publisher.Flux;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class DoOnExample {

    public static final String line1 = ">---------------------------------------------------------";
    public static final String line2 = "------------------<";

    public static void main(String[] args) {
        doOnNextExample1();
        doOnNextExample2();
        doOnCompleteExample3();
        doOnTerminateExample4();
        doOnEachExample5();
    }

    public static void doOnNextExample1() {
        System.out.println("doOnNext Example 1 "+ line1);

        Flux.just("A", "B", "C")
                .doOnNext(item -> System.out.println("doOnNext called with item: " + item))
                .subscribe();

        System.out.println(line2+ line1);
    }

    public static void doOnNextExample2() {
        System.out.println("doOnNext Example 2 "+ line1);

        Flux.just("A", "B", "C", "D", "E", "F")
            .map(item -> {
                if ("F".equals(item)) {
                    throw new RuntimeException("Error Invalid VALUE!!!");
                }
                return item.toLowerCase();
            })
            .doOnError(error -> System.out.println("doOnError called with ERROR: " + error))
            .subscribe();

        System.out.println(line2+ line1);
    }

    public static void doOnCompleteExample3() {
        System.out.println("doOnComplete Example 3 "+ line1);
        Flux.just("A", "B", "C")
                .doOnComplete(() -> System.out.println("doOnComplete called"))
                .subscribe();

        System.out.println(line2+ line1);
    }

    public static void doOnTerminateExample4() {
        System.out.println("doOnNext Example 4 "+ line1);

        Flux.just("A", "B", "C")
                .map(item -> {
                    if ("B".equals(item)) {
                        throw new RuntimeException("Error");
                    }
                    return item.toLowerCase();
                })
                .doOnTerminate(() -> System.out.println("doOnTerminate called"))
                .subscribe();

        System.out.println(line2+ line1);
    }

    public static void doOnEachExample5() {
        System.out.println("doOnEach Example 5 "+ line1);

        Flux.just("A", "B", "C")
            .doOnEach(signal -> {
                if (signal.isOnNext()) {
                    System.out.println("doOnEach called with onNext: " + signal.get());
                } else if (signal.isOnError()) {
                    System.out.println("doOnEach called with onError: " + signal.getThrowable());
                } else if (signal.isOnComplete()) {
                    System.out.println("doOnEach called with onComplete");
                }
            })
            .subscribe();

        System.out.println(line2+ line1);
    }
}
