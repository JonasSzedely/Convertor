# Java → Kotlin: Das große Nachschlagewerk

Von einfach bis fortgeschritten. Jeder Abschnitt zeigt links Java, rechts (darunter) das Kotlin-Äquivalent.

**Wie man sucht:** Mit `Strg+F` / `Cmd+F` nach dem Java-Schlüsselwort suchen, das du gerade brauchst — z. B. `static`, `instanceof`, `switch`, `Optional`, `synchronized`. Jeder Abschnitt hat eine Zeile **Stichwörter:** mit allen relevanten Begriffen, damit die Suche auch dann trifft, wenn der Begriff im Code selbst nicht vorkommt. Ganz unten gibt es zusätzlich einen alphabetischen Index (Abschnitt 25).

**Kotlin-Version:** 2.x · **Java-Version:** 17+ (Abweichungen für Java 8 sind markiert)

---

## Inhaltsverzeichnis

1. [Variablen, Konstanten, Basistypen](#1-variablen-konstanten-basistypen)
2. [Null-Sicherheit](#2-null-sicherheit)
3. [Strings](#3-strings)
4. [Operatoren und Vergleiche](#4-operatoren-und-vergleiche)
5. [Kontrollfluss: if, when, Schleifen](#5-kontrollfluss-if-when-schleifen)
6. [Funktionen und Methoden](#6-funktionen-und-methoden)
7. [Klassen, Konstruktoren, Properties](#7-klassen-konstruktoren-properties)
8. [Vererbung, Interfaces, abstrakte Klassen](#8-vererbung-interfaces-abstrakte-klassen)
9. [static, Singleton, companion object](#9-static-singleton-companion-object)
10. [data class, record, equals/hashCode/toString](#10-data-class-record-equalshashcodetostring)
11. [enum und sealed class](#11-enum-und-sealed-class)
12. [Casting, instanceof, Reflection-Basics](#12-casting-instanceof-reflection-basics)
13. [Collections: Listen, Sets, Maps](#13-collections-listen-sets-maps)
14. [Streams vs. Collection-Operationen vs. Sequences](#14-streams-vs-collection-operationen-vs-sequences)
15. [Lambdas, Funktionstypen, funktionale Interfaces](#15-lambdas-funktionstypen-funktionale-interfaces)
16. [Exceptions](#16-exceptions)
17. [Generics und Varianz](#17-generics-und-varianz)
18. [Extension Functions und Extension Properties](#18-extension-functions-und-extension-properties)
19. [Scope Functions: let, run, apply, also, with](#19-scope-functions-let-run-apply-also-with)
20. [Delegation: by lazy, by, observable](#20-delegation-by-lazy-by-observable)
21. [Nebenläufigkeit: Threads, Futures, Coroutines](#21-nebenläufigkeit-threads-futures-coroutines)
22. [Datei- und I/O-Operationen](#22-datei--und-io-operationen)
23. [Java-Interop: @Jvm-Annotationen und Fallstricke](#23-java-interop-jvm-annotationen-und-fallstricke)
24. [Fortgeschritten: inline, reified, typealias, value class, DSLs](#24-fortgeschritten-inline-reified-typealias-value-class-dsls)
25. [Stichwortindex](#25-stichwortindex)

---

## 1. Variablen, Konstanten, Basistypen

**Stichwörter:** var, val, final, primitive, int, Integer, boxing, Autoboxing, Typinferenz, var (Java 10), long, double, char, byte, Konstante, static final, const, Typumwandlung, cast, Overflow, unsigned

### 1.1 Lokale Variable, veränderbar

```java
// Java
int x = 0;
x = 5;
String name = "Anna";
```

```kotlin
// Kotlin
var x: Int = 0     // Typ ist optional, wird abgeleitet
x = 5
var name = "Anna"  // Typ String wird inferiert
```

> Achtung: In Kotlin heißen die Typen `Int`, `Long`, `Double`, `Boolean`, `Char` — immer großgeschrieben. `int` (klein) existiert nicht. Das ist der häufigste Anfängerfehler beim Umstieg.

### 1.2 Lokale Konstante (`final`)

```java
// Java
final int x = 0;
final var list = new ArrayList<String>();  // Java 10+
```

```kotlin
// Kotlin
val x = 0
val list = ArrayList<String>()
```

> `val` ist in Kotlin der Standardfall. Faustregel: Schreib immer `val`, bis der Compiler sich beschwert. `val` heißt „Referenz unveränderbar", nicht „Objekt unveränderbar" — der Inhalt einer `val list` kann sich weiterhin ändern.

### 1.3 Feld in einer Klasse

```java
// Java
public class Counter {
    private int x = 0;
    private final String id = "abc";
    protected List<String> items = new ArrayList<>();
}
```

```kotlin
// Kotlin
class Counter {
    private var x: Int = 0
    private val id: String = "abc"
    protected var items: MutableList<String> = mutableListOf()
}
```

> In Kotlin sind das keine Felder, sondern **Properties**: Der Compiler erzeugt automatisch Getter (und bei `var` auch Setter). Details in Abschnitt 7.

### 1.4 Konstanten auf Klassenebene

```java
// Java
public class Config {
    public static final int MAX = 100;
    public static final String URL = "https://example.com";
}
```

```kotlin
// Kotlin – Variante A: Top-Level (empfohlen)
const val MAX = 100
const val URL = "https://example.com"

// Variante B: im companion object, wenn es zur Klasse gehören soll
class Config {
    companion object {
        const val MAX = 100
    }
}
```

> `const` geht nur für Compile-Zeit-Konstanten: primitive Typen und `String`, auf Top-Level oder im `companion object`. Für alles andere (z. B. eine Liste) nimmt man `val` ohne `const`.

### 1.5 Zahlentypen und Umwandlung

```java
// Java
int i = 42;
long l = i;          // implizite Erweiterung
int back = (int) l;  // expliziter Cast
double d = 3.14;
float f = 3.14f;
byte b = 8;
```

```kotlin
// Kotlin
val i: Int = 42
val l: Long = i.toLong()   // KEINE implizite Erweiterung!
val back: Int = l.toInt()
val d: Double = 3.14
val f: Float = 3.14f
val b: Byte = 8
```

> Kotlin macht **keine** impliziten Zahlenkonvertierungen. `val l: Long = 42` funktioniert aber, weil `42` ein Literal ist und direkt als `Long` interpretiert wird.

### 1.6 Literale und Lesbarkeit

```java
// Java
int million = 1_000_000;
long big = 10_000_000_000L;
int hex = 0xFF;
int bin = 0b1010;
```

```kotlin
// Kotlin – identisch, nur ohne Oktal-Literale
val million = 1_000_000
val big = 10_000_000_000L
val hex = 0xFF
val bin = 0b1010
```

> Kotlin hat **keine** Oktalliterale (`017` in Java). Und: Kotlin kennt zusätzlich `UInt`, `ULong`, `UByte`, `UShort` für vorzeichenlose Zahlen.

### 1.7 Boxing / Autoboxing

```java
// Java
int primitive = 5;
Integer boxed = 5;
Integer a = 1000, b = 1000;
System.out.println(a == b);         // false! Referenzvergleich
System.out.println(a.equals(b));    // true
```

```kotlin
// Kotlin
val primitive: Int = 5        // wird zu int kompiliert
val boxed: Int? = 5           // nullable → wird zu Integer kompiliert
val a: Int? = 1000
val b: Int? = 1000
println(a == b)               // true  – == ruft equals() auf
println(a === b)              // false – === ist Referenzvergleich
```

> Kotlin hat nur `Int`. Ob daraus `int` oder `Integer` wird, entscheidet der Compiler: nullable oder generisch → `Integer`, sonst `int`.

---

## 2. Null-Sicherheit

**Stichwörter:** null, NullPointerException, NPE, Optional, @Nullable, @NonNull, Elvis, safe call, ?., ?:, !!, requireNonNull, Objects.requireNonNull, orElse, ifPresent, platform type, lateinit

### 2.1 Nullable-Deklaration

```java
// Java
String name = null;                   // immer erlaubt
@Nullable String maybe = null;        // nur eine Annotation, keine Garantie
```

```kotlin
// Kotlin
var name: String = "Anna"    // kann NIE null sein – Compilerfehler bei = null
var maybe: String? = null    // das Fragezeichen macht den Typ nullable
```

### 2.2 Null-Check vor Zugriff

```java
// Java
if (name != null) {
    System.out.println(name.length());
}
```

```kotlin
// Kotlin – Variante A: Safe Call
println(name?.length)   // gibt null aus, wenn name null ist

// Variante B: Smart Cast nach Check
if (name != null) {
    println(name.length)   // name ist hier automatisch String, nicht String?
}
```

### 2.3 Defaultwert bei null (Elvis-Operator)

```java
// Java
String result = name != null ? name : "unbekannt";
// oder:
String result = Objects.requireNonNullElse(name, "unbekannt");  // Java 9+
int len = name != null ? name.length() : 0;
```

```kotlin
// Kotlin
val result = name ?: "unbekannt"
val len = name?.length ?: 0
```

### 2.4 Elvis mit früher Rückkehr

```java
// Java
String process(String input) {
    if (input == null) return "leer";
    if (input.isEmpty()) throw new IllegalArgumentException("leer");
    return input.uppercase();
}
```

```kotlin
// Kotlin – ?: kann return oder throw auf der rechten Seite haben
fun process(input: String?): String {
    val safe = input ?: return "leer"
    require(safe.isNotEmpty()) { "leer" }
    return safe.uppercase()
}
```

### 2.5 Verkettete Zugriffe

```java
// Java
String city = null;
if (user != null && user.getAddress() != null) {
    city = user.getAddress().getCity();
}
// oder mit Optional:
String city = Optional.ofNullable(user)
        .map(User::getAddress)
        .map(Address::getCity)
        .orElse("?");
```

```kotlin
// Kotlin
val city = user?.address?.city ?: "?"
```

> Das ist der Punkt, an dem Kotlin sich für die meisten Java-Entwickler zum ersten Mal wirklich lohnt.

### 2.6 Not-null-Assertion (`!!`)

```java
// Java
Objects.requireNonNull(name).length();
```

```kotlin
// Kotlin
name!!.length   // wirft NPE, wenn name null ist
```

> `!!` ist ein Codesmell. Es sagt „ich weiß es besser als der Compiler". In sauberem Kotlin-Code kommt es fast nie vor. Wenn du es oft brauchst, ist meist die Typmodellierung falsch.

### 2.7 Nur ausführen, wenn nicht null

```java
// Java
if (name != null) {
    doSomething(name);
}
// oder:
Optional.ofNullable(name).ifPresent(this::doSomething);
```

```kotlin
// Kotlin
name?.let { doSomething(it) }
```

### 2.8 Optional vs. Nullable

```java
// Java
Optional<User> findUser(String id) { ... }

findUser("1").map(User::getName).orElse("?");
findUser("1").ifPresentOrElse(this::show, this::showError);   // Java 9+
findUser("1").orElseThrow(() -> new NotFoundException());
```

```kotlin
// Kotlin – Optional wird schlicht nicht gebraucht
fun findUser(id: String): User? { ... }

findUser("1")?.name ?: "?"
findUser("1")?.let(::show) ?: showError()
findUser("1") ?: throw NotFoundException()
```

> `Optional` in Kotlin zu benutzen gilt als Anti-Pattern (außer beim Aufruf von Java-APIs, die es zurückgeben).

### 2.9 lateinit — Initialisierung später

```java
// Java
@Autowired
private UserService service;    // wird vom Framework gesetzt
```

```kotlin
// Kotlin
@Autowired
lateinit var service: UserService   // nicht nullable, aber später gesetzt

// Prüfen, ob schon initialisiert:
if (::service.isInitialized) { ... }
```

> `lateinit` geht nur für `var`, nicht für primitive Typen und nicht für nullable Typen. Zugriff vor der Initialisierung wirft `UninitializedPropertyAccessException`.

### 2.10 Platform Types (Java-Rückgabewerte)

```java
// Java
public String getName() { return null; }   // keine Annotation
```

```kotlin
// Kotlin sieht den Typ als String! ("Platform Type")
val n = javaObj.name        // Typ: String! – Compiler prüft NICHTS
val n: String = javaObj.name    // knallt zur Laufzeit mit NPE
val n: String? = javaObj.name   // sicher – so solltest du es machen
```

> Deshalb lohnt es sich, in Java-Code `@Nullable`/`@NonNull` (z. B. von JSpecify oder org.jetbrains.annotations) zu setzen: Kotlin wertet sie aus und macht daraus echte nullable/non-null Typen.

---

## 3. Strings

**Stichwörter:** String, StringBuilder, concat, format, printf, Textblock, """, split, join, substring, trim, isEmpty, isBlank, equalsIgnoreCase, StringBuffer, chars, repeat, padStart, Template, Interpolation

### 3.1 Verkettung und Interpolation

```java
// Java
String first = "Anna", last = "Müller";
String msg = "Hallo " + first + " " + last + ", du bist " + age + " Jahre alt.";
String fmt = String.format("Hallo %s, du bist %d", first, age);
```

```kotlin
// Kotlin – String Templates
val msg = "Hallo $first $last, du bist $age Jahre alt."
val calc = "Nächstes Jahr: ${age + 1}"       // Ausdrücke in ${}
val prop = "Länge: ${first.length}"
val literal = "Preis: \$100"                  // Dollarzeichen escapen
```

### 3.2 Mehrzeilige Strings

```java
// Java 15+
String json = """
        {
          "name": "Anna"
        }
        """;
```

```kotlin
// Kotlin
val json = """
    {
      "name": "Anna"
    }
""".trimIndent()

val withMargin = """
    |Zeile 1
    |Zeile 2
""".trimMargin()
```

> Wichtig: Kotlin entfernt die Einrückung **nicht** automatisch — `trimIndent()` oder `trimMargin()` musst du selbst aufrufen. In Raw Strings funktionieren keine Escapes (`\n`), aber Templates (`$x`) funktionieren.

### 3.3 Häufige String-Operationen

```java
// Java
s.isEmpty();
s == null || s.isEmpty();
s.isBlank();                       // Java 11+
s.equalsIgnoreCase(t);
s.substring(0, 5);
s.split(",");                      // gibt String[]
String.join(", ", list);
s.repeat(3);                       // Java 11+
s.strip();                         // Java 11+
s.chars().forEach(...);
s.contains("x");
s.replace("a", "b");
```

```kotlin
// Kotlin
s.isEmpty()
s.isNullOrEmpty()                  // arbeitet direkt auf String?
s.isBlank(); s.isNullOrBlank()
s.equals(t, ignoreCase = true)
s.substring(0, 5)                  // oder: s.take(5)
s.split(",")                       // gibt List<String>
list.joinToString(", ")
s.repeat(3)
s.trim()
s.forEach { c -> ... }             // String ist iterierbar
"x" in s                           // contains als Operator
s.replace("a", "b")
```

### 3.4 Praktische Kotlin-Extras ohne Java-Pendant

```kotlin
s.take(5)             // erste 5 Zeichen, ohne IndexOutOfBounds
s.takeLast(5)
s.drop(5)             // ohne die ersten 5
s.substringBefore("@")
s.substringAfter("@")
s.substringAfterLast(".")
s.padStart(5, '0')
s.lines()             // splittet an Zeilenumbrüchen
s.reversed()
s.toIntOrNull()       // null statt NumberFormatException!
s.capitalize()        // veraltet → s.replaceFirstChar { it.uppercase() }
```

### 3.5 StringBuilder

```java
// Java
StringBuilder sb = new StringBuilder();
sb.append("a").append(1).append(true);
String out = sb.toString();
```

```kotlin
// Kotlin
val out = buildString {
    append("a")
    append(1)
    append(true)
}
```

### 3.6 Zahlen parsen

```java
// Java
int i = Integer.parseInt("42");             // wirft NumberFormatException
try { i = Integer.parseInt(s); } catch (NumberFormatException e) { i = 0; }
double d = Double.parseDouble("3.14");
```

```kotlin
// Kotlin
val i = "42".toInt()                // wirft NumberFormatException
val safe = s.toIntOrNull() ?: 0     // viel besser
val d = "3.14".toDouble()
val hex = "FF".toInt(16)
```

---

## 4. Operatoren und Vergleiche

**Stichwörter:** equals, ==, ===, compareTo, Comparable, Comparator, ternär, ternary, Bitoperatoren, shift, XOR, in, range, .., until, step, downTo

### 4.1 Gleichheit

```java
// Java
a == b          // Referenzvergleich (bei Objekten)
a.equals(b)     // inhaltlicher Vergleich, NPE wenn a null
Objects.equals(a, b);   // null-sicher
```

```kotlin
// Kotlin
a == b          // ruft equals() auf, null-sicher!
a === b         // Referenzvergleich
a != b          // ruft !equals() auf
a !== b
```

> Das ist genau umgekehrt zur Java-Intuition. In Kotlin ist `==` fast immer das, was du willst.

### 4.2 Ternärer Operator

```java
// Java
String s = x > 0 ? "positiv" : "negativ";
```

```kotlin
// Kotlin – es gibt KEINEN ternären Operator; if ist ein Ausdruck
val s = if (x > 0) "positiv" else "negativ"
```

### 4.3 Bitoperationen

```java
// Java
int a = x & y;
int b = x | y;
int c = x ^ y;
int d = ~x;
int e = x << 2;
int f = x >> 2;
int g = x >>> 2;
```

```kotlin
// Kotlin – Infix-Funktionen statt Symbole
val a = x and y
val b = x or y
val c = x xor y
val d = x.inv()
val e = x shl 2
val f = x shr 2
val g = x ushr 2
```

### 4.4 Ranges und Bereichsprüfungen

```java
// Java
if (x >= 1 && x <= 10) { ... }
if (list.contains(item)) { ... }
```

```kotlin
// Kotlin
if (x in 1..10) { ... }
if (x in 1..<10) { ... }        // exklusiv oberes Ende (früher: until)
if (x !in 1..10) { ... }
if (item in list) { ... }
if (c in 'a'..'z') { ... }
```

### 4.5 Sortierung

```java
// Java
list.sort(Comparator.comparing(Person::getLastName)
        .thenComparing(Person::getFirstName)
        .reversed());
Collections.sort(list);
```

```kotlin
// Kotlin
val sorted = list.sortedBy { it.lastName }
val sorted2 = list.sortedWith(compareBy({ it.lastName }, { it.firstName }))
val desc = list.sortedByDescending { it.age }
list.sort()                     // in-place, nur auf MutableList
val maxP = list.maxByOrNull { it.age }
```

### 4.6 Comparable implementieren

```java
// Java
class Person implements Comparable<Person> {
    public int compareTo(Person o) { return this.age - o.age; }
}
```

```kotlin
// Kotlin
class Person(val age: Int) : Comparable<Person> {
    override fun compareTo(other: Person): Int = age.compareTo(other.age)
}
// Danach funktionieren <, >, <=, >= direkt auf Person-Objekten!
```

---

## 5. Kontrollfluss: if, when, Schleifen

**Stichwörter:** if, else, switch, case, break, continue, for, while, do-while, foreach, enhanced for, label, goto, yield, fallthrough, Iterator, indices, withIndex

### 5.1 if als Ausdruck

```java
// Java
int max;
if (a > b) { max = a; } else { max = b; }
```

```kotlin
// Kotlin
val max = if (a > b) a else b

// mit Blöcken – der letzte Ausdruck ist der Rückgabewert
val result = if (a > b) {
    println("a gewinnt")
    a
} else {
    b
}
```

### 5.2 switch → when

```java
// Java
switch (x) {
    case 1:
        System.out.println("eins");
        break;
    case 2:
    case 3:
        System.out.println("zwei oder drei");
        break;
    default:
        System.out.println("andere");
}
```

```kotlin
// Kotlin
when (x) {
    1 -> println("eins")
    2, 3 -> println("zwei oder drei")
    else -> println("andere")
}
```

> Kein `break` nötig — es gibt kein Fallthrough.

### 5.3 switch als Ausdruck (Java 14+ vs. when)

```java
// Java 14+
String s = switch (x) {
    case 1 -> "eins";
    case 2, 3 -> "zwei oder drei";
    default -> {
        String tmp = compute();
        yield tmp;
    }
};
```

```kotlin
// Kotlin
val s = when (x) {
    1 -> "eins"
    2, 3 -> "zwei oder drei"
    else -> {
        val tmp = compute()
        tmp
    }
}
```

### 5.4 when mit Bedingungen (kein Java-Pendant)

```java
// Java
if (x < 0) { ... }
else if (x == 0) { ... }
else if (x < 10) { ... }
else { ... }
```

```kotlin
// Kotlin – when ohne Argument ersetzt if-else-Ketten
val kategorie = when {
    x < 0 -> "negativ"
    x == 0 -> "null"
    x < 10 -> "klein"
    else -> "groß"
}

// when mit Ranges, Typen und Collections
when (x) {
    in 1..9 -> "einstellig"
    in 10..99 -> "zweistellig"
    is String -> "kein Int"
    !in validValues -> "ungültig"
    else -> "?"
}
```

### 5.5 when mit Subject-Variable

```kotlin
// Kotlin – Ergebnis direkt binden
when (val response = api.fetch()) {
    is Success -> handle(response.data)
    is Error -> log(response.message)
}
```

### 5.6 for-Schleifen

```java
// Java
for (int i = 0; i < 10; i++) { ... }
for (int i = 10; i > 0; i--) { ... }
for (int i = 0; i < 10; i += 2) { ... }
for (String s : list) { ... }
for (Map.Entry<String,Integer> e : map.entrySet()) {
    System.out.println(e.getKey() + "=" + e.getValue());
}
```

```kotlin
// Kotlin
for (i in 0..<10) { ... }        // 0 bis 9
for (i in 0 until 10) { ... }    // identisch, ältere Schreibweise
for (i in 10 downTo 1) { ... }
for (i in 0..10 step 2) { ... }
for (s in list) { ... }
for ((key, value) in map) {
    println("$key=$value")
}
```

### 5.7 Index mitzählen

```java
// Java
for (int i = 0; i < list.size(); i++) {
    System.out.println(i + ": " + list.get(i));
}
```

```kotlin
// Kotlin
for ((i, item) in list.withIndex()) {
    println("$i: $item")
}
// oder
list.forEachIndexed { i, item -> println("$i: $item") }
// oder
for (i in list.indices) { ... }
```

### 5.8 while / do-while

```java
// Java – identisch in Kotlin
while (x > 0) { x--; }
do { x--; } while (x > 0);
```

```kotlin
// Kotlin
while (x > 0) { x-- }
do { x-- } while (x > 0)
```

### 5.9 break/continue mit Labels

```java
// Java
outer:
for (int i = 0; i < 10; i++) {
    for (int j = 0; j < 10; j++) {
        if (cond) break outer;
    }
}
```

```kotlin
// Kotlin – Label mit @
outer@ for (i in 0..<10) {
    for (j in 0..<10) {
        if (cond) break@outer
    }
}

// In einem forEach-Lambda gibt es kein break – aber:
list.forEach inner@{
    if (it == 3) return@inner    // wirkt wie continue
}
// Abbrechen geht mit einer echten Schleife oder:
list.takeWhile { it != 3 }.forEach { ... }
```

### 5.10 return aus Lambdas

```kotlin
// Kotlin – Stolperfalle!
fun test(list: List<Int>) {
    list.forEach {
        if (it == 3) return       // verlässt die GANZE Funktion test()
    }
    println("wird nicht erreicht, wenn 3 enthalten ist")
}

fun test2(list: List<Int>) {
    list.forEach {
        if (it == 3) return@forEach   // verlässt nur diese Iteration
    }
    println("wird immer erreicht")
}
```

---
## 6. Funktionen und Methoden

**Stichwörter:** method, function, fun, void, Unit, return, varargs, Überladung, overload, default parameter, named argument, static method, Top-Level, Rekursion, tailrec, Nothing, lokale Funktion

### 6.1 Grundform

```java
// Java
public int add(int a, int b) {
    return a + b;
}

private void log(String msg) {
    System.out.println(msg);
}
```

```kotlin
// Kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}

private fun log(msg: String) {   // kein Rückgabetyp = Unit (≈ void)
    println(msg)
}
```

> `public` ist in Kotlin der Default und wird weggelassen.

### 6.2 Expression Body

```java
// Java
public int add(int a, int b) { return a + b; }
public boolean isAdult() { return age >= 18; }
```

```kotlin
// Kotlin – Einzeiler ohne Klammern und return
fun add(a: Int, b: Int): Int = a + b
fun add2(a: Int, b: Int) = a + b            // Typ wird inferiert
fun isAdult() = age >= 18
```

### 6.3 void vs. Unit vs. Nothing

```java
// Java
void doSomething() { }
```

```kotlin
// Kotlin
fun doSomething() { }              // Rückgabetyp Unit, implizit
fun doSomething2(): Unit { }       // explizit, unüblich

// Nothing: Funktion kehrt NIE zurück
fun fail(msg: String): Nothing = throw IllegalStateException(msg)

val x = someNullable ?: fail("fehlt")   // x ist danach non-null
```

### 6.4 Default-Parameter statt Überladung

```java
// Java – Überladungen von Hand
public void connect(String host) { connect(host, 80, 5000); }
public void connect(String host, int port) { connect(host, port, 5000); }
public void connect(String host, int port, int timeout) { ... }
```

```kotlin
// Kotlin – eine einzige Funktion
fun connect(host: String, port: Int = 80, timeout: Int = 5000) { ... }

connect("example.com")
connect("example.com", 8080)
connect("example.com", timeout = 1000)     // Named Argument!
```

### 6.5 Named Arguments

```java
// Java – am Aufrufort nicht erkennbar, was true bedeutet
createUser("Anna", true, false, true);
```

```kotlin
// Kotlin
createUser(name = "Anna", isAdmin = true, isActive = false, sendMail = true)
```

### 6.6 varargs

```java
// Java
public int sum(int... numbers) {
    int total = 0;
    for (int n : numbers) total += n;
    return total;
}
sum(1, 2, 3);
int[] arr = {1,2,3};
sum(arr);
```

```kotlin
// Kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

sum(1, 2, 3)
val arr = intArrayOf(1, 2, 3)
sum(*arr)                    // Spread-Operator * ist Pflicht
```

### 6.7 Top-Level-Funktionen (statt Utility-Klassen)

```java
// Java
public final class StringUtils {
    private StringUtils() {}
    public static String slugify(String s) { ... }
}
StringUtils.slugify("Hallo Welt");
```

```kotlin
// Kotlin – Datei StringUtils.kt, keine Klasse nötig
fun slugify(s: String): String = ...

slugify("Hallo Welt")
```

> Aus Java heraus heißt das dann `StringUtilsKt.slugify(...)`. Mit `@file:JvmName("StringUtils")` ganz oben in der Datei kann man den Namen ändern.

### 6.8 Lokale (verschachtelte) Funktionen

```java
// Java – nicht möglich, nur als Lambda oder private Methode
```

```kotlin
// Kotlin
fun processOrder(order: Order) {
    fun validate(field: String, value: String) {
        if (value.isBlank()) throw IllegalArgumentException("$field fehlt")
    }
    validate("Name", order.name)
    validate("Adresse", order.address)
}
```

### 6.9 Infix-Funktionen

```java
// Java
map.put("a", 1);
```

```kotlin
// Kotlin
infix fun Int.times(str: String) = str.repeat(this)
2 times "Hi "     // "Hi Hi "

// Aus der Stdlib:
val pair = "a" to 1        // to ist eine infix fun
if (x shl 2 == y) { }
```

### 6.10 Tail-Rekursion

```java
// Java – tiefe Rekursion → StackOverflowError
```

```kotlin
// Kotlin – wird vom Compiler in eine Schleife übersetzt
tailrec fun factorial(n: Long, acc: Long = 1): Long =
    if (n <= 1) acc else factorial(n - 1, acc * n)
```

---

## 7. Klassen, Konstruktoren, Properties

**Stichwörter:** class, constructor, this, super, getter, setter, field, property, init, Sichtbarkeit, public, private, protected, internal, package-private, inner class, nested class, static nested, object expression, anonymous class

### 7.1 Einfache Klasse mit Konstruktor

```java
// Java
public class Person {
    private final String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

```kotlin
// Kotlin – eine Zeile
class Person(val name: String, var age: Int)
```

> Getter/Setter werden automatisch generiert. Aus Java rufst du weiterhin `person.getName()` auf, aus Kotlin `person.name`.

### 7.2 Primärkonstruktor mit Body (`init`)

```java
// Java
public class Rectangle {
    private final int w, h;
    private final int area;

    public Rectangle(int w, int h) {
        if (w <= 0) throw new IllegalArgumentException();
        this.w = w;
        this.h = h;
        this.area = w * h;
    }
}
```

```kotlin
// Kotlin
class Rectangle(private val w: Int, private val h: Int) {
    val area: Int = w * h

    init {
        require(w > 0) { "Breite muss positiv sein" }
    }
}
```

### 7.3 Mehrere Konstruktoren

```java
// Java
public class Point {
    int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public Point(int x) { this(x, 0); }
    public Point() { this(0, 0); }
}
```

```kotlin
// Kotlin – Variante A: Default-Werte (bevorzugt)
class Point(val x: Int = 0, val y: Int = 0)

// Variante B: Sekundärkonstruktoren, wenn wirklich nötig
class Point(val x: Int, val y: Int) {
    constructor(x: Int) : this(x, 0)
    constructor() : this(0, 0)
}
```

### 7.4 Eigene Getter und Setter

```java
// Java
public class Temperature {
    private double celsius;

    public double getFahrenheit() { return celsius * 9 / 5 + 32; }

    public void setCelsius(double c) {
        if (c < -273.15) throw new IllegalArgumentException();
        this.celsius = c;
    }
}
```

```kotlin
// Kotlin
class Temperature {
    var celsius: Double = 0.0
        set(value) {
            require(value >= -273.15)
            field = value        // "field" ist das Backing Field
        }

    val fahrenheit: Double
        get() = celsius * 9 / 5 + 32     // berechnet, kein Speicher

    var internalId: String = ""
        private set                       // Setter nur intern sichtbar
}
```

### 7.5 Sichtbarkeitsmodifikatoren

| Java | Kotlin | Bedeutung in Kotlin |
|---|---|---|
| `public` | `public` (Default) | überall sichtbar |
| `protected` | `protected` | nur in Klasse + Subklassen (NICHT im Package!) |
| *(package-private)* | — | gibt es nicht |
| — | `internal` | im gesamten Modul sichtbar |
| `private` | `private` | in der Klasse bzw. Datei |

```kotlin
// Kotlin
class Foo {
    private val a = 1        // nur in Foo
    protected val b = 2      // Foo + Subklassen
    internal val c = 3       // im ganzen Modul (Gradle-Modul)
    val d = 4                // public
}

private class Helper         // nur in dieser Datei sichtbar
```

### 7.6 Innere und verschachtelte Klassen

```java
// Java
public class Outer {
    private int x = 1;

    static class StaticNested { }        // kein Zugriff auf x
    class Inner { int get() { return x; } }   // Zugriff auf x
}
new Outer.StaticNested();
new Outer().new Inner();
```

```kotlin
// Kotlin – umgekehrter Default!
class Outer {
    private val x = 1

    class Nested                          // entspricht static nested
    inner class Inner { fun get() = x }   // "inner" nötig für Zugriff
}
Outer.Nested()
Outer().Inner()
```

> Merksatz: In Kotlin ist eine verschachtelte Klasse standardmäßig `static`. Das `inner`-Keyword macht sie zur echten inneren Klasse.

### 7.7 Anonyme Klassen / Objektausdrücke

```java
// Java
Runnable r = new Runnable() {
    @Override public void run() { System.out.println("läuft"); }
};

button.addListener(new ClickListener() {
    public void onClick() { }
    public void onLongClick() { }
});
```

```kotlin
// Kotlin
val r = Runnable { println("läuft") }        // SAM-Konvertierung

button.addListener(object : ClickListener {
    override fun onClick() { }
    override fun onLongClick() { }
})

// Objektausdruck ohne Supertyp – Ad-hoc-Objekt
val point = object {
    val x = 1
    val y = 2
}
```

---

## 8. Vererbung, Interfaces, abstrakte Klassen

**Stichwörter:** extends, implements, abstract, final, open, override, super, interface, default method, Mehrfachvererbung, diamond, sealed

### 8.1 Vererbung

```java
// Java – Klassen sind standardmäßig vererbbar
public class Animal {
    public void speak() { System.out.println("..."); }
}

public class Dog extends Animal {
    @Override
    public void speak() { System.out.println("Wuff"); }
}
```

```kotlin
// Kotlin – Klassen sind standardmäßig FINAL
open class Animal {
    open fun speak() { println("...") }
}

class Dog : Animal() {
    override fun speak() { println("Wuff") }
}
```

> Das ist einer der größten Unterschiede: In Kotlin musst du Vererbung explizit mit `open` erlauben. `override` ist ein Keyword, keine Annotation, und ist Pflicht.

### 8.2 Konstruktor der Basisklasse aufrufen

```java
// Java
public class Dog extends Animal {
    public Dog(String name) { super(name); }
}
```

```kotlin
// Kotlin
open class Animal(val name: String)

class Dog(name: String) : Animal(name)         // beachte: kein val bei name

// mit Sekundärkonstruktor:
class Cat : Animal {
    constructor(name: String) : super(name)
}
```

### 8.3 Interfaces

```java
// Java
public interface Drivable {
    int MAX_SPEED = 200;               // implizit static final
    void drive();
    default void honk() { System.out.println("Hup"); }   // Java 8+
    static Drivable create() { ... }
}

public class Car implements Drivable, Serializable {
    public void drive() { }
}
```

```kotlin
// Kotlin
interface Drivable {
    val maxSpeed: Int                  // abstrakte Property, kein Feld!
    fun drive()
    fun honk() { println("Hup") }      // Default-Implementierung

    companion object {
        fun create(): Drivable = ...   // "statische" Methode
    }
}

class Car : Drivable, Serializable {
    override val maxSpeed = 200
    override fun drive() { }
}
```

> Interfaces in Kotlin dürfen Properties deklarieren (ohne Backing Field) — das kann Java nicht.

### 8.4 Abstrakte Klassen

```java
// Java
public abstract class Shape {
    protected String name;
    public abstract double area();
    public void describe() { System.out.println(name + ": " + area()); }
}
```

```kotlin
// Kotlin
abstract class Shape(protected val name: String) {
    abstract fun area(): Double
    fun describe() = println("$name: ${area()}")
}
```

> `abstract` impliziert `open` — kein zusätzliches Keyword nötig.

### 8.5 Diamant-Problem / Mehrfache Default-Methoden

```java
// Java
interface A { default void f() { } }
interface B { default void f() { } }
class C implements A, B {
    public void f() { A.super.f(); }
}
```

```kotlin
// Kotlin
interface A { fun f() { } }
interface B { fun f() { } }
class C : A, B {
    override fun f() { super<A>.f() }
}
```

### 8.6 super aufrufen

```java
// Java
@Override public void speak() {
    super.speak();
    System.out.println("+ Wuff");
}
```

```kotlin
// Kotlin
override fun speak() {
    super.speak()
    println("+ Wuff")
}
```

---

## 9. static, Singleton, companion object

**Stichwörter:** static, static method, static field, static block, Singleton, Enum-Singleton, companion object, object, @JvmStatic, Utility-Klasse, Factory

### 9.1 Statische Methode

```java
// Java
public class MathUtils {
    public static int square(int x) { return x * x; }
}
MathUtils.square(4);
```

```kotlin
// Kotlin – Variante A: Top-Level-Funktion (idiomatisch)
fun square(x: Int) = x * x

// Variante B: companion object, wenn es zur Klasse gehört
class MathUtils {
    companion object {
        fun square(x: Int) = x * x
    }
}
MathUtils.square(4)
```

### 9.2 companion object mit Java-Interop

```kotlin
// Kotlin
class User(val name: String) {
    companion object {
        @JvmStatic                          // damit Java User.of() aufrufen kann
        fun of(name: String) = User(name)

        @JvmField
        val DEFAULT = User("anonym")        // echtes statisches Feld für Java
    }
}
```

```java
// Java – ohne @JvmStatic wäre es: User.Companion.of("x")
User u = User.of("Anna");
```

### 9.3 Singleton

```java
// Java
public class Registry {
    private static final Registry INSTANCE = new Registry();
    private Registry() { }
    public static Registry getInstance() { return INSTANCE; }
    public void register(String s) { }
}
Registry.getInstance().register("x");
```

```kotlin
// Kotlin – "object" IST das Singleton
object Registry {
    fun register(s: String) { }
}
Registry.register("x")
```

> Thread-sicher und lazy initialisiert, ohne dass du dich darum kümmern musst.

### 9.4 Statischer Initialisierungsblock

```java
// Java
public class Config {
    static Map<String,String> defaults = new HashMap<>();
    static {
        defaults.put("host", "localhost");
    }
}
```

```kotlin
// Kotlin
object Config {
    val defaults = mutableMapOf<String, String>()

    init {
        defaults["host"] = "localhost"
    }
}
```

### 9.5 Konstante in einer Klasse

```java
// Java
public class Http {
    public static final int TIMEOUT = 30;
}
```

```kotlin
// Kotlin
class Http {
    companion object {
        const val TIMEOUT = 30      // const → echtes static final im Bytecode
    }
}
```

---

## 10. data class, record, equals/hashCode/toString

**Stichwörter:** record, POJO, DTO, equals, hashCode, toString, copy, clone, Builder, Lombok, @Data, destructuring, componentN, Wertobjekt

### 10.1 Datenklasse

```java
// Java – Record (Java 16+)
public record Person(String name, int age) { }

// Java – klassisch, ~50 Zeilen mit Gettern, equals, hashCode, toString
```

```kotlin
// Kotlin
data class Person(val name: String, val age: Int)
```

Was `data class` automatisch generiert:
- `equals()` / `hashCode()` über alle Properties des Primärkonstruktors
- `toString()` → `Person(name=Anna, age=30)`
- `copy()`
- `component1()`, `component2()` … für Destructuring

### 10.2 copy() — der große Vorteil gegenüber Records

```java
// Java – manuell
Person older = new Person(p.name(), p.age() + 1);
```

```kotlin
// Kotlin
val older = p.copy(age = p.age + 1)
```

### 10.3 Destructuring

```java
// Java – nicht vorhanden (Record-Patterns ab Java 21 in switch/instanceof)
if (obj instanceof Person(String name, int age)) { ... }    // Java 21
```

```kotlin
// Kotlin
val (name, age) = person
val (_, onlyAge) = person          // Unterstrich = ignorieren

for ((key, value) in map) { }
list.forEach { (name, age) -> println("$name $age") }
```

### 10.4 equals/hashCode von Hand

```java
// Java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person p = (Person) o;
    return age == p.age && Objects.equals(name, p.name);
}
@Override
public int hashCode() { return Objects.hash(name, age); }
```

```kotlin
// Kotlin – nur nötig, wenn es KEINE data class ist
override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Person) return false
    return age == other.age && name == other.name
}
override fun hashCode(): Int = Objects.hash(name, age)
```

### 10.5 Builder-Pattern

```java
// Java
Person p = new Person.Builder()
        .name("Anna")
        .age(30)
        .city("Berlin")
        .build();
```

```kotlin
// Kotlin – Builder wird durch Default- und Named-Argumente überflüssig
val p = Person(name = "Anna", age = 30, city = "Berlin")
```

### 10.6 Fallstricke bei data class

```kotlin
// Nur Properties des PRIMÄRkonstruktors zählen für equals/hashCode:
data class User(val id: String) {
    var email: String = ""    // NICHT in equals/hashCode/toString!
}

// data class kann nicht abstract/open/sealed/inner sein
// Bei Arrays als Property: equals vergleicht Referenzen, nicht Inhalt!
data class Wrapper(val data: ByteArray)   // Compiler warnt zu Recht
```

---

## 11. enum und sealed class

**Stichwörter:** enum, enum class, values, valueOf, entries, ordinal, EnumMap, sealed, sealed interface, versiegelt, exhaustive, ADT, Union Type, Zustandsautomat

### 11.1 Enum

```java
// Java
public enum Status {
    ACTIVE("aktiv"), INACTIVE("inaktiv");

    private final String label;
    Status(String label) { this.label = label; }
    public String getLabel() { return label; }
}
Status.valueOf("ACTIVE");
Status.values();
```

```kotlin
// Kotlin
enum class Status(val label: String) {
    ACTIVE("aktiv"),
    INACTIVE("inaktiv");

    fun isOn() = this == ACTIVE
}
Status.valueOf("ACTIVE")
Status.entries              // seit Kotlin 1.9, ersetzt values()
Status.entries.first { it.label == "aktiv" }
```

### 11.2 Enum mit abstrakten Methoden

```java
// Java
public enum Op {
    PLUS { public int apply(int a, int b) { return a + b; } },
    MINUS { public int apply(int a, int b) { return a - b; } };
    public abstract int apply(int a, int b);
}
```

```kotlin
// Kotlin
enum class Op {
    PLUS { override fun apply(a: Int, b: Int) = a + b },
    MINUS { override fun apply(a: Int, b: Int) = a - b };

    abstract fun apply(a: Int, b: Int): Int
}
```

### 11.3 sealed class — geschlossene Typhierarchie

```java
// Java 17+
public sealed interface Result permits Success, Failure { }
record Success(String data) implements Result { }
record Failure(Exception e) implements Result { }
```

```kotlin
// Kotlin
sealed interface Result
data class Success(val data: String) : Result
data class Failure(val e: Exception) : Result
object Loading : Result

// when ist EXHAUSTIVE – kein else nötig, Compiler prüft Vollständigkeit
fun handle(r: Result): String = when (r) {
    is Success -> r.data              // Smart Cast!
    is Failure -> r.e.message ?: "?"
    Loading -> "lädt..."
}
```

> Wenn du später einen vierten Fall hinzufügst, meldet der Compiler jede `when`-Stelle, die ihn nicht behandelt. Das ist extrem wertvoll für Zustandsmodellierung.

### 11.4 Wann enum, wann sealed?

- **enum**: feste Menge von Werten *ohne* eigene Daten pro Instanz (bzw. gleiche Struktur).
- **sealed**: feste Menge von Typen, die *unterschiedliche* Daten tragen.

---

## 12. Casting, instanceof, Reflection-Basics

**Stichwörter:** instanceof, cast, ClassCastException, getClass, .class, ::class, KClass, Class, Smart Cast, as, as?, is, !is, Reflection, javaClass

### 12.1 Typprüfung und Cast

```java
// Java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
// Java 16+ Pattern Matching:
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

```kotlin
// Kotlin – Smart Cast
if (obj is String) {
    println(obj.length)      // obj ist hier automatisch String
}
if (obj !is String) return
println(obj.length)          // funktioniert auch hier
```

### 12.2 Unsicherer und sicherer Cast

```java
// Java
String s = (String) obj;                   // ClassCastException möglich
String s = obj instanceof String ? (String) obj : null;
```

```kotlin
// Kotlin
val s = obj as String       // wirft ClassCastException
val s = obj as? String      // gibt null statt Exception
val s = obj as? String ?: "default"
```

### 12.3 Klassenreferenzen

```java
// Java
Class<?> c = String.class;
Class<?> c2 = obj.getClass();
String name = c.getSimpleName();
```

```kotlin
// Kotlin
val c: KClass<String> = String::class      // Kotlin-Reflection
val jc: Class<String> = String::class.java // Java-Class
val c2 = obj::class
val c3 = obj.javaClass                     // = obj.getClass()
val name = c.simpleName
```

### 12.4 Smart-Cast-Grenzen

```kotlin
// Funktioniert NICHT bei var-Properties einer Klasse (könnte sich ändern):
class Box { var value: Any? = null }

fun test(b: Box) {
    if (b.value is String) {
        // println(b.value.length)   // Compilerfehler!
        val v = b.value
        if (v is String) println(v.length)   // so geht es
    }
}
```

---

## 13. Collections: Listen, Sets, Maps

**Stichwörter:** List, ArrayList, LinkedList, Set, HashSet, Map, HashMap, LinkedHashMap, TreeMap, Arrays.asList, List.of, Collections.unmodifiable, immutable, mutable, Array, Iterator, getOrDefault, computeIfAbsent, putIfAbsent

### 13.1 Erzeugen

```java
// Java
List<String> mutable = new ArrayList<>();
List<String> withItems = new ArrayList<>(List.of("a", "b"));
List<String> immutable = List.of("a", "b");            // Java 9+
Set<String> set = new HashSet<>(Set.of("a"));
Map<String,Integer> map = new HashMap<>();
Map<String,Integer> imm = Map.of("a", 1, "b", 2);
String[] arr = {"a", "b"};
int[] nums = new int[10];
```

```kotlin
// Kotlin
val mutable = mutableListOf<String>()
val withItems = mutableListOf("a", "b")
val immutable = listOf("a", "b")            // read-only
val set = mutableSetOf("a")
val readOnlySet = setOf("a", "b")
val map = mutableMapOf<String, Int>()
val imm = mapOf("a" to 1, "b" to 2)
val arr = arrayOf("a", "b")
val nums = IntArray(10)                     // entspricht int[]
val empty = emptyList<String>()
```

> **Wichtig:** `List` in Kotlin ist *read-only*, nicht *immutable*. Es gibt keine Garantie, dass sich die Liste nicht über eine andere Referenz ändert — nur, dass *du* sie über diese Referenz nicht ändern kannst.

### 13.2 Typ-Übersicht

| Java | Kotlin (read-only) | Kotlin (veränderbar) |
|---|---|---|
| `List<T>` | `List<T>` | `MutableList<T>` |
| `ArrayList<T>` | — | `ArrayList<T>` / `mutableListOf()` |
| `Set<T>` | `Set<T>` | `MutableSet<T>` |
| `Map<K,V>` | `Map<K,V>` | `MutableMap<K,V>` |
| `T[]` | `Array<T>` | `Array<T>` (Größe fix, Inhalt änderbar) |
| `int[]` | `IntArray` | `IntArray` |

### 13.3 Zugriff und Änderung

```java
// Java
list.get(0);
list.set(0, "x");
list.add("y");
list.remove("y");
list.size();
list.isEmpty();
map.get("a");
map.getOrDefault("a", 0);
map.put("a", 1);
map.computeIfAbsent("a", k -> new ArrayList<>()).add("x");
map.containsKey("a");
```

```kotlin
// Kotlin
list[0]                     // Operator statt get()
list[0] = "x"               // nur MutableList
list.add("y")
list.remove("y")
list.size
list.isEmpty()
map["a"]                    // gibt V? zurück
map.getOrDefault("a", 0)
map.getOrElse("a") { 0 }
map["a"] = 1
map.getOrPut("a") { mutableListOf() }.add("x")
"a" in map                  // containsKey
```

### 13.4 Sicherer Zugriff auf Elemente

```java
// Java
String s = list.isEmpty() ? null : list.get(0);
String last = list.isEmpty() ? null : list.get(list.size()-1);
```

```kotlin
// Kotlin
val s = list.firstOrNull()
val last = list.lastOrNull()
val at = list.getOrNull(5)
val orDefault = list.getOrElse(5) { "default" }
val first = list.first()          // wirft NoSuchElementException wenn leer
val match = list.firstOrNull { it.startsWith("a") }
```

### 13.5 Kopieren und Umwandeln

```java
// Java
List<String> copy = new ArrayList<>(list);
List<String> ro = Collections.unmodifiableList(list);
String[] arr = list.toArray(new String[0]);
List<String> back = Arrays.asList(arr);
Set<String> asSet = new HashSet<>(list);
```

```kotlin
// Kotlin
val copy = list.toMutableList()
val ro = list.toList()
val arr = list.toTypedArray()
val back = arr.toList()
val asSet = list.toSet()
val asMap = list.associateWith { it.length }
```

### 13.6 Nützliche Kotlin-Collection-Funktionen ohne direktes Java-Pendant

```kotlin
list.chunked(3)                      // in Blöcke à 3 zerlegen
list.windowed(3)                     // gleitendes Fenster
list.zip(otherList)                  // → List<Pair<A,B>>
list.partition { it.length > 3 }     // → Pair<List, List>
list.groupBy { it.first() }          // → Map<Char, List<String>>
list.associateBy { it.id }           // → Map<Id, Item>
list.distinctBy { it.email }
list.sumOf { it.price }
list.flatten()
list.reversed()
list.shuffled()
list.take(3); list.drop(3)
list.takeWhile { it < 10 }
list.ifEmpty { listOf("fallback") }
list.requireNoNulls()
list.filterNotNull()
list.minOrNull(); list.maxOrNull()
list.average()
```

---
## 14. Streams vs. Collection-Operationen vs. Sequences

**Stichwörter:** Stream, stream(), filter, map, collect, Collectors, groupingBy, reduce, flatMap, anyMatch, allMatch, count, distinct, sorted, limit, skip, parallelStream, Sequence, asSequence, lazy, terminal operation

### 14.1 Grundoperationen

```java
// Java
List<String> result = people.stream()
        .filter(p -> p.getAge() > 18)
        .map(Person::getName)
        .collect(Collectors.toList());
```

```kotlin
// Kotlin – kein .stream() und kein .collect() nötig
val result = people
    .filter { it.age > 18 }
    .map { it.name }
```

> In Kotlin arbeiten `filter`/`map` direkt auf Collections und geben sofort eine neue Liste zurück (eager). Für große Datenmengen oder lange Ketten nimmt man `asSequence()` (lazy, entspricht Java-Streams).

### 14.2 Operationen-Mapping

| Java Stream | Kotlin Collection |
|---|---|
| `.stream()` | *(entfällt)* oder `.asSequence()` |
| `.filter(p -> ...)` | `.filter { ... }` |
| `.map(f)` | `.map { ... }` |
| `.flatMap(f)` | `.flatMap { ... }` |
| `.mapToInt(...).sum()` | `.sumOf { ... }` |
| `.distinct()` | `.distinct()` / `.distinctBy { }` |
| `.sorted()` | `.sorted()` / `.sortedBy { }` |
| `.limit(n)` | `.take(n)` |
| `.skip(n)` | `.drop(n)` |
| `.anyMatch(p)` | `.any { }` |
| `.allMatch(p)` | `.all { }` |
| `.noneMatch(p)` | `.none { }` |
| `.findFirst()` | `.firstOrNull()` |
| `.count()` | `.count()` / `.size` |
| `.reduce(...)` | `.reduce { acc, e -> }` / `.fold(init) { }` |
| `.forEach(...)` | `.forEach { }` |
| `.collect(toList())` | *(entfällt)* |
| `.collect(toSet())` | `.toSet()` |
| `.collect(joining(", "))` | `.joinToString(", ")` |
| `.collect(groupingBy(f))` | `.groupBy { }` |
| `.collect(toMap(k, v))` | `.associate { k to v }` |
| `.collect(partitioningBy(p))` | `.partition { }` |
| `.min(cmp)` / `.max(cmp)` | `.minByOrNull { }` / `.maxByOrNull { }` |
| `.peek(...)` | `.onEach { }` |
| `.iterator()` | `.iterator()` |
| `.parallelStream()` | *(kein direktes Pendant → Coroutines)* |

### 14.3 Gruppieren

```java
// Java
Map<String, List<Person>> byCity = people.stream()
        .collect(Collectors.groupingBy(Person::getCity));

Map<String, Long> countByCity = people.stream()
        .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));

Map<String, List<String>> namesByCity = people.stream()
        .collect(Collectors.groupingBy(Person::getCity,
                 Collectors.mapping(Person::getName, Collectors.toList())));
```

```kotlin
// Kotlin
val byCity = people.groupBy { it.city }
val countByCity = people.groupingBy { it.city }.eachCount()
val namesByCity = people.groupBy({ it.city }, { it.name })
```

### 14.4 Aggregieren

```java
// Java
int sum = people.stream().mapToInt(Person::getAge).sum();
double avg = people.stream().mapToInt(Person::getAge).average().orElse(0);
Optional<Person> oldest = people.stream().max(Comparator.comparingInt(Person::getAge));
String joined = people.stream().map(Person::getName).collect(Collectors.joining(", ", "[", "]"));
```

```kotlin
// Kotlin
val sum = people.sumOf { it.age }
val avg = people.map { it.age }.average()
val oldest = people.maxByOrNull { it.age }
val joined = people.joinToString(", ", "[", "]") { it.name }
```

### 14.5 fold / reduce

```java
// Java
int total = list.stream().reduce(0, Integer::sum);
```

```kotlin
// Kotlin
val total = list.fold(0) { acc, x -> acc + x }   // mit Startwert
val total2 = list.reduce { acc, x -> acc + x }   // ohne, wirft bei leerer Liste
val running = list.runningFold(0) { a, b -> a + b }   // Zwischenergebnisse
```

### 14.6 Sequences (Lazy Evaluation)

```java
// Java – Streams sind immer lazy
long count = list.stream()
        .filter(s -> s.length() > 3)
        .map(String::toUpperCase)
        .limit(10)
        .count();
```

```kotlin
// Kotlin – asSequence() macht die Kette lazy
val count = list.asSequence()
    .filter { it.length > 3 }
    .map { it.uppercase() }
    .take(10)
    .count()

// Unendliche Sequenz
val fibs = generateSequence(Pair(0, 1)) { (a, b) -> Pair(b, a + b) }
    .map { it.first }
    .take(10)
    .toList()

// Sequence mit yield
val seq = sequence {
    yield(1)
    yieldAll(listOf(2, 3))
}
```

> Faustregel: unter ~1000 Elementen und wenigen Schritten ist die normale Collection-API schneller (keine Objektallokation für die Sequence). Bei großen Datenmengen, vielen Zwischenschritten oder `take()`-Abbrüchen lohnt sich `asSequence()`.

---

## 15. Lambdas, Funktionstypen, funktionale Interfaces

**Stichwörter:** Lambda, ->, Function, BiFunction, Supplier, Consumer, Predicate, Runnable, Callable, funktionales Interface, SAM, Methodenreferenz, ::, Closure, Higher-Order Function, it, invoke

### 15.1 Lambda-Syntax

```java
// Java
Runnable r = () -> System.out.println("hi");
Function<Integer,Integer> square = x -> x * x;
BiFunction<Integer,Integer,Integer> add = (a, b) -> a + b;
Predicate<String> isEmpty = String::isEmpty;
Consumer<String> printer = System.out::println;
Supplier<String> supplier = () -> "wert";
```

```kotlin
// Kotlin
val r: () -> Unit = { println("hi") }
val square: (Int) -> Int = { x -> x * x }
val add: (Int, Int) -> Int = { a, b -> a + b }
val isEmpty: (String) -> Boolean = String::isEmpty
val printer: (String) -> Unit = ::println
val supplier: () -> String = { "wert" }
```

### 15.2 Funktionstypen-Mapping

| Java | Kotlin |
|---|---|
| `Runnable` | `() -> Unit` |
| `Supplier<T>` | `() -> T` |
| `Consumer<T>` | `(T) -> Unit` |
| `BiConsumer<T,U>` | `(T, U) -> Unit` |
| `Function<T,R>` | `(T) -> R` |
| `BiFunction<T,U,R>` | `(T, U) -> R` |
| `Predicate<T>` | `(T) -> Boolean` |
| `UnaryOperator<T>` | `(T) -> T` |
| `Callable<T>` | `() -> T` |

### 15.3 Das implizite `it`

```java
// Java
list.forEach(item -> System.out.println(item));
```

```kotlin
// Kotlin – bei genau einem Parameter heißt er implizit "it"
list.forEach { println(it) }
list.filter { it.length > 3 }
```

### 15.4 Trailing Lambda

```java
// Java
executeWithRetry(3, () -> doWork());
```

```kotlin
// Kotlin – letztes Lambda darf aus den Klammern raus
fun executeWithRetry(times: Int, block: () -> Unit) { ... }

executeWithRetry(3) {
    doWork()
}

// Bei nur einem Parameter entfallen die Klammern ganz:
list.forEach { ... }
```

### 15.5 Higher-Order Functions

```java
// Java
public <T,R> List<R> transform(List<T> input, Function<T,R> mapper) {
    return input.stream().map(mapper).collect(Collectors.toList());
}
```

```kotlin
// Kotlin
fun <T, R> transform(input: List<T>, mapper: (T) -> R): List<R> =
    input.map(mapper)

// Funktion als Rückgabewert
fun multiplier(factor: Int): (Int) -> Int = { it * factor }
val double = multiplier(2)
double(5)      // 10
```

### 15.6 Methodenreferenzen

```java
// Java
Person::getName
String::length
System.out::println
Person::new
this::handle
```

```kotlin
// Kotlin
Person::name
String::length
::println
::Person
this::handle
```

### 15.7 SAM-Konvertierung (Java-Interfaces aus Kotlin)

```kotlin
// Kotlin – bei Java-Interfaces mit einer Methode automatisch:
button.setOnClickListener { println("Klick") }
executor.submit { doWork() }

// Für Kotlin-eigene Interfaces braucht man "fun interface":
fun interface Validator {
    fun validate(s: String): Boolean
}
val v = Validator { it.isNotEmpty() }
```

### 15.8 Lambda mit Receiver

```kotlin
// Kotlin – kein Java-Pendant, Basis für DSLs
fun buildConfig(block: Config.() -> Unit): Config {
    val c = Config()
    c.block()          // "this" im Lambda ist die Config
    return c
}

val config = buildConfig {
    host = "localhost"   // kein "it." nötig
    port = 8080
}
```

---

## 16. Exceptions

**Stichwörter:** try, catch, finally, throw, throws, checked exception, unchecked, RuntimeException, try-with-resources, use, AutoCloseable, Custom Exception, runCatching, Result, rethrow, Multi-Catch

### 16.1 try/catch/finally

```java
// Java
try {
    riskyOperation();
} catch (IOException | SQLException e) {
    log.error("Fehler", e);
} catch (Exception e) {
    throw new RuntimeException(e);
} finally {
    cleanup();
}
```

```kotlin
// Kotlin – kein Multi-Catch mit |
try {
    riskyOperation()
} catch (e: IOException) {
    log.error("Fehler", e)
} catch (e: SQLException) {
    log.error("Fehler", e)
} catch (e: Exception) {
    throw RuntimeException(e)
} finally {
    cleanup()
}
```

### 16.2 try als Ausdruck

```java
// Java
int value;
try { value = Integer.parseInt(s); }
catch (NumberFormatException e) { value = 0; }
```

```kotlin
// Kotlin
val value = try { s.toInt() } catch (e: NumberFormatException) { 0 }
// noch besser:
val value = s.toIntOrNull() ?: 0
```

### 16.3 Checked Exceptions

```java
// Java – throws ist Pflicht
public void read() throws IOException {
    Files.readString(path);
}
```

```kotlin
// Kotlin – es gibt KEINE checked exceptions
fun read() {
    Files.readString(path)      // kein throws nötig, kein try erzwungen
}

// Nur für Java-Aufrufer relevant:
@Throws(IOException::class)
fun read2() { ... }
```

### 16.4 try-with-resources

```java
// Java
try (BufferedReader r = new BufferedReader(new FileReader("f.txt"))) {
    return r.readLine();
}
```

```kotlin
// Kotlin – use {}
BufferedReader(FileReader("f.txt")).use { r ->
    return r.readLine()
}

// oder direkt:
File("f.txt").useLines { lines -> lines.count() }
```

### 16.5 Eigene Exceptions

```java
// Java
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) { super(msg); }
    public NotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
```

```kotlin
// Kotlin
class NotFoundException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)
```

### 16.6 Vorbedingungen prüfen

```java
// Java
if (age < 0) throw new IllegalArgumentException("age negativ");
Objects.requireNonNull(name, "name darf nicht null sein");
if (!isReady) throw new IllegalStateException("nicht bereit");
assert x > 0;
```

```kotlin
// Kotlin – Stdlib-Funktionen
require(age >= 0) { "age negativ" }            // IllegalArgumentException
requireNotNull(name) { "name fehlt" }
check(isReady) { "nicht bereit" }              // IllegalStateException
checkNotNull(config)
error("unerreichbar")                          // wirft IllegalStateException
TODO("noch nicht implementiert")               // wirft NotImplementedError
```

### 16.7 runCatching / Result

```java
// Java – kein Pendant in der Stdlib
```

```kotlin
// Kotlin
val result: Result<Int> = runCatching { s.toInt() }

result
    .onSuccess { println("ok: $it") }
    .onFailure { println("fehler: ${it.message}") }

val value = result.getOrDefault(0)
val value2 = result.getOrElse { 0 }
val value3 = result.getOrNull()
val mapped = result.map { it * 2 }
```

> Vorsicht: `runCatching` fängt auch `Error` und `CancellationException`. In Coroutines deshalb mit Bedacht einsetzen.

---

## 17. Generics und Varianz

**Stichwörter:** Generics, Typparameter, extends, super, Wildcard, ?, Kovarianz, Kontravarianz, out, in, PECS, Type Erasure, reified, bounded type, where

### 17.1 Generische Klasse und Funktion

```java
// Java
public class Box<T> {
    private T value;
    public T get() { return value; }
    public void set(T v) { this.value = v; }
}

public <T> List<T> singleton(T item) { ... }
```

```kotlin
// Kotlin
class Box<T> {
    var value: T? = null
}

fun <T> singleton(item: T): List<T> = listOf(item)
```

### 17.2 Beschränkte Typparameter

```java
// Java
public <T extends Comparable<T>> T max(T a, T b) { ... }
public <T extends Number & Comparable<T>> void f(T t) { ... }
```

```kotlin
// Kotlin
fun <T : Comparable<T>> max(a: T, b: T): T = if (a > b) a else b

fun <T> f(t: T) where T : Number, T : Comparable<T> { ... }
```

### 17.3 Varianz: `? extends` → `out`, `? super` → `in`

```java
// Java – Wildcards am Verwendungsort
void copy(List<? extends Number> source, List<? super Number> dest) { }
List<? extends Animal> animals = new ArrayList<Dog>();
```

```kotlin
// Kotlin – Deklarationsseitige Varianz (am Typ selbst)
interface Producer<out T> {      // T nur als Rückgabewert (kovariant)
    fun produce(): T
}
interface Consumer<in T> {       // T nur als Parameter (kontravariant)
    fun consume(item: T)
}

val animals: List<Animal> = listOf<Dog>()   // funktioniert, List ist <out E>

// Verwendungsseitige Varianz (wie Java-Wildcards) geht auch:
fun copy(source: List<out Number>, dest: MutableList<in Number>) { }
```

Merkhilfe: **out** = kommt raus (Producer, `? extends`) · **in** = geht rein (Consumer, `? super`).

### 17.4 Star-Projection

```java
// Java
List<?> anyList = ...;
```

```kotlin
// Kotlin
val anyList: List<*> = ...
```

### 17.5 Type Erasure umgehen mit `reified`

```java
// Java – geht nicht, Typ ist zur Laufzeit weg
public <T> T parse(String json, Class<T> clazz) {
    return gson.fromJson(json, clazz);
}
parse(json, Person.class);
```

```kotlin
// Kotlin – inline + reified
inline fun <reified T> parse(json: String): T =
    gson.fromJson(json, T::class.java)

val p: Person = parse(json)      // kein Class-Objekt mehr nötig!

// Auch möglich:
inline fun <reified T> List<*>.filterIsInstance(): List<T> =
    filter { it is T }.map { it as T }
```

---

## 18. Extension Functions und Extension Properties

**Stichwörter:** Extension, Erweiterungsfunktion, Utility, StringUtils, static helper, receiver, this, Extension Property, Scope, Shadowing

### 18.1 Utility-Methode → Extension

```java
// Java
public final class StringUtils {
    public static boolean isPalindrome(String s) {
        String clean = s.toLowerCase().replaceAll("[^a-z]", "");
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }
}
StringUtils.isPalindrome("Regal Lager");
```

```kotlin
// Kotlin
fun String.isPalindrome(): Boolean {
    val clean = lowercase().filter { it.isLetter() }
    return clean == clean.reversed()
}
"Regal Lager".isPalindrome()      // liest sich wie eine echte Methode
```

### 18.2 Extension Property

```kotlin
// Kotlin
val String.firstWord: String
    get() = substringBefore(" ")

val <T> List<T>.secondOrNull: T?
    get() = getOrNull(1)

"Hallo Welt".firstWord    // "Hallo"
```

### 18.3 Extensions auf nullable Typen

```kotlin
// Kotlin
fun String?.orEmptyTrimmed(): String = this?.trim() ?: ""

val s: String? = null
s.orEmptyTrimmed()        // funktioniert, kein NPE
```

### 18.4 Extensions für Java-Klassen

```kotlin
// Kotlin – funktioniert auch für Klassen, die du nicht besitzt
fun LocalDate.isWeekend(): Boolean =
    dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

fun <T> ResultSet.mapAll(mapper: (ResultSet) -> T): List<T> {
    val out = mutableListOf<T>()
    while (next()) out += mapper(this)
    return out
}
```

### 18.5 Wichtige Einschränkungen

```kotlin
// 1. Extensions werden STATISCH aufgelöst, nicht polymorph:
open class A
class B : A()
fun A.name() = "A"
fun B.name() = "B"
val x: A = B()
println(x.name())        // "A" – nicht "B"!

// 2. Eine Member-Funktion gewinnt immer gegen eine gleichnamige Extension
// 3. Extensions haben keinen Zugriff auf private Member der Klasse
```

---

## 19. Scope Functions: let, run, apply, also, with

**Stichwörter:** let, run, apply, also, with, takeIf, takeUnless, Scope Function, this, it, Kettenaufruf, Konfiguration, Nullcheck

### 19.1 Übersicht

| Funktion | Referenz | Rückgabewert | Typischer Einsatz |
|---|---|---|---|
| `let` | `it` | Lambda-Ergebnis | Null-Check, Transformation |
| `run` | `this` | Lambda-Ergebnis | Berechnung mit Objektkontext |
| `apply` | `this` | das Objekt | Konfiguration/Initialisierung |
| `also` | `it` | das Objekt | Seiteneffekte, Logging |
| `with` | `this` | Lambda-Ergebnis | mehrere Aufrufe auf einem Objekt |

### 19.2 apply — Objekt konfigurieren

```java
// Java
Person p = new Person();
p.setName("Anna");
p.setAge(30);
p.setCity("Berlin");
```

```kotlin
// Kotlin
val p = Person().apply {
    name = "Anna"
    age = 30
    city = "Berlin"
}
```

### 19.3 let — Null-Check und Transformation

```java
// Java
if (name != null) {
    String upper = name.toUpperCase();
    save(upper);
}
```

```kotlin
// Kotlin
name?.let {
    save(it.uppercase())
}

// Transformation in einer Kette
val len = readInput()?.trim()?.let { it.length * 2 } ?: 0
```

### 19.4 also — Seiteneffekt ohne Kettenbruch

```java
// Java
List<String> list = compute();
log.debug("Ergebnis: " + list);
return list;
```

```kotlin
// Kotlin
return compute().also { log.debug("Ergebnis: $it") }
```

### 19.5 with / run — mehrere Aufrufe

```java
// Java
StringBuilder sb = new StringBuilder();
sb.append("a");
sb.append("b");
String s = sb.toString();
```

```kotlin
// Kotlin
val s = with(StringBuilder()) {
    append("a")
    append("b")
    toString()
}

// run als Erweiterung
val s2 = StringBuilder().run {
    append("a")
    toString()
}
```

### 19.6 takeIf / takeUnless

```java
// Java
String result = s.length() > 5 ? s : null;
```

```kotlin
// Kotlin
val result = s.takeIf { it.length > 5 }
val result2 = s.takeUnless { it.isBlank() } ?: "default"
```

### 19.7 Entscheidungshilfe

- Objekt **konfigurieren** und zurückbekommen → `apply`
- Objekt **loggen/prüfen** und zurückbekommen → `also`
- **Etwas anderes** aus dem Objekt berechnen → `let` (mit `it`) oder `run` (mit `this`)
- Nur zur **Gruppierung** von Aufrufen, kein Kettenglied → `with`

---

## 20. Delegation: by lazy, by, observable

**Stichwörter:** lazy, Lazy Initialization, Singleton holder, double-checked locking, Delegation, by, Delegated Property, observable, vetoable, Map-Delegation, notNull, Decorator

### 20.1 Lazy Initialization

```java
// Java
private volatile ExpensiveThing thing;
public ExpensiveThing getThing() {
    if (thing == null) {
        synchronized (this) {
            if (thing == null) thing = new ExpensiveThing();
        }
    }
    return thing;
}
```

```kotlin
// Kotlin
val thing: ExpensiveThing by lazy { ExpensiveThing() }
// thread-safe by default; alternativ:
val t2 by lazy(LazyThreadSafetyMode.NONE) { ExpensiveThing() }
```

### 20.2 Klassendelegation (Decorator ohne Boilerplate)

```java
// Java – alle Methoden von Hand weiterreichen
public class LoggingList<T> implements List<T> {
    private final List<T> inner;
    public LoggingList(List<T> inner) { this.inner = inner; }
    public int size() { return inner.size(); }
    public boolean isEmpty() { return inner.isEmpty(); }
    // ... 20 weitere Methoden ...
    public boolean add(T t) { log(t); return inner.add(t); }
}
```

```kotlin
// Kotlin – "by" delegiert alles Nicht-Überschriebene automatisch
class LoggingList<T>(private val inner: MutableList<T>) : MutableList<T> by inner {
    override fun add(element: T): Boolean {
        println("add $element")
        return inner.add(element)
    }
}
```

### 20.3 Observable Properties

```java
// Java – PropertyChangeSupport oder von Hand
public void setName(String name) {
    String old = this.name;
    this.name = name;
    listeners.forEach(l -> l.changed(old, name));
}
```

```kotlin
// Kotlin
var name: String by Delegates.observable("initial") { prop, old, new ->
    println("$old -> $new")
}

// Änderung ablehnen können:
var age: Int by Delegates.vetoable(0) { _, _, new -> new >= 0 }

// Non-null ohne Initialwert:
var config: Config by Delegates.notNull()
```

### 20.4 Properties aus einer Map

```kotlin
// Kotlin – praktisch für JSON/Config
class User(map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}
val u = User(mapOf("name" to "Anna", "age" to 30))
```

---

## 21. Nebenläufigkeit: Threads, Futures, Coroutines

**Stichwörter:** Thread, Runnable, ExecutorService, CompletableFuture, async, await, synchronized, volatile, AtomicInteger, Coroutine, suspend, launch, withContext, Dispatchers, Flow, Channel, Mutex, structured concurrency

### 21.1 Thread starten

```java
// Java
new Thread(() -> doWork()).start();

ExecutorService pool = Executors.newFixedThreadPool(4);
pool.submit(() -> doWork());
pool.shutdown();
```

```kotlin
// Kotlin – Thread geht auch
thread { doWork() }

// idiomatisch: Coroutines
import kotlinx.coroutines.*

runBlocking {
    launch { doWork() }
}
```

### 21.2 Asynchron mit Ergebnis

```java
// Java
CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> fetchData());
CompletableFuture<Integer> g = f.thenApply(String::length);
String result = f.get();

CompletableFuture.allOf(f1, f2).join();
```

```kotlin
// Kotlin
suspend fun load(): Int = coroutineScope {
    val a = async { fetchData() }     // startet parallel
    val b = async { fetchOther() }
    a.await().length + b.await().length   // wartet auf beide
}
```

### 21.3 suspend-Funktionen

```java
// Java – blockierend
public String fetch() {
    return httpClient.send(request).body();   // blockiert den Thread
}
```

```kotlin
// Kotlin – nicht-blockierend
suspend fun fetch(): String = withContext(Dispatchers.IO) {
    httpClient.get(url)
}
```

> Eine `suspend`-Funktion kann nur aus einer anderen `suspend`-Funktion oder einem Coroutine-Scope aufgerufen werden. Sie blockiert keinen Thread, sondern gibt ihn während der Wartezeit frei.

### 21.4 Dispatcher

```kotlin
// Kotlin
Dispatchers.Default    // CPU-intensive Arbeit, Pool = Anzahl Kerne
Dispatchers.IO         // Blockierende I/O (Datei, Netzwerk, JDBC)
Dispatchers.Main       // UI-Thread (Android/JavaFX)
Dispatchers.Unconfined // selten sinnvoll
```

### 21.5 Timeout und Abbruch

```java
// Java
future.get(5, TimeUnit.SECONDS);
future.cancel(true);
```

```kotlin
// Kotlin
val result = withTimeoutOrNull(5000) { fetch() }   // null bei Timeout

val job = scope.launch { work() }
job.cancel()
job.join()
```

### 21.6 Synchronisation

```java
// Java
private final Object lock = new Object();
synchronized (lock) { counter++; }

private volatile boolean running = true;
private final AtomicInteger count = new AtomicInteger();
count.incrementAndGet();
```

```kotlin
// Kotlin
private val lock = Any()
synchronized(lock) { counter++ }

@Volatile private var running = true
private val count = AtomicInteger()
count.incrementAndGet()

// In Coroutines besser: Mutex (blockiert keinen Thread)
private val mutex = Mutex()
mutex.withLock { counter++ }
```

### 21.7 Streams von Werten: Flow

```java
// Java – Reactive Streams / RxJava
Flowable.fromIterable(list)
        .map(String::toUpperCase)
        .subscribe(System.out::println);
```

```kotlin
// Kotlin
flow {
    emit(1); emit(2); emit(3)
}
.map { it * 2 }
.filter { it > 2 }
.collect { println(it) }

// aus einer Liste
list.asFlow().map { it.uppercase() }.collect(::println)
```

---
## 22. Datei- und I/O-Operationen

**Stichwörter:** File, Files, Path, readAllLines, readString, write, BufferedReader, InputStream, Scanner, System.in, readLine, Resource, classpath

### 22.1 Datei lesen

```java
// Java
String content = Files.readString(Path.of("f.txt"));
List<String> lines = Files.readAllLines(Path.of("f.txt"));
byte[] bytes = Files.readAllBytes(Path.of("f.bin"));

try (Stream<String> s = Files.lines(Path.of("f.txt"))) {
    s.forEach(System.out::println);
}
```

```kotlin
// Kotlin
val content = File("f.txt").readText()
val lines = File("f.txt").readLines()
val bytes = File("f.bin").readBytes()

File("f.txt").forEachLine { println(it) }
File("f.txt").useLines { seq -> seq.count() }   // lazy, schließt automatisch
```

### 22.2 Datei schreiben

```java
// Java
Files.writeString(Path.of("f.txt"), "Inhalt");
Files.write(Path.of("f.txt"), lines);
try (BufferedWriter w = Files.newBufferedWriter(path)) { w.write("x"); }
```

```kotlin
// Kotlin
File("f.txt").writeText("Inhalt")
File("f.txt").appendText("mehr")
File("f.txt").printWriter().use { it.println("x") }
File("f.txt").bufferedWriter().use { it.write("x") }
```

### 22.3 Konsoleneingabe

```java
// Java
Scanner sc = new Scanner(System.in);
String line = sc.nextLine();
int n = sc.nextInt();
```

```kotlin
// Kotlin
val line = readlnOrNull()      // gibt String? zurück
val line2 = readln()           // wirft bei EOF
val n = readln().toInt()
```

### 22.4 Verzeichnisse

```java
// Java
Files.list(dir).forEach(...);
Files.walk(dir).filter(Files::isRegularFile).forEach(...);
Files.createDirectories(path);
Files.exists(path);
```

```kotlin
// Kotlin
File(dir).listFiles()?.forEach { }
File(dir).walkTopDown().filter { it.isFile }.forEach { }
File(dir).mkdirs()
File(path).exists()
File(path).extension       // ohne Punkt
File(path).nameWithoutExtension
File(a).resolve("b/c.txt") // Pfade zusammensetzen
```

---

## 23. Java-Interop: @Jvm-Annotationen und Fallstricke

**Stichwörter:** Interop, JvmStatic, JvmField, JvmOverloads, JvmName, Throws, Companion, INSTANCE, Kt-Suffix, getter, setter, Java aus Kotlin, Kotlin aus Java, SAM, Nullability-Annotation

### 23.1 Die wichtigsten Annotationen

| Annotation | Zweck |
|---|---|
| `@JvmStatic` | macht companion-object-Methode zur echten statischen Methode |
| `@JvmField` | erzeugt ein öffentliches Feld statt Getter/Setter |
| `@JvmOverloads` | erzeugt Java-Überladungen für Default-Parameter |
| `@JvmName("X")` | ändert den Namen im Bytecode |
| `@file:JvmName("X")` | benennt die generierte Klasse für Top-Level-Funktionen |
| `@Throws(IOException::class)` | fügt `throws` zur Signatur hinzu |
| `@JvmInline` | Value Class (Inline Class) |

### 23.2 @JvmOverloads

```kotlin
// Kotlin
@JvmOverloads
fun connect(host: String, port: Int = 80, timeout: Int = 5000) { }
```

```java
// Java – ohne @JvmOverloads gäbe es nur die 3-Parameter-Version
connect("host");
connect("host", 8080);
connect("host", 8080, 1000);
```

### 23.3 Was Java von Kotlin-Code sieht

```kotlin
// Kotlin
class Person(val name: String, var age: Int)
object Registry { fun register() {} }
class Foo { companion object { fun create() = Foo() } }
// Datei Utils.kt:
fun helper() {}
const val MAX = 10
```

```java
// Java
Person p = new Person("Anna", 30);
p.getName();
p.setAge(31);

Registry.INSTANCE.register();      // Singleton über INSTANCE
Foo.Companion.create();            // ohne @JvmStatic
UtilsKt.helper();                  // Top-Level → Dateiname + "Kt"
int m = UtilsKt.MAX;
```

### 23.4 Kotlin-Datentypen aus Java-Sicht

| Kotlin | Java |
|---|---|
| `Int` | `int` (bzw. `Integer` wenn nullable/generisch) |
| `Int?` | `Integer` |
| `String` | `String` (+ `@NotNull`) |
| `Any` | `Object` |
| `Unit` | `void` (bzw. `kotlin.Unit` bei Generics) |
| `Nothing` | `Void` |
| `List<T>` | `java.util.List<T>` (zur Laufzeit dieselbe Klasse!) |
| `MutableList<T>` | `java.util.List<T>` |
| `Array<Int>` | `Integer[]` |
| `IntArray` | `int[]` |

> Achtung: Java kann eine read-only `List` aus Kotlin problemlos verändern — die Read-only-Garantie besteht nur zur Kompilierzeit in Kotlin.

### 23.5 Typische Stolperfallen beim Umstieg

1. **`==` vs `equals`** — in Kotlin ist `==` inhaltlicher Vergleich, `===` der Referenzvergleich.
2. **Klassen sind `final`** — ohne `open` keine Vererbung, ohne `open` kein `override`. Mockito/Spring brauchen ggf. das `all-open`-Compiler-Plugin.
3. **Nested Class ist `static`** — für die Java-Semantik braucht man `inner`.
4. **Keine impliziten Zahlenkonvertierungen** — `toLong()`, `toInt()` etc. sind Pflicht.
5. **`return` in Lambdas** verlässt die äußere Funktion (siehe 5.10).
6. **Platform Types** aus Java sind ein NPE-Risiko (siehe 2.10).
7. **`lateinit`** wirft eine eigene Exception, nicht NPE.
8. **Sekundärkonstruktoren** rufen Initialisierer in anderer Reihenfolge auf als erwartet: `init`-Blöcke laufen als Teil des *Primär*konstruktors.
9. **Extension Functions sind statisch** — kein Polymorphismus (siehe 18.5).
10. **`data class` und Vererbung** vertragen sich schlecht — `copy()` und `equals()` kennen keine Subklassen-Felder.
11. **Arrays in `data class`** — `equals` vergleicht Referenzen; `equals`/`hashCode` von Hand schreiben.
12. **`internal` wird zu `public`** im Bytecode (mit verunstaltetem Namen) — Java kann darauf zugreifen.

---

## 24. Fortgeschritten: inline, reified, typealias, value class, DSLs

**Stichwörter:** inline, noinline, crossinline, reified, typealias, value class, inline class, JvmInline, operator overloading, invoke, DSL, Builder, contract, expect/actual, Multiplatform, context receiver, Annotation

### 24.1 inline-Funktionen

```kotlin
// Kotlin – Lambda-Objekt wird eingespart, Code wird an die Aufrufstelle kopiert
inline fun measure(block: () -> Unit): Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}

// noinline: dieses Lambda NICHT inlinen (z.B. weil es gespeichert wird)
inline fun f(inlined: () -> Unit, noinline stored: () -> Unit) { }

// crossinline: Lambda darf kein non-local return machen
inline fun runLater(crossinline block: () -> Unit) {
    Executors.newSingleThreadExecutor().submit { block() }
}
```

> `inline` lohnt sich vor allem bei Higher-Order-Funktionen. Bei normalen Funktionen bringt es meist nichts und bläht den Bytecode auf.

### 24.2 typealias

```java
// Java – kein Pendant
Map<String, List<Pair<Integer, String>>> data;
```

```kotlin
// Kotlin
typealias UserId = String
typealias Handler = (Event) -> Unit
typealias DataMap = Map<String, List<Pair<Int, String>>>

val data: DataMap = mapOf()
```

> `typealias` erzeugt **keinen** neuen Typ — nur einen zweiten Namen. Für echte Typsicherheit siehe Value Class.

### 24.3 Value Class (früher Inline Class)

```java
// Java – ein Wrapper-Objekt kostet Speicher
public record UserId(String value) { }
```

```kotlin
// Kotlin – zur Laufzeit meist einfach ein String, kein Wrapper-Objekt
@JvmInline
value class UserId(val value: String)

@JvmInline
value class Meters(val value: Double) {
    operator fun plus(other: Meters) = Meters(value + other.value)
}

fun findUser(id: UserId) { }
findUser(UserId("abc"))
// findUser("abc")   // Compilerfehler – genau das ist der Sinn
```

### 24.4 Operator Overloading

```java
// Java – nicht möglich
vector.add(other);
matrix.get(i, j);
```

```kotlin
// Kotlin
data class Vec(val x: Int, val y: Int) {
    operator fun plus(o: Vec) = Vec(x + o.x, y + o.y)
    operator fun minus(o: Vec) = Vec(x - o.x, y - o.y)
    operator fun times(f: Int) = Vec(x * f, y * f)
    operator fun unaryMinus() = Vec(-x, -y)
    operator fun get(i: Int) = if (i == 0) x else y
    operator fun contains(v: Int) = v == x || v == y
    operator fun compareTo(o: Vec) = 0
    operator fun invoke() = "$x/$y"
}

val v = Vec(1,2) + Vec(3,4)
val w = -v
val first = v[0]
if (3 in v) { }
v()      // ruft invoke() auf
```

Wichtige Operator-Namen: `plus` `minus` `times` `div` `rem` `inc` `dec` `unaryPlus` `unaryMinus` `not` `get` `set` `invoke` `contains` `compareTo` `rangeTo` `equals` `plusAssign` `iterator` `component1`.

### 24.5 Type-Safe Builder / DSL

```java
// Java
StringBuilder html = new StringBuilder();
html.append("<html><body><p>Text</p></body></html>");
```

```kotlin
// Kotlin – Lambda mit Receiver macht DSLs möglich
class HtmlBuilder {
    private val sb = StringBuilder()
    fun body(block: HtmlBuilder.() -> Unit) { sb.append("<body>"); block(); sb.append("</body>") }
    fun p(text: String) { sb.append("<p>$text</p>") }
    override fun toString() = sb.toString()
}
fun html(block: HtmlBuilder.() -> Unit) = HtmlBuilder().apply(block).toString()

val page = html {
    body {
        p("Text")
    }
}
```

> Dasselbe Muster steckt hinter Gradle Kotlin DSL, Jetpack Compose, Ktor-Routing und kotlinx.html.

### 24.6 Destrukturierende Deklarationen für eigene Klassen

```kotlin
// Kotlin – ohne data class
class Point(val x: Int, val y: Int) {
    operator fun component1() = x
    operator fun component2() = y
}
val (x, y) = Point(1, 2)
```

### 24.7 Annotationen definieren

```java
// Java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Timed {
    String value() default "";
}
```

```kotlin
// Kotlin
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Timed(val value: String = "")

// Use-site targets bei Properties:
class Foo(@field:JsonProperty("n") val name: String)
class Bar(@get:NotNull val x: String)
```

### 24.8 Kotlin-Features ohne Java-Pendant (Kurzliste)

- Named Arguments und Default-Parameter
- Extension Functions/Properties
- Smart Casts
- String Templates
- Scope Functions (`let`, `apply`, …)
- Delegated Properties (`by lazy`, `by`)
- Coroutines und `suspend`
- Operator Overloading und Infix-Funktionen
- Sealed Hierarchien mit exhaustivem `when` (Java hat das inzwischen ähnlich)
- Lambda mit Receiver / DSLs
- `reified` Typparameter
- Top-Level-Funktionen und -Properties
- Nullable Typen im Typsystem
- `data class` mit `copy()`
- Multiplatform (`expect`/`actual`)

### 24.9 Java-Features ohne Kotlin-Pendant

- Checked Exceptions
- Statische Imports von Feldern (`import static`) — in Kotlin nur `import` einzelner Namen
- Ternärer Operator (`?:` als `a ? b : c`)
- Primitive Typen als eigene Sprachebene (Kotlin abstrahiert das weg)
- `package-private`-Sichtbarkeit
- Anonyme innere Klassen mit Konstruktorargumenten in derselben Kurzform
- Wildcards in derselben Ausdrucksstärke am Deklarationsort
- `synchronized` als Methodenmodifikator (in Kotlin: `@Synchronized`)

---

## 25. Stichwortindex

Java-Begriff → Abschnitt

| Suchbegriff | Abschnitt |
|---|---|
| `abstract` | 8.4 |
| Annotation definieren | 24.7 |
| anonyme Klasse | 7.7 |
| `Arrays.asList` | 13.1, 13.5 |
| `assert` | 16.6 |
| Autoboxing | 1.7 |
| `AtomicInteger` | 21.6 |
| Bitoperationen | 4.3 |
| Builder-Pattern | 10.5, 24.5 |
| `Callable` | 15.2 |
| Cast / `(String) obj` | 12.2 |
| checked exception | 16.3 |
| `Class` / `.class` | 12.3 |
| `Collections.sort` | 4.5 |
| `Collectors.groupingBy` | 14.3 |
| `Collectors.joining` | 14.4 |
| `Comparable` | 4.6 |
| `Comparator` | 4.5 |
| `CompletableFuture` | 21.2 |
| `computeIfAbsent` | 13.3 |
| Konstruktor | 7.1–7.3 |
| `const` / `static final` | 1.4, 9.5 |
| `Consumer` | 15.2 |
| `copy` / Kopie eines Objekts | 10.2 |
| default method (Interface) | 8.3 |
| Destructuring | 10.3, 24.6 |
| `do-while` | 5.8 |
| `enum` | 11.1, 11.2 |
| `equals` / `hashCode` | 4.1, 10.4 |
| `ExecutorService` | 21.1 |
| `extends` | 8.1 |
| `File` / `Files` | 22.1, 22.2 |
| `final` (Variable) | 1.2 |
| `final` (Klasse) | 8.1 |
| `for`-Schleife | 5.6, 5.7 |
| `Function` | 15.1, 15.2 |
| Generics | 17 |
| Getter / Setter | 7.1, 7.4 |
| `HashMap` | 13.1, 13.3 |
| Higher-Order Function | 15.5 |
| `if/else` | 5.1 |
| `implements` | 8.3 |
| `import static` | 6.7, 24.9 |
| `instanceof` | 12.1 |
| Interface | 8.3 |
| innere Klasse | 7.6 |
| `Integer.parseInt` | 3.6 |
| Lambda | 15.1 |
| `lateinit` / Injection | 2.9 |
| `List.of` | 13.1 |
| Label / `break outer` | 5.9 |
| lazy init / double-checked locking | 20.1 |
| `Map.Entry` iterieren | 5.6, 13.3 |
| Methodenreferenz `::` | 15.6 |
| Mehrfachvererbung / Diamond | 8.5 |
| `new` | 7.1 |
| `null` / NPE | 2 |
| `Objects.requireNonNull` | 2.6, 16.6 |
| `Optional` | 2.8 |
| Operator überladen | 24.4 |
| `package-private` | 7.5 |
| POJO / DTO | 10.1 |
| `Predicate` | 15.2 |
| `private` / `public` / `protected` | 7.5 |
| `record` | 10.1 |
| Reflection | 12.3 |
| `Runnable` | 15.1, 21.1 |
| `sealed` | 11.3 |
| `Scanner` / `System.in` | 22.3 |
| Singleton | 9.3 |
| `static` Methode | 9.1 |
| `static` Block | 9.4 |
| `Stream` | 14 |
| `String.format` | 3.1 |
| `StringBuilder` | 3.5 |
| `substring` | 3.3 |
| `super` | 8.2, 8.6 |
| `switch` | 5.2, 5.3 |
| `synchronized` | 21.6 |
| Ternärer Operator | 4.2 |
| Textblock `"""` | 3.2 |
| `this` | 7.1 |
| `Thread` | 21.1 |
| `throw` / `throws` | 16.1, 16.3 |
| `toString` | 10.1 |
| `try-with-resources` | 16.4 |
| Type Erasure | 17.5 |
| Utility-Klasse | 6.7, 18.1 |
| `var` (Java 10) | 1.1 |
| varargs | 6.6 |
| `void` | 6.3 |
| `volatile` | 21.6 |
| Wildcard `? extends` | 17.3 |
| `while` | 5.8 |

---

## Weiterführende Links

- Offizielle Migrationsguides: kotlinlang.org/docs/java-to-kotlin-idioms-strings.html · .../java-to-kotlin-collections-guide.html · .../java-to-kotlin-nullability-guide.html
- Idiome: kotlinlang.org/docs/idioms.html
- Coding Conventions: kotlinlang.org/docs/coding-conventions.html
- Übungen: play.kotlinlang.org/koans
- Java-Interop im Detail: kotlinlang.org/docs/java-interop.html und .../java-to-kotlin-interop.html
- In IntelliJ: Java-Code in eine `.kt`-Datei einfügen → automatische Konvertierung. Umgekehrt: *Tools → Kotlin → Show Kotlin Bytecode → Decompile*.
