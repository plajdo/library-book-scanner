package io.github.shardbytes.lbs.database

class ClassDatabase private constructor(){
	
	init{
		
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
		
	}
	
	fun save(){
		
	}
	
}