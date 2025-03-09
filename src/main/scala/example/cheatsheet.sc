// Reglas de evaluación de variables en Scala
// Para definición de variable existen 4 formas:

// Usando var, def, lazy val y val, 
// las dos primeras (def & lazy val) simplemente alocan la dirección de memoria (puntero) sin ningún valor y 
// cuando el programa las vaya a utilizar, se "devuelve" y valida a qué equivalen o qué se pretende de dichas variables
// La dos últimas (var y val) hace las dos cosas al tiempo, crea el puntero a la dirección de memoria y guarda o ejecuta el valor

def myDefExample = 2      // evaluated when called
val myValExample = 2      // evaluated immediately
var myVarExample = 2      // evaluated immediately
lazy val myLazyValExample = 2 // evaluated once when needed

// Si se utiliza o llaman las variables myDefExample y myLazyValExample en ese momento el sistema se da cuenta que equivalen a 2
println(myDefExample) // println(myDefExample=2) -> println(2)
println(myLazyValExample) // println(myDefExample=2) -> println(2)

// Si se utiliza myValExample, el sistema ya sabe que equivale a 2
println(myValExample) // println(2)
println(myVarExample) // pinrtln(2)

// Para pasar argumentos a una función, existen 2 formas de evaluación en Scala
// La primera se denomina "call by value", en este caso se evalua inmediatamente (antes de ejecutar la función)
// y el valor es conocido por la función desde el inicio
def squareVal(x: Double): Unit = {  // call by value
    println(x * x)
}

// La segunda se denomina "call by name", en este caso existe una evaluación perezosa y el valor solo es conocido
// por la función en el momento en que se necesite usar dicho argumento
def squareName(x: => Double): Unit ={ // call by name
    println(x * x)
}

// También se pueden definir "listas" o "vectores" usando * al final
def myFct(bindings: Int*): Unit =  {  // bindings is a sequence of int, containing a varying # of arguments
    bindings.foreach(println)
}

