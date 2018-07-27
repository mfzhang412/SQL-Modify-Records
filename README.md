# SQL-Modify-Records

How to run: Will have to modify source code to get desired result, because this program is really two in one (was running out of compputer space so joined the two together). To run a program via command prompt, navigate to the 'bin' folder and type 'java file name'. To navigate to the 'bin' folder from command prompt, copy the directory from your computer, and type 'cd ' and then the copied file directory, then hit 'enter'.

Description: The user selects a .txt or .csv file they wish to modify records for. The modifications are as follows: for each line in the .txt or .csv file, that line gets duplicated with a number following that line from 1 to some integer. The other part of the program inserts records from a .txt or .csv file directly into a specified table in a database.

Example of duplicating records:

Original .txt or .csv file ---

Doe, John

Roe, Richard@

Doe, Jane5
____________________________________________________

Modified .txt or .csv file ---

Doe, John1

Doe, John2

...

Doe, John9999

Roe, Richard@1

Roe, Richard@2

...

Roe, Richard@9999

Doe, Jane51

Doe, Jane52

...

Doe, Jane59999
