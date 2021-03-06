/**
 *  Child Water Sensor
 *
 *  Copyright 2017 Daniel Ogorchock
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Change History:
 *
 *    Date        Who            What
 *    ----        ---            ----
 *    2017-04-10  Dan Ogorchock  Original Creation
 *    2017-08-23  Allan (vseven) Added a generateEvent routine that gets info from the parent device.  This routine runs each time the value is updated which can lead to other modifications of the device.
 *    2018-06-02  Dan Ogorchock  Revised/Simplified for Hubitat Composite Driver Model
 *    2020-09-27  Dan Ogorchock  Tweaked metadata for new ST App, removed lastUpdated attribute
 *
 *  
 */
metadata {
	definition (name: "Child Water Sensor", namespace: "ogiewon", author: "Dan Ogorchock", ocfDeviceType: "x.com.st.d.sensor.moisture", vid: "generic-leak-2") {
		capability "Water Sensor"
		capability "Sensor"

//		attribute "lastUpdated", "String"
	}

	simulator {

	}

	tiles(scale: 2) {
		multiAttributeTile(name:"water", type: "generic", width: 6, height: 4){
			tileAttribute ("device.water", key: "PRIMARY_CONTROL") {
				attributeState("dry", label: "Dry", icon:"st.alarm.water.dry", backgroundColor:"#ffffff")
				attributeState("wet", label: "Wet", icon:"st.alarm.water.wet", backgroundColor:"#00a0dc")
			}
// 			tileAttribute("device.lastUpdated", key: "SECONDARY_CONTROL") {
//    				attributeState("default", label:'    Last updated ${currentValue}',icon: "st.Health & Wellness.health9")
//            }
		}
	}
}

def parse(String description) {
    log.debug "parse(${description}) called"
	def parts = description.split(" ")
    def name  = parts.length>0?parts[0].trim():null
    def value = parts.length>1?parts[1].trim():null
	if (name && value) {
        // Update device
        sendEvent(name: name, value: value)
//        // Update lastUpdated date and time
//        def nowDay = new Date().format("MMM dd", location.timeZone)
//        def nowTime = new Date().format("h:mm a", location.timeZone)
//        sendEvent(name: "lastUpdated", value: nowDay + " at " + nowTime, displayed: false)
    }
    else {
    	log.debug "Missing either name or value.  Cannot parse!"
    }
}


def installed() {
}
