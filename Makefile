runGui:
	mvn package && java -jar ./target/client-tictactoe-1.0-SNAPSHOT.jar

runTerminal: 
	mvn package && java -jar ./target/client-tictactoe-1.0-SNAPSHOT.jar --terminal

clean:
	mvn clean 

