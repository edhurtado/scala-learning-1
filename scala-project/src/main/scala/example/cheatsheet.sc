import scala.compiletime.ops.boolean
// ############################
// Handling & defining variables in Scala
// ############################

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

// ############################
// Defining Functions
// ############################
// Al parecer en Scala se pueden definir funciones de la misma forma en que se definen variables usando def, val var y lazy val
// La diferencia entre esas es que cuando no se utiliza def, se debe definir la función como una función anonima:
// (a: Int) => Int = a * 2

def doubleDefNum(a: Int): Int = 2 * a // Declaración usando def
// Para usar val, var y lazy val se hacen virtualmente de la misma forma, usando funciones anonimas
val doubleValNum = (a: Int) => 2 * a // Declaración usando val
var doubleVarNum = (a: Int) => {2 * a} // Declaración usando var
lazy val doubleLazyNum: (Int) => Int = (a) => 2* a // Declaración usando lazy val

// Utilizar '=>' simboliza en Scala que se está haciendo una transformación, es decir que implicitamente se refiere a una función
// Utilizar ':' simboliza en Scala que se está definiendo un tipo de datos
def myFunc(f: Int => Int) = f // Esto simboliza que myFunc recibe 'f', que es una función que recibe un Int y devuelve un Int
def myFunc2(a: Int, b: Int): Int = a + b // Esto simboliza que a, b son enteros y myFunc2 devuelve o es tipo entero


// ############################
// High Order Functions
// ############################
// Aquellas funciones que son capaces de recibir funciones como parametros o devolver otras funciones. Según lo que entiendo
// son como recursividad de funciones, funciones que pueden usar funciones... (aún en proceso de entendimiento)

// En este caso la función sumSubFunct es una función de orden superior que toma una función que recibe un entero
// para devolver un entero e internamente necesita y toma 2 enteros para devolver un entero
def sumSubFunct(f: Int => Int): (Int, Int) => Int = // Funcion que recibe función
  def sumf(a: Int, b: Int): Int = f(a) + f(b) // Internamente se define otra función que es la que suma el 
                                              // resultado de la aplicación de la función principal
  sumf  // Llamada a la función interna

// En este punto se define la misma función de suma, solo que sin definir la sub función (interna) sumf
def sumDirectFunct(f: Int => Int)(a: Int, b: Int): Int = f(a) + f(b) // sumDirectFunct recibe la función f así como a y b

// Se puede definir la funcion f de forma explicita o implicita a la hora de llamar cualquiera
// de las 2 funciones sumDirectFunct o sumSubFunct
def cube(x: Int) = x * x * x // Se define una función que devuelve el cubo de un numero

// Se puede llamar las ordenes de orden superior de la siguiente forma

sumSubFunct(x => x * x * x) (1, 10) // Esta forma se asemeja a las lambda functions de Python lambda x: x*X*X, esa es la función
                                    // Luego se define que se va a aplicar la suma de los cubos de 1 (1) y 100 (1e6)

// Igual forma podemos llamar la función de orden superior utilizando otra función pre-definida
sumDirectFunct(cube) (1, 10) // Igual que la anterior, va a coger los cubos de 1 (1) y 10 (1e6) y luego los va a sumar


// ############################
// Currying
// ############################

// Currying en Scala define la forma en que se pasan (y utilizan) argumentos dentro de una función
// Permite convertir una función que recibe n argumentos a convertirla en n funciones que recibe 1 argumento
// Por ejemplo:
def fC(a: Int, b: Int): Int // Uncurried, la función fC recibe al tiempo a y b
def fU(a: Int)(b: Int): Int // Curried, la funcion fU recibe primero a y luego b

// Puntualmente se puede definir una función real para sumar elementos
def sumUnCur(a: Int, b: Int): Int = a + b // Uncurried, recibe al tiempo a y b
val x = sumUnCur(1, 2) // Se llama la función directamente inyectando los parametros dando como resultado 3

// El curriying permite ejecutar parcialmente las funciones solo si es necesario, mientras tanto el sistema
// puede evaluar otras partes del codigo sin perder tiempo en cosas que no se utilizarán.
def sumCur(a: Int)(b: Int): Int = a + b // Curried, recibe primero el a y luego el b
val y = sumCur(1) // Se llama la función parcialmente, como queremos sumar 1 + 2 primero le pasamos el primer argumento
                    // es capaz de esperar sin ejecutarse, hasta que le pase el segundo argumento
val z = y(2)        // En z, se guarda el resultado de la función, en el momento en que a y le pasamos el segundo argumento 2
                    // dando como resultado 1 + 2 = 3
val w = sumCur(1)(2) // De esta forma también se puede usar la función

// Se puede convertir una función Curried en una Uncurried y viceversa de la siguiente forma:
val sumUncurried: (Int, Int) => Int = (a, b) => a + b
val curriedSum: Int => Int => Int = sumUncurried.curried // Convierte a una función curried
val resultCurried = curriedSum(3)(5)  // Utilizando la versión curried, imprime: 8

// Se puede convertir una Curried function en una un curried usando .uncurried
val uncurriedSum = Function.uncurried(curriedSum)
val resultUncurried = uncurriedSum(3, 5)  // Utilizando la versión uncurried, imprime: 8

// ############################
// Classes
// ############################

// De forma similar a como se hace en Python, se pueden definir clases indicando su tipo
// Se puede utilizar val y: Int, para indicar que dicha variable va a ser inyectada, 
// por lo que genera un get de dicha variable (no la pasa como tal)
// Se puede utilizar var z: Int, para indicar que dicha variable va a ser inyectada y puede ser modificada, 
// po rlo que genera un get y un set de dicha variable (no la pasa como tal)
class MyClass(x: Int, val y: Int, var z: Int): // Si no se define un argumeto de clase con val o var, se entiende 
                                               // que es un argumento interno/privado de la clase no accesible desde fuera
    // En una clase y en general en Scala se pueden crear Precondiciones que generan IllegalArgumentException si no se cumple
    // evitando que se creen o inicialicen los objetos
    require(y > 0, "y must be positive")    // require se puede usar para otros contextos, no solamente Clases

    // Se pueden definir constructores auxiliares, que funcionan de forma similar a como lo hace el 
    // __init__ dentro de Python, se pueden definir comportamientos y valores predeterminados al inicializar la clase
    def this (x: Int) = this(x, 10, 20) // Constructor auxiliar para definir que por defecto y=10 y z=20

    // Se pueden crear metodos públicos dentro de la clase, un método en general se define como una función
    // o conjunto de logica que se puede ejecutar dentro de la clase
    def printX(): Unit = {
    println(x)
    }
    
    // Los métodos privados son funciones que se utilizan o solo se pueden llamar dentro de la misma clase, se definen con 'private'
    private def secretMethod(): String = "I am secret"
    // En este caso, asumimos que en algún momento queremos validar que x sea positivo para que la clase funcione correctamente
    // por lo que no nos interesa saber en un contexto externo si un valor es positivo
    private def checkPositive(x: Int): Boolean =  x > 0    
    
    // Se pueden definir constantes/variables dentro de la clase, cuyo Scope será la misma clase, no puede ser accedida desde afuera
    // se define de la misma forma que se define una variable en Scala con cualquiera de: [def, val, var, lazy val]
    val nb3 = x + y // Se computa una única vez en la clase
    // De igual manera, se pueden definir variables que se utilizen únicamente dentro de la clase usando 'private'
    private val nb4 = x + z // Solo se podrá acceder dentro de la clase

end MyClass // En Scala al parecer se debe finalizar el bloque de definición de la clase usando 'end' (end-markers)
            // Igualmente, se pueden definir clases usando brackets {} y no colocar el end myClass

// Se puede crear una nueva instancia del tipo de objeto de la siguiente forma
new MyClass(1, 2, 3)

// Dentro de las clases de Scala, existe algo llamado override de metodos, lo que entiendo es que sirve cuando se tienen
// clases que actúan como interfaces (o fábricas), donde se definen métodos básicos, que deben ser mejorados
// o reemplazados cuando clases hijas los heredan
class Animal { // Clase general llamada Animal, define cualquier animal en general
    def sound(): Unit = println("Generic animal sound")
}
// El override permite reemplazar el comportamiento de un método heredado
class Dog extends Animal { // Se crea un 'Perro' basandose en la clase 'Animal'
    // Como un perro es un animal que hace un sonido, se hereda el metodo de sonido
    override def sound(): Unit = println("Bark") // Sin embargo, el sonido del perro es 'bark' / 'guau', por lo que
                                                 // se personaliza o sobre-escribe (override) el metodo
}

// En la realidad cuando se instancia un objeto de la clase 'Perro', si se usa el metodo 'sound', 
// generará "bark" en vez de "Generic animal sound"
val dog = new Dog
println(dog.sound()) // "Bark"


// ############################
// End Markers
// ############################

// Dentro de Scala se puede utilizar marcadores de finalización, de esta forma no es necesario tener brackets {}
// dentro de la logica, se puede escribir código normalmente y utilizar un marcador "end" para indicar el fin de la 
// lógica de dicho elemento, usualmente debe tener el nombre del elemento, si no se hace, cierra lo inmediatamete anterior
class MyClass2(a: Int, b: String):
    // body
end MyClass2

object MyObject:
    // body
end MyObject

object MyTrait:
    // body
end MyTrait

def myMethod(name: String): Unit =
    println(s"Hello $name")
end myMethod

val myVal: Int =
    42
end myVal