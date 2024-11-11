Linear Hashing Implementation in Java


This project involved implementing a Linear Hashing algorithm in Java to manage a dynamic hash table. 

The hash table was designed to grow incrementally as data was inserted, with each position capable of holding multiple keys. 
The system utilized a fullness factor to determine when a page should be split during insertions, with thresholds for splitting based on different fullness factors. 
For deletions, the hash table pages would merge if the fullness factor dropped below a certain threshold. The core operations—insertion, search and deletion—were implemented to handle random keys 
and the hash table resized automatically as keys were added. The performance of these operations was evaluated by measuring the number of comparisons required for insertion, searching and deletion 
as the table size grew. The evaluation process included testing different thresholds for splitting and merging, and comparing the performance of the hash table with a binary tree as well. 
To visualize the results, I constructed a X-Y graph (presented on the report 2 file, inside project3 folder) where Y-axis represented the average number of comparisons and X-axis represented the number of keys 
that showed the average number of comparisons for insertion, search and deletion operations as the number of keys in the table increased. The diagram includes three curves showing the relationship between 
the number of comparisons and the number of keys for different splitting criteria. 

This project allowed me to explore the dynamic behavior of hash tables, optimize performance through splitting and merging criteria, and gain hands-on experience in data structure design and performance analysis.
