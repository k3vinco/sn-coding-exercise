# Open Book Coding Exercise - Kevin Co
This program finds the minimal path for a given triangle of numbers.

## Setup
1) Ensure you have `scalac` installed on your machine.
2) Navigate to the folder containing `MinTrianglePath.scala` and run

        scalac MinTrianglePath.scala
    
   This will compile the scala code into `MinTrianglePath.class`

## Usage
### Manual input
You can pipe input into the program directly in the CLI. It will then repeatedly prompt you for a line of numbers until you enter `EOF` (or any identifier of your choosing), after which it will print the minimal path and how it got there:

        cat << EOF | scala MinTrianglePath
        > 7
        > 6 3
        > 3 8 5
        > 11 2 10 9
        > EOF
        Minimal path is: 7 + 6 + 3 + 2 = 18

### Input from file
You can also pipe input from a file into the program:

        cat input.txt | scala MinTrianglePath
        Minimal path is: 7 + 6 + 3 + 2 = 18

The file `input.txt` would look like the following:

        7
        6 3
        3 8 5
        11 2 10 9