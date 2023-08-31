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
package io.fusion.air.microservice.adapters.debug.examples;

import reactor.core.publisher.Flux;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Duration;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class CheckpointExample {

    public static void main(String[] args) throws InterruptedException {
        ReactorDebugAgent.init();

        checkPointExample();

        System.out.println("Waiting for 10 seconds...");
        Thread.sleep(15000);
        System.out.println("Program Execution over...");
    }

    public static void checkPointExample() {
        System.out.println("Checkpoint Example...");

        Flux.just(1, 2, 3, 4, 5)
            .log("Initial Data")
            .checkpoint("Initial Data Emission")
            .log("Adding a delay after the initial data emission")
            .delayElements(Duration.ofMillis(1000))
            .map(i -> {
                if (i == 3) throw new RuntimeException("An error occurred!");
                int x = i * 2;
                System.out.println("Processing: " + i + " * 2 = " + x);
                return x;
            })
            .checkpoint("After Data Transformation")
            .onErrorResume(e -> {
                e.printStackTrace();
                return Flux.just(-1);
            })
            .checkpoint("After Error Handling")
            .subscribe();
    }
}
