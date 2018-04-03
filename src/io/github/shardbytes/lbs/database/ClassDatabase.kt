package io.github.shardbytes.lbs.database

import io.github.shardbytes.lbs.gui.terminal.TermUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.PrintWriter

class ClassDatabase private constructor(){
	
	var classList: ArrayList<String> = ArrayList()
	
	init{
		load()
	}
	
	private object Holder{
		val INSTANCE: ClassDatabase = ClassDatabase()
	}
	
	companion object{
		val instance: ClassDatabase by lazy {
			Holder.INSTANCE
		}
		
	}
	
	fun load(){
		try{
			var textRead: String = File("data" + File.separator + "groupList.json").readText()
			var jsonObject: JSONObject = JSONObject(textRead)
			var jsonArray: JSONArray = jsonObject.getJSONArray("groups")

			for(i in 0..jsonArray.length() - 1){
				classList.add(jsonArray.get(i).toString())
			}
			
		}catch(e: Exception){
			TermUtils.printerr("Class database not found, creating a new one")
			save()
		}

	}
	
	fun save(){
		var jsonObject: JSONObject = JSONObject()
		var jsonArray: JSONArray = JSONArray()
		when(classList.size){
			0 -> {}
			else -> {
				for(i in 0..classList.size - 1){
						jsonArray.put(classList.get(i))
				}
			}
		}
		jsonObject.put("groups", jsonArray)
		var writer: PrintWriter = PrintWriter("data" + File.separator + "groupList.json")
		writer.println(jsonObject.toString())
		writer.close()
		
	}
	
}