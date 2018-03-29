JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Gen_pass.java \
	SslServ.java \
	SslCli.java

default: classes

classes: $(CLASSES:.java=.class)


clean:
	rm -f *.class
