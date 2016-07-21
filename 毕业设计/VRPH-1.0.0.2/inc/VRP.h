
#ifndef _VRP_H
#define _VRP_H

// VRP class
class VRP
{

    friend class OnePointMove;
    friend class TwoPointMove;
    friend class TwoOpt;

    friend class Postsert;
    friend class Presert;
    friend class Swap;
    friend class Concatenate;
    friend class SwapEnds;
    friend class Flip;

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
    void create_pred_array();
    void print_stats();

    // Files (read/write)
    void write_solution_file(const char *filename);

    // Solution buffers (import/export)
    void export_solution_buff(int *sol_buff);
    void import_solution_buff(int *sol_buff);
    void export_canonical_solution_buff(int *sol_buff);

    // Solution display
    void summary();

    // Graphics - only meaningful with PLPlot
    bool plot(const char *filename, int options, int orientation);
    bool plot(const char *filename);
    bool plot_route(int r, const char *filename);

    // Solvers
    double SA_solve(int heuristics, double start_temp, double cool_ratio,
        int iters_per_loop, int num_loops, int nlist_size, bool verbose);

    // The name of the problem (from TSPLIB file)
    char name[VRPH_STRING_SIZE];


    // Creating default routes for CW, etc.
    bool create_default_routes();

    // Route operations
    void normalize_route_numbers();
    void clean_route(int r, int heuristics);


    // Accessor functions for private data
    double get_total_route_length();
    double get_best_total_route_length();
    int get_total_number_of_routes();
    double get_best_known();


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
    double max_route_length;
    int edge_weight_type;
    int edge_weight_format;
    int coord_type;
    int display_type;
    int matrix_size;
    int dummy_index;
    int neighbor_list_size;
    double temperature;            // For VRPH_SIMULATED_ANNEALING
    double cooling_ratio;

    bool can_display;

    double **d;                    // The distance matrix d
    class VRPNode *nodes;        // Array of nodes - contains coordinates, demand


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


    int *current_sol_buff;  // Place for the current solution if desired

    // To handle infeasibilities
    bool is_feasible(VRPMove *M, int rules);

    // Solution manipulation
    bool postsert_dummy(int i);
    bool presert_dummy(int i);
    bool remove_dummy();
    void update(VRPMove *M);
    bool get_segment_info(int a, int b, struct VRPSegment *S);

    // Solution memory
    void capture_best_solution();

    // Solution information
    int count_num_routes();
    bool check_move(VRPMove *M, int rules);

    //access route information
    bool before(int a, int b);

    // Savings evaluation - inline this to speed things up
    inline bool check_savings(VRPMove *M, int rules){
        ///
        /// Evaluates the given savings in terms of the rules.
        /// The only part of the rules considered are things such
        /// as VRPH_DOWNHILL, VRPH_RECORD_TO_RECORD, VRPH_SIMULATED_ANNEALING
        ///

        if( M->savings < -VRPH_EPSILON )
        {
            M->evaluated_savings=true;
            return true;
        }
        // The order needs to be changed if we eventually add additional rules
        // that do not always accept improving moves!!


        if( (rules & VRPH_DOWNHILL) )
        {
            if(M->savings>=-VRPH_EPSILON )
            {
                M->evaluated_savings=true;
                return false;
            }
        }


        if( rules & VRPH_SIMULATED_ANNEALING )
        {
            if( exp( - ((M->savings) / this->temperature)) > lcgrand(0) )
            {
                M->evaluated_savings=true;
                return true;
            }
            else
                return false;
        }

        return false;

    };

};

#endif


