
# **Sudoku Kid v0.5**

This application is still in **early development** so, please, bear with
any and all issues or noob mistakes you may find. -u-"
**All feedback is welcome!** (and keep in mind this is a learning project)

## How to run the application:

For now the only way to change the sudoku statement we want to solve and
which methods use we are enabling is through modifying the code of the
main class. (I know this is not at all optimal and will be changing it
in the near future.)

1. Open the project in any IDE (I'm personaly using NetBeans at the
moment).
2. Open the main class.
3. Change the sudokuMatrix variable to match the sudoku you want to solve
in the format of a 9x9 java array matrix of single integer digits.<br>
> regex = "^\{(\{(\d\,){8}\d\}\,){8}\{(\d\,){8}\d\}\}$"
4. Change the boolean attributes at the top to true if you want to use
the asociated solving algorithm or false if you don't (all are true by
default). Don't change the ones with an expression differen to true or
false on them!
5. Run the application!

> If this file is incomplete it's because it's work I still havent forced
> myself to do (: