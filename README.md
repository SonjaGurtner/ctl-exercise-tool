CTL Exercise Tool
=================
About
-----
This is a CTL Exercise Tool for training basic (and simple nested) operators and quantifiers of CTL formulas with visual representation of the Kripke Structure and solutions.

Use
---
A new Kripke Structure with either 4 or 5 states is randomly generated by pressing the Button with the according number. 
It is then visualized with black circles as states and their corresponding labels and arrows for transitions.
The "New Formula" Button chooses a new formula from a set of available formulas and displays it next to the "Formula:" label.
If you are not sure what the meaning of the formula is, you can mark the checkbox with the "Explain" label, which will display 
an textual explanation with a formal definition, as well as an image. 
The states in which you think the formula holds can be marked by clicking and will then be colored blue. Clicking again unmarks the state.
Clicking the "Check Formula" Button will check in which states the formula holds. The regarding states will be marked green, or red if it does not hold.
Additionally a black outline shows which states were selected.
A counter is increased for every state that is marked & correct, or not marked & incorrect.

Running
-------

Download respective `.jar` file from Releases and run using Java 11 or newer. JavaFX is bundled,
but only for the respective OS variant.

To run on windows using Java 11, if you also have Java 8 installed, use a command
similar to the following one:

    # <PATH TO JAVA.EXE> -jar <PATH TO CTL .jar>
    "C:\Program Files\Java\jdk-11.0.2\bin\java.exe" -jar ctl-exercise-tool-all.jar
    
To run on linux use following command:

    # <PATH TO JAVA> -jar <PATH TO CTL .jar>
    
