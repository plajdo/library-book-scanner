package io.github.shardbytes.lbs.database

import io.github.shardbytes.lbs.gui.terminal.TermUtils
import io.github.shardbytes.lbs.objects.Group
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.PrintWriter

class ClassDatabase private constructor(){
	
	var classList: ArrayList<Group> = ArrayList()
	
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
			var jsonObject = JSONObject(textRead)
			var jsonArray: JSONArray = jsonObject.getJSONArray("groups")

			for(i in 0..jsonArray.length() - 1){
				classList.add(Group(jsonArray.get(i).toString()))
			}
			
		}catch(e: Exception){
			TermUtils.printerr("Class database not found, creating a new one")
			save()
		}

	}
	
	fun save(){
		var jsonObject = JSONObject()
		var jsonArray = JSONArray()
		when(classList.size){
			0 -> {}
			else -> {
				for(i in 0..classList.size - 1){ 
					jsonArray.put(classList.get(i).getName())
				}
			}
		}
		jsonObject.put("groups", jsonArray)
		var writer = PrintWriter("data" + File.separator + "groupList.json")
		writer.println(jsonObject.toString())
		writer.close()
		
	}
	
}