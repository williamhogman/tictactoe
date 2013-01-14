TicTacToe
============
Tre i rad.

Kompilering
-------------

Kompilera och bygg en JAR-fil med hjälp av apache maven:

$ cd tictactoe
$ mvn compile
$ mvn assembly:single
$ java -jar target/tictactoe-1.0-SNAPSHOT-jar-with-dependencies.jar

Detta behövs eftersom att att klassen FeedForwardNNAI använder encog
biblioteket för neurala nätverk. Alternativt så kan
FeedForwardNNAI.java tas bort och kompilation göras manuellt med
javac.


Runtime
---------

Huvudmenyn i programmet har prompten $ och det finns tre kommandon:

benchmark - låt AI:et spela 100 000 matcher mot sig själv, räkna ut
            tiden som det tog.

play - spela en runda TicTacToe mot ett tradiontellt AI.
       Det går inte (så svitt jag vet) att vinna mot AI:t. AI:t är
       inte perfekt men kan inte förlora utan bara spela oavgjort
       eller vinna. I simuleringar har jag ännu inte sätt en förlust
       från AI:t

playnn - spela mot ett Neuralt nätverk. Notera att nätverket måste
         tränas innan matchen spelas så det kan ta lite tid innan man
         kan spela. Det ska gå att vinna mot detta, och det kan sägas
         spela mer likt en människa. Endast tillgängligt om
         FeedForwardNNAI fanns med i kompilations steget.

quit - avslutar programet
