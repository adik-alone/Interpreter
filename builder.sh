java -jar /usr/local/lib/antlr4/antlr-4.13.2-complete.jar MyGram.g4
javac MyGram*.java

java -jar /usr/local/lib/antlr4/antlr-4.13.2-complete.jar -no-listener -visitor MyGram.g4
javac cls/VariableUtils.java
javac Interpriter.java
