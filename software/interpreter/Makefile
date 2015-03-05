JAVA_FILES = *.java ast/*.java parser/*.java visitor/*.java
JAVA_CLASSES = *.class ast/*.class parser/*.class visitor/*.class

all: *.java ast/*.java parser/*.java visitor/*.java
	javac $(JAVA_FILES)

clean:
	rm $(JAVA_CLASSES)

jar:
	jar cvf I.jar *.class visitor/*.class parser/*.class ast/*.class
