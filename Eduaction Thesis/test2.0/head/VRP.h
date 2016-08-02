////////////////////////////////////////////////////////////
//                                                        //
// This file is part of the VRPH software package for     //
// generating solutions to vehicle routing problems.      //
// VRPH was developed by Chris Groer (cgroer@gmail.com).  //
//                                                        //
// (c) Copyright 2010 Chris Groer.                        //
// All Rights Reserved.  VRPH is licensed under the       //
// Common Public License.  See LICENSE file for details.  //
//                                                        //
////////////////////////////////////////////////////////////

#ifndef _VRP_H
#define _VRP_H

// VRP class
class VRP
{

    friend class OnePointMove;
    friend class TwoPointMove;

    friend class Postsert;
    friend class Presert;
    friend class Swap;
    friend class Concatenate;

    friend class ClarkeWright;

public:
    VRP(int n);
    // Constructor for problems with n non-VRPH_DEPOT nodes
    // Destructor
    ~VRP();

    // TSPLIB file processing
    void read_TSPLIB_file(const char *infile);
    // Write problem instance
    void write_TSPLIB_file(const char *outfile);

    // Solution display/debugging
    void show_next_array();
    void show_pred_array();
    bool verify_routes(const char *message);
    void create_pred_array();
    void print_stats();

    // Files (read/write)
    void write_solution_file(const char *filename);
    void write_solutions(int num_sols, const char *filename);
    void write_tex_file(const char *filename);
    void read_solution_file(const char *filename);

    // Solution buffers (import/export)
    void export_solution_buff(int *sol_buff);
    void import_solution_buff(int *sol_buff);
    void export_canonical_solution_buff(int *sol_buff);

    // Solution display
    void show_routes();
    void show_route(int k);
    void summary();

    // Solvers

    double SA_solve(int heuristics, double start_temp, double cool_ratio,
        int iters_per_loop, int num_loops, int nlist_size, bool verbose);

    // The name of the problem (from TSPLIB file)
    char name[VRPH_STRING_SIZE];

    // Creating default routes for CW, etc.
    bool create_default_routes();

    // Route operations
    void refresh_routes();
    void normalize_route_numbers();
    void clean_route(int r, int heuristics);
    void add_route(int *route_buff);

    // Accessor functions for private data
    double get_total_route_length();
    double get_best_total_route_length();
    int get_total_number_of_routes();
    double get_best_known();

    // Places to store unique solutions and routes - uses internal hash table
    VRPSolutionWarehouse *solution_wh; // To store additional solutions
    VRPRouteWarehouse *route_wh;     // To store routes/columns

    // Distance matrix creation
    void create_distance_matrix(int type);
    // Neighbor list creation
    void create_neighbor_lists(int nsize);

    // Route reversal
    void reverse_route(int i);

    // Statistics
    int num_evaluations[NUM_HEURISTICS];
    int num_moves[NUM_HEURISTICS];

private:

    // Problem parameters, description, etc.
    int num_nodes;
    double total_route_length;
    int *best_sol_buff;                // Place for the best solution to live
    double best_total_route_length;
    int total_number_of_routes;
    int num_original_nodes;
    double best_known;  // Record of the best known solution for benchmarks
    int problem_type;
    int total_demand;
    int max_veh_capacity;
    int orig_max_veh_capacity;
    double max_route_length;
    double min_route_length;
    double orig_max_route_length;
    int min_vehicles;    // Not currently used
    int edge_weight_type;
    int coord_type;
    int display_type;
    int edge_weight_format;
    int matrix_size;
    int dummy_index;
    int neighbor_list_size;
    double temperature;            // For VRPH_SIMULATED_ANNEALING
    double cooling_ratio;

    bool symmetric;                 // To keep track of symmetric/asymmetric instances
                                    // Note! Asymmetric instances have received only
                                    // limited testing!
    bool can_display;

    double **d;                    // The distance matrix d
    bool **fixed;                // Matrix to keep track of fixed edges

    class VRPNode *nodes;        // Array of nodes - contains coordinates, demand
    // amounts, etc.

    bool depot_normalized;        // Set to true if VRPH_DEPOT coords normalized to origin

    bool forbid_tiny_moves;        // Set to true to prevent potentially nonsense moves
                                   // that have a tiny (perhaps zero) effect on route length

    // Local search neighborhood creation
    bool create_search_neighborhood(int j, int rules);
    int search_size;
    int *search_space;

    // Solution storage
    int *next_array;
    int *pred_array;
    int *route_num;
    bool *routed;            // Indicates whether the customer is in a route yet or not

    class VRPRoute *route;    // Array stores useful information about the routes in a solution

    // Tabu search - very limited testing so far!!


    int *current_sol_buff;  // Place for the current solution if desired

    // Accessing edge information
    bool before(int a, int b);

    // To handle infeasibilities
    class VRPViolation violation;
    bool is_feasible(VRPMove *M, int rules);

    // Solution manipulation
    void update(VRPMove *M);

    // Solution memory
    void capture_best_solution();

    // Solution information
    int count_num_routes();
    bool  check_move(VRPMove *M, int rules);

};

#endif


