runGui:
	mvn package && java -jar ./target/client-tictactoe-1.0-RELEASE.jar

runTerminal: 
	mvn package && java -jar ./target/client-tictactoe-1.0-RELEASE.jar --terminal

clean:
	mvn clean 

