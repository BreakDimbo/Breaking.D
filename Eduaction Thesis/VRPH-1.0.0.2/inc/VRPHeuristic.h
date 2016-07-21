
#ifndef _VRP_HEUR_H
#define _VRP_HEUR_H

// Various rules that can be or'ed together
#define VRPH_DOWNHILL                           1
#define VRPH_SIMULATED_ANNEALING                (1<<2)
#define VRPH_FIRST_ACCEPT                       (1<<3)
#define VRPH_USE_NEIGHBOR_LIST                  (1<<4)
#define VRPH_RANDOMIZED                         (1<<5)
#define VRPH_SAVINGS_ONLY                       (1<<6)

// Heuristic operations
#define ONE_POINT_MOVE                          (1<<7)
#define TWO_POINT_MOVE                          (1<<8)
#define TWO_OPT                                 (1<<9)


// Values of heuristics that can be used in functions such as
// clean_routes

#define NUM_HEURISTICS                  3

#define ONE_POINT_MOVE_INDEX            0
#define TWO_POINT_MOVE_INDEX            1
#define TWO_OPT_INDEX                   2




// Move types
#define PRESERT                          1
#define POSTSERT                         2
#define CONCATENATE                      3
#define FLIP                             5
#define SWAP                             7
#define SWAP_ENDS                        8

#endif





