/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fanzone.data

object DataSource {
    val MLB_teams = listOf(
        "Atlanta Braves",
        "Arizona Diamondbacks",
        "Baltimore Orioles",
        "Boston Red Sox",
        "Chicago Cubs",
        "Chicago White Sox",
        "Cincinnati Reds",
        "Cleveland Guardians",
        "Colorado Rockies",
        "Detroit Tigers",
        "Houston Astros",
        "Kansas City Royals",
        "Los Angeles Angels",
        "Los Angeles Dodgers",
        "Miami Marlins",
        "Milwaukee Brewers",
        "Minnesota Twins",
        "New York Mets",
        "New York Yankees",
        "Oakland Athletics",
        "Philadelphia Phillies",
        "Pittsburgh Pirates",
        "San Diego Padres",
        "San Francisco Giants",
        "Seattle Mariners",
        "St. Louis Cardinals",
        "Tampa Bay Rays",
        "Texas Rangers",
        "Toronto Blue Jays",
        "Washington Nationals"
    )

    val teamAbbreviations:HashMap<String, String> = hashMapOf(
        "Atlanta Hawks" to "atl",
        "Atlanta Braves" to "atl",
        "Arizona Diamondbacks" to "ari",
        "Baltimore Orioles" to "bal",
        "Boston Red Sox" to "bos",
        "Chicago Cubs" to "chc",
        "Chicago White Sox" to "chw",
        "Cincinnati Reds" to "cin",
        "Cleveland Guardians" to "cle",
        "Colorado Rockies" to "col",
        "Detroit Tigers" to "det",
        "Houston Astros" to "hou",
        "Kansas City Royals" to "kc",
        "Los Angeles Angels" to "laa",
        "Los Angeles Dodgers" to "lad",
        "Miami Marlins" to "mia",
        "Milwaukee Brewers" to "mil",
        "Minnesota Twins" to "min",
        "New York Mets" to "nym",
        "New York Yankees" to "nyy",
        "Oakland Athletics" to "oak",
        "Philadelphia Phillies" to "phi",
        "Pittsburgh Pirates" to "pit",
        "San Diego Padres" to "sd",
        "San Francisco Giants" to "sf",
        "Seattle Mariners" to "sea",
        "St. Louis Cardinals" to "stl",
        "Tampa Bay Rays" to "tb",
        "Texas Rangers" to "tex",
        "Toronto Blue Jays" to "tor",
        "Washington Nationals" to "wsh"
    )

}