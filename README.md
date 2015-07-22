# 2048 Solver

A Solver for the 2048 game.

## Algorithms

It has 3 algorithms, with varying complexity levels.

1- Empirically choose next move.  
2- Modified BFS.  
3- Use the first algorithm until a certain point and the other one afterwards.  

### Results
Over 10000 tries.  
Format: [ Highest Tile ]: [ Count ]  

1-   
128: 68  
256: 719  
512: 3861  
1024: 4697  
2048: 651  
4096: 1  
Average points: 11896  
Time elapsed: 16052 ms  

2-  
128: 58  
256: 719  
512: 3924  
1024: 4518  
2048: 779  
4096: 0  
Average points: 12293  
Time elapsed: 81038 ms  

3-  
64: 62  
128: 380  
256: 2065  
512: 5334  
1024: 2125  
2048: 21  
4096: 0  
Average: 7547  
Time elapsed: 772175 ms ~ 13 minutes  

### Conclusion
The modified BFS doesn't go very deep, which makes it not ideal for when there are few tiles and the numbers are very low but shows positive results when there are many tiles on the board, so the combination of both algorithms proved to be slightly better than using the empirical one alone, although it takes 5 times longer to run.
