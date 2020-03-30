package com.soen390.conumap.Directions

import android.app.Activity
import android.content.res.Resources
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import com.soen390.conumap.Directions.DataClass.directionsResponse
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import org.json.JSONArray
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DirectionServiceTest {
    val mockMap  = mock<Map>()
    val mockActivity = mock<Activity>()

    var fakeJsonString= "{\n" +
            "  \"geocoded_waypoints\": [\n" +
            "    {\n" +
            "      \"geocoder_status\": \"OK\",\n" +
            "      \"place_id\": \"ChIJndMIH1sayUwROv2hl0YW1eg\",\n" +
            "      \"types\": [\n" +
            "        \"street_address\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"geocoder_status\": \"OK\",\n" +
            "      \"place_id\": \"ChIJ1WMdWM4byUwRHBdshiHAsRw\",\n" +
            "      \"types\": [\n" +
            "        \"establishment\",\n" +
            "        \"point_of_interest\",\n" +
            "        \"school\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"routes\": [\n" +
            "    {\n" +
            "      \"bounds\": {\n" +
            "        \"northeast\": {\n" +
            "          \"lat\": 45.5030704,\n" +
            "          \"lng\": -73.56395619999999\n" +
            "        },\n" +
            "        \"southwest\": {\n" +
            "          \"lat\": 45.4952882,\n" +
            "          \"lng\": -73.5784328\n" +
            "        }\n" +
            "      },\n" +
            "      \"copyrights\": \"Map data ©2020 Google\",\n" +
            "      \"legs\": [\n" +
            "        {\n" +
            "          \"distance\": {\n" +
            "            \"text\": \"1.7 km\",\n" +
            "            \"value\": 1742\n" +
            "          },\n" +
            "          \"duration\": {\n" +
            "            \"text\": \"8 mins\",\n" +
            "            \"value\": 466\n" +
            "          },\n" +
            "          \"end_address\": \"1400 Boulevard de Maisonneuve O, Montréal, QC H3G 2V8, Canada\",\n" +
            "          \"end_location\": {\n" +
            "            \"lat\": 45.4970552,\n" +
            "            \"lng\": -73.5784328\n" +
            "          },\n" +
            "          \"start_address\": \"490 Rue De La Gauchetière O, Montréal, QC H2Z 0B3, Canada\",\n" +
            "          \"start_location\": {\n" +
            "            \"lat\": 45.5025258,\n" +
            "            \"lng\": -73.56395619999999\n" +
            "          },\n" +
            "          \"steps\": [\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"38 m\",\n" +
            "                \"value\": 38\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 12\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5022247,\n" +
            "                \"lng\": -73.5641778\n" +
            "              },\n" +
            "              \"html_instructions\": \"Head <b>southwest</b> on <b>Rue De La Gauchetière O</b> toward <b>Côte du Beaver Hall</b>\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"yevtGv}~_M|@j@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5025258,\n" +
            "                \"lng\": -73.56395619999999\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.2 km\",\n" +
            "                \"value\": 191\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 64\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5030277,\n" +
            "                \"lng\": -73.5663158\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>right</b> at the 1st cross street onto <b>Côte du Beaver Hall</b>\",\n" +
            "              \"maneuver\": \"turn-right\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"{cvtGb__`MCd@EV?@EPENUt@m@fB{@dCEPER\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5022247,\n" +
            "                \"lng\": -73.5641778\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"1.0 km\",\n" +
            "                \"value\": 1048\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"4 mins\",\n" +
            "                \"value\": 222\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.4952882,\n" +
            "                \"lng\": -73.5737153\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>left</b> onto <b>Boulevard René-Lévesque O S</b>\",\n" +
            "              \"maneuver\": \"turn-left\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"}hvtGnl_`MGd@`B~@RPt@p@f@d@TRb@^|BrBfA`Ad@b@pAlA~AxAj@f@v@t@bA`ANL~BxBjCfCvCjClCjCXVbB`B\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5030277,\n" +
            "                \"lng\": -73.5663158\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.4 km\",\n" +
            "                \"value\": 417\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"2 mins\",\n" +
            "                \"value\": 142\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.4974225,\n" +
            "                \"lng\": -73.57810839999999\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>right</b> onto <b>Rue Bishop</b>\",\n" +
            "              \"maneuver\": \"turn-right\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"qxttGvz``Ms@`BO`@oAhDs@nBw@nBaA~BgB~E\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.4952882,\n" +
            "                \"lng\": -73.5737153\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"48 m\",\n" +
            "                \"value\": 48\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 26\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.4970552,\n" +
            "                \"lng\": -73.5784328\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>left</b> onto <b>Boulevard de Maisonneuve O</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>\",\n" +
            "              \"maneuver\": \"turn-left\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"{eutGdva`MfA~@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.4974225,\n" +
            "                \"lng\": -73.57810839999999\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"traffic_speed_entry\": [],\n" +
            "          \"via_waypoint\": []\n" +
            "        }\n" +
            "      ],\n" +
            "      \"overview_polyline\": {\n" +
            "        \"points\": \"yevtGv}~_M|@j@Cd@EXK`@cA|CaAvCMx@tBpA|AvA~FhFbGrFjG~FbHrGfDbDbB`Bs@`B_BjEkB~EiD~IfA~@\"\n" +
            "      },\n" +
            "      \"summary\": \"Boulevard René-Lévesque O S\",\n" +
            "      \"warnings\": [],\n" +
            "      \"waypoint_order\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"bounds\": {\n" +
            "        \"northeast\": {\n" +
            "          \"lat\": 45.5030704,\n" +
            "          \"lng\": -73.56395619999999\n" +
            "        },\n" +
            "        \"southwest\": {\n" +
            "          \"lat\": 45.4970552,\n" +
            "          \"lng\": -73.5784328\n" +
            "        }\n" +
            "      },\n" +
            "      \"copyrights\": \"Map data ©2020 Google\",\n" +
            "      \"legs\": [\n" +
            "        {\n" +
            "          \"distance\": {\n" +
            "            \"text\": \"1.7 km\",\n" +
            "            \"value\": 1735\n" +
            "          },\n" +
            "          \"duration\": {\n" +
            "            \"text\": \"8 mins\",\n" +
            "            \"value\": 495\n" +
            "          },\n" +
            "          \"end_address\": \"1400 Boulevard de Maisonneuve O, Montréal, QC H3G 2V8, Canada\",\n" +
            "          \"end_location\": {\n" +
            "            \"lat\": 45.4970552,\n" +
            "            \"lng\": -73.5784328\n" +
            "          },\n" +
            "          \"start_address\": \"490 Rue De La Gauchetière O, Montréal, QC H2Z 0B3, Canada\",\n" +
            "          \"start_location\": {\n" +
            "            \"lat\": 45.5025258,\n" +
            "            \"lng\": -73.56395619999999\n" +
            "          },\n" +
            "          \"steps\": [\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"38 m\",\n" +
            "                \"value\": 38\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 12\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5022247,\n" +
            "                \"lng\": -73.5641778\n" +
            "              },\n" +
            "              \"html_instructions\": \"Head <b>southwest</b> on <b>Rue De La Gauchetière O</b> toward <b>Côte du Beaver Hall</b>\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"yevtGv}~_M|@j@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5025258,\n" +
            "                \"lng\": -73.56395619999999\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.2 km\",\n" +
            "                \"value\": 191\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 64\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5030277,\n" +
            "                \"lng\": -73.5663158\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>right</b> at the 1st cross street onto <b>Côte du Beaver Hall</b>\",\n" +
            "              \"maneuver\": \"turn-right\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"{cvtGb__`MCd@EV?@EPENUt@m@fB{@dCEPER\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5022247,\n" +
            "                \"lng\": -73.5641778\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.4 km\",\n" +
            "                \"value\": 401\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"2 mins\",\n" +
            "                \"value\": 95\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.50012830000001,\n" +
            "                \"lng\": -73.56910409999999\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>left</b> onto <b>Boulevard René-Lévesque O S</b>\",\n" +
            "              \"maneuver\": \"turn-left\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"}hvtGnl_`MGd@`B~@RPt@p@f@d@TRb@^|BrBfA`Ad@b@pAlA\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5030277,\n" +
            "                \"lng\": -73.5663158\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.4 km\",\n" +
            "                \"value\": 409\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"2 mins\",\n" +
            "                \"value\": 121\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5022282,\n" +
            "                \"lng\": -73.5734075\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>right</b> onto <b>Rue Mansfield</b>\",\n" +
            "              \"maneuver\": \"turn-right\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"yvutGz}_`M]|@gApCSf@i@pAmAhDo@`B?@GJ?@Yt@_B`E\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.50012830000001,\n" +
            "                \"lng\": -73.56910409999999\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.7 km\",\n" +
            "                \"value\": 696\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"3 mins\",\n" +
            "                \"value\": 203\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.4970552,\n" +
            "                \"lng\": -73.5784328\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>left</b> onto <b>Boulevard de Maisonneuve O</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>\",\n" +
            "              \"maneuver\": \"turn-left\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"}cvtGxx``M~BxBjClCHHLNj@n@|@fAdAdAdAdAbA|@tAnATRvBjB~BvBfA~@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5022282,\n" +
            "                \"lng\": -73.5734075\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"traffic_speed_entry\": [],\n" +
            "          \"via_waypoint\": []\n" +
            "        }\n" +
            "      ],\n" +
            "      \"overview_polyline\": {\n" +
            "        \"points\": \"yevtGv}~_M|@j@Cd@EXK`@cA|CaAvCMx@tBpA|AvA~FhFvBpBeBnE}@xB}BjGGNyBvFjGfG`CpCjCjCxClCtIvH\"\n" +
            "      },\n" +
            "      \"summary\": \"Boulevard de Maisonneuve O\",\n" +
            "      \"warnings\": [],\n" +
            "      \"waypoint_order\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"bounds\": {\n" +
            "        \"northeast\": {\n" +
            "          \"lat\": 45.5040573,\n" +
            "          \"lng\": -73.56395619999999\n" +
            "        },\n" +
            "        \"southwest\": {\n" +
            "          \"lat\": 45.4970552,\n" +
            "          \"lng\": -73.5784328\n" +
            "        }\n" +
            "      },\n" +
            "      \"copyrights\": \"Map data ©2020 Google\",\n" +
            "      \"legs\": [\n" +
            "        {\n" +
            "          \"distance\": {\n" +
            "            \"text\": \"1.8 km\",\n" +
            "            \"value\": 1765\n" +
            "          },\n" +
            "          \"duration\": {\n" +
            "            \"text\": \"9 mins\",\n" +
            "            \"value\": 519\n" +
            "          },\n" +
            "          \"end_address\": \"1400 Boulevard de Maisonneuve O, Montréal, QC H3G 2V8, Canada\",\n" +
            "          \"end_location\": {\n" +
            "            \"lat\": 45.4970552,\n" +
            "            \"lng\": -73.5784328\n" +
            "          },\n" +
            "          \"start_address\": \"490 Rue De La Gauchetière O, Montréal, QC H2Z 0B3, Canada\",\n" +
            "          \"start_location\": {\n" +
            "            \"lat\": 45.5025258,\n" +
            "            \"lng\": -73.56395619999999\n" +
            "          },\n" +
            "          \"steps\": [\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.2 km\",\n" +
            "                \"value\": 206\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"1 min\",\n" +
            "                \"value\": 62\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.50088,\n" +
            "                \"lng\": -73.5651594\n" +
            "              },\n" +
            "              \"html_instructions\": \"Head <b>southwest</b> on <b>Rue De La Gauchetière O</b> toward <b>Côte du Beaver Hall</b>\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"yevtGv}~_M|@j@lBrAtA|@fAp@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5025258,\n" +
            "                \"lng\": -73.56395619999999\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.6 km\",\n" +
            "                \"value\": 616\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"3 mins\",\n" +
            "                \"value\": 193\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.5040573,\n" +
            "                \"lng\": -73.5716254\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>right</b> at the 2nd cross street onto <b>Boulevard Robert-Bourassa</b><div style=\\\"font-size:0.9em\\\">Parts of this road may be closed at certain times or days</div>\",\n" +
            "              \"maneuver\": \"turn-right\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"o{utGfe_`M[l@CFg@hAUf@]`AuArDOX_@dAiBdFIZS`@Wf@i@tAe@nAa@~@Sj@GNWr@s@lB\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.50088,\n" +
            "                \"lng\": -73.5651594\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"distance\": {\n" +
            "                \"text\": \"0.9 km\",\n" +
            "                \"value\": 943\n" +
            "              },\n" +
            "              \"duration\": {\n" +
            "                \"text\": \"4 mins\",\n" +
            "                \"value\": 264\n" +
            "              },\n" +
            "              \"end_location\": {\n" +
            "                \"lat\": 45.4970552,\n" +
            "                \"lng\": -73.5784328\n" +
            "              },\n" +
            "              \"html_instructions\": \"Turn <b>left</b> onto <b>Boulevard de Maisonneuve O</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>\",\n" +
            "              \"maneuver\": \"turn-left\",\n" +
            "              \"polyline\": {\n" +
            "                \"points\": \"kovtGtm``M~@z@vArA@@VTp@n@RRNN|@~@f@f@~BxBjClCHHLNj@n@|@fAdAdAdAdAbA|@tAnATRvBjB~BvBfA~@\"\n" +
            "              },\n" +
            "              \"start_location\": {\n" +
            "                \"lat\": 45.5040573,\n" +
            "                \"lng\": -73.5716254\n" +
            "              },\n" +
            "              \"travel_mode\": \"DRIVING\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"traffic_speed_entry\": [],\n" +
            "          \"via_waypoint\": []\n" +
            "        }\n" +
            "      ],\n" +
            "      \"overview_polyline\": {\n" +
            "        \"points\": \"yevtGv}~_MjD~B|CnB}AfDcDtIsB`Gk@hAeCpG_@bAs@lB~@z@xAtAhAdAhCjCjGfGVXhBvBjCjCfHlGfEvD\"\n" +
            "      },\n" +
            "      \"summary\": \"Boulevard Robert-Bourassa and Boulevard de Maisonneuve O\",\n" +
            "      \"warnings\": [],\n" +
            "      \"waypoint_order\": []\n" +
            "    }\n" +
            "  ],\n" +
            "  \"status\": \"OK\"\n" +
            "}"


    val jack = jacksonObjectMapper()

    val entireResponse: directionsResponse = jack.readValue(fakeJsonString, directionsResponse::class.java)

    @Test
    fun testGetGoogleMapRequestURL() {
//        var fakeDirectionService = DirectionService
        val mockDirectionService = mock<DirectionService>()
        val mockR = mock<Resources>()
        val mockOrigin:LatLng = mock<LatLng>()
        val originLat =  45.5030704
        val originLng = -73.56395619999999
        val mockDestination = mock<LatLng>()
        val destinationLat = 45.4952882
        val destinationLng = -73.5784328
        val transportationMode = "driving"

        val fakeAPIKey = "APIkey"
        val fakeDirectionAPI = "url"

//        val mockReturnedURL = fakeDirectionAPI + originLat + "," + originLng + "&destination=" + destinationLat + "," + destinationLng + "&mode=" + transportationMode + "&key=" + mockActivity.getString(
//            R.string.apiKey)
//
//        whenever(mockR.getString(any())).thenReturn(fakeAPIKey)
//        whenever(mockOrigin.latitude).thenReturn(originLat)
//        whenever(mockOrigin.longitude).thenReturn(originLng)
//
//        whenever(mockDestination.latitude).thenReturn(destinationLat)
//        whenever(mockDestination.longitude).thenReturn(destinationLng)

//        whenever(mockDirectionService.getGoogleMapRequestURL(mockActivity,mockOrigin, mockDestination, "driving", false)).thenReturn(mockReturnedURL)
//
//        val expectedURL =
//        assertEquals(mockDirectionService.getGoogleMapRequestURL(mockActivity, mockOrigin, mockDestination, transportationMode, false),
//        )

    }


    @Test
    fun testRoute() {
    }


    @Test
    fun testResetPathDrawing(){
        val dirService = mock<DirectionService>()

        val mockPolyline = mock<ArrayList<Polyline>> ()
        val mockPoly1 = mock<Polyline>()
        val mockPoly2 = mock<Polyline>()

        mockPolyline.addAll(listOf(mockPoly1,mockPoly2))

        whenever(dirService.polyline).thenReturn(mockPolyline)

        dirService.ResetPathDrawing()

        assert(mockPolyline.size==0)

    }

    @Test
    fun testDisplayOnScreenPath(){
        val dirService = DirectionService
        val path = Path
        val mockPath = mock<Path>()
        val mockListOfPath = mock<ArrayList<Directions>>()
        var mockN = 1

        val mockDirection1 = mock<Directions>

        mockListOfPath.add(mockDirection1)
        dirService.listOfPath = mockListOfPath

        whenever(mockDirection1.getTotalTimeText()).thenReturn("Mocked Time")
        whenever(mockDirection1.getDirectionText()).thenReturn("Direction Text")
        whenever(mockDirection1.getInfoPathText()).thenReturn("Info Path")
        whenever(mockDirection1.getTotalDistanceText()).thenReturn("Durantion Text")

        dirService.displayOnScreenPath(mockListOfPath, mockN)

        assertEquals(path._PathTotalDistanceText,mockListOfPath[0].getTotalDistanceText() )
        assertEquals(path._PathTotalTimeText, mockListOfPath[0].getTotalTimeText())
        assertEquals(path._infoPathText, mockListOfPath[0].getInfoPathText())
        

    }




}