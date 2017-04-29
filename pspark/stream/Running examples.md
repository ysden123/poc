# Running examples
## Stream - Orders
Example Orders demonstrates simple usage Spark streaming.

Module _**WriteData**_ creates "big" data file, splits it on 
many small files and writes these files with time 
interval in directory _tempTest_.

Module _**ReadData**_ monitors directory and, if a new file
was added, handles new data. Module _**ReadData**_ stores
result data in directory _out_. It creates subdirectory for
each new input file  

Module _**ReadResult**_ reads and handles result data - calculates
totals and prints them on console.

To run the example
1. Start _**ReadData**_ and immediately start _**WriteData**_
1. After _**WriteData**_ and _**ReadData**_ finished run _**ReadResult**_ 
 