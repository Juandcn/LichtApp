package co.edu.unab.mgads.jdcn.applicht.model

enum class LampStatus (val value:Int) {
    SWITCHON(1), SWITCHOF(2);

    fun description():String{
        return when(this){
            SWITCHON -> "Lampara Encendida"
            SWITCHOF -> "Lampara Apagada"
        }
    }
}