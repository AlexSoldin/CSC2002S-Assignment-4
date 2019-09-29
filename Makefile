JAVAC = /usr/bin/javac
JFLAGS = -g
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

all: 	
	javac src/Score.java src/WordDictionary.java src/WordRecord.java src/Completion.java src/WordApp.java src/WordPanel.java -cp bin/ -d bin

comp:	
	javac src/Score.java src/WordDictionary.java src/WordRecord.java src/Completion.java src/WordApp.java src/WordPanel.java -cp bin/ -d bin

run:
	java -cp $(BINDIR) WordApp

docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

clean:
	rm $(BINDIR)/*.class
