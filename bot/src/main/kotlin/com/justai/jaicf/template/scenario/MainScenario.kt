package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario() {

    init {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
                    sayRandom(
                        "Hello! How can I help?",
                        "Hi there! How can I help you?"
                    )
                    buttons(
                        "Location",
                        "What is your name?"
                    )
                }
            }
        }

        state("bye") {
            activators {
                intent("Bye")
            }

            action {

                reactions.sayRandom(
                    "See you!",
                    "Bye!"
                )
            }
        }

        state("smalltalk", noContext = true) {
            activators {
                anyIntent()
            }
            action {
                activator.caila?.topIntent?.answer?.let {
                    reactions.say(it)
                }
            }
        }

        state("CurrentLocation") {
            activators {
                intent("CurrentLocation")
            }
            action {
                println("answer: " + activator.caila?.slots?.get("LocationName"))
                reactions.sayRandom(
                    activator.caila?.slots?.get("LocationName")?.let
                    { Api.getWikiInfo(it) } ?: "Nothing interesting"
                )
            }

        }

        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that...",
                "Sorry, could you repeat please?"
            )
        }
    }
}