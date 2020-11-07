package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.LocationHandler

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

        state("History") {
            activators {
                intent("History")

            }
            action {
                val history = LocationHandler().getLocationHistory()
                reactions.say("Today you've seen: ")
                for (i in history){
                    reactions.say(i)
                }
                reactions.image("https://sun9-49.userapi.com/wIMP-eQtXxzFfeVdApgEQcBQDGSrZLGJz78lLQ/b08XFAwwiSo.jpg")
            }
        }

        state("CurrentLocation") {
            activators {
                intent("CurrentLocation")
            }

            action {
                val place = LocationHandler().getPlaceName(53.2122827, 6.56607809)
                val response = Api.getWikiInfo(place)

                reactions.sayRandom(
                        "In front of you you could see $response",
                        "On the left you could see $response",
                        "On the right you could see $response"
                )
                when (place) {
                    "University_of_Groningen" -> {
                        reactions.image("https://www.rug.nl/about-ug/images/homepage/placeholders/gebouwen-faculteiten/academiegebouw-vooraanzicht.jpg")
                    }
                    "Der_Aa-kerk" -> {
                        reactions.image("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Der_Aa-kerk_Groningen.jpg/1200px-Der_Aa-kerk_Groningen.jpg")
                    }
                    "Martinitoren" -> {
                        reactions.image("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Martini_Toren.JPG/440px-Martini_Toren.JPG")
                    }
                    "Groninger_Museum" -> {
                        reactions.image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Groninger_Museum_2.jpg/500px-Groninger_Museum_2.jpg")
                    }
                    else -> {
                        reactions.image("https://www.martinikerk.nl/images/Trouwerij%20in%20het%20koor.jpg")
                    }
                }
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