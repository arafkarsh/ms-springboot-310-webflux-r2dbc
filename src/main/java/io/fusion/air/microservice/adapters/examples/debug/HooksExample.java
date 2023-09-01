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
import reactor.core.publisher.Hooks;

import java.time.LocalDateTime;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class HooksExample {

    public static void main(String[] args) {
        // Adding a hook
        Hooks.onEachOperator("Trace", original -> {
            System.out.println(LocalDateTime.now()+" Operator being executed: " + original);
            return original;
        });

        // creates a Flux stream that emits the values 1, 2, to 15
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
                // map each value to its double
                .map(i -> i * 2)
                // filter out values that are divisible by 3
                .filter(i -> i % 3 == 0);

        flux.subscribe(i -> System.out.println("Filtered Values: " + i));

        // Removing hooks when they are no longer needed
        Hooks.resetOnEachOperator("Trace");
    }
}
