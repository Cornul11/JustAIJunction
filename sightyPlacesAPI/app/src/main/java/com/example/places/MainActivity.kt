package com.example.places

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var location_handler = LocationHandler()
        /*
        println(location_handler.getPlaceName(53.2122827, 6.56607809))
        println(location_handler.getPlaceName(53.2195244, 6.568807))
        println(location_handler.getLocationHistory())
        */

        val textView1: TextView = findViewById(R.id.textView1) as TextView
        textView1.text = location_handler.getPlaceName(53.2122827, 6.56607809)

        val textView2: TextView = findViewById(R.id.textView2) as TextView
        textView2.text = location_handler.getPlaceName(53.2195244, 6.568807)

        val textView3: TextView = findViewById(R.id.textView3) as TextView
        textView3.text = location_handler.getLocationHistory()[0]

    }
}



/*
accounting
airport
amusement_park
aquarium
art_gallery
atm
bakery
bank
bar
beauty_salon
bicycle_store
book_store
bowling_alley
bus_station
cafe
campground
car_dealer
car_rental
car_repair
car_wash
casino
cemetery
church
city_hall
clothing_store
convenience_store
courthouse
dentist
department_store
doctor
drugstore
electrician
electronics_store
embassy
fire_station
florist
funeral_home
furniture_store
gas_station
gym
hair_care
hardware_store
hindu_temple
home_goods_store
hospital
insurance_agency
jewelry_store
laundry
lawyer
library
light_rail_station
liquor_store
local_government_office
locksmith
lodging
meal_delivery
meal_takeaway
mosque
movie_rental
movie_theater
moving_company
museum
night_club
painter
park
parking
pet_store
pharmacy
physiotherapist
plumber
police
post_office
primary_school
real_estate_agency
restaurant
roofing_contractor
rv_park
school
secondary_school
shoe_store
shopping_mall
spa
stadium
storage
store
subway_station
supermarket
synagogue
taxi_stand
tourist_attraction
train_station
transit_station
travel_agency
university
veterinary_care
zoo
 */