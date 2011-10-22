JFLAGS=-g -d ../classes
JC=javac
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = *.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class
	rm -f ../classes/translator/*.class
