JFLAGS = -g
JC = javac
JVM= java 
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
       A3Server.java \
        A3Client.java 
MAIN = A3Server
default: classes
classes: $(CLASSES:.java=.class)
run: classes
	$(JVM) $(MAIN)
clean:
	$(RM) *.class