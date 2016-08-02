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

#include "VRPH.h"
#define VRPH_MAX_CYCLES 500

void VRPH_version()
{
    printf("--------------------------------------------\n");
    printf("VRPH, version 1.0\nCopyright 2010 Chris Groer\nDistributed under Common Public License 1.0\n");
    printf("--------------------------------------------\n\n");
}


VRP::VRP(int n)
{
    ///
    /// Constructor for an n-node problem.
    ///

    int i,j;

    num_nodes=n;
    num_original_nodes=n;
    total_demand=0;

    next_array = new int[n+2];
    pred_array = new int[n+2];
    route_num = new int[n+2];
    route = new VRPRoute[n+2];
    routed = new bool[n+2];
    best_sol_buff = new int[n+2];
    current_sol_buff = new int[n+2];
    search_space = new int[n+2];
    nodes = new VRPNode[n+2];
    // Add an extra spot for the VRPH_DEPOT and for the dummy node

    symmetric=true;
    // Set to false only when we encounter FULL_MATRIX file

    forbid_tiny_moves=true;
    // Default is to allow these moves

    d=NULL;
    // The distance matrix is allocated when the problem is loaded
    fixed=new bool*[n+2];
    fixed[0]=new bool[(n+2)*(n+2)];
    for(i=1;i<n+2;i++)
        fixed[i]=fixed[i-1]+(n+2);
    for(i=0;i<n+2;i++)
    {
        routed[i]=false;
        for(j=0;j<n+2;j++)
            fixed[i][j]=false;
    }

    // Set these to default values--they may change once
    // we read the file.

    min_vehicles=-1;
    max_route_length=VRP_INFINITY;
    orig_max_route_length=VRP_INFINITY;
    total_route_length=0.0;
    best_known=VRP_INFINITY;
    depot_normalized=false;
    // Will be set to true if we shift nodes so VRPH_DEPOT is at origin

    // These are for record-to-record travel

    // For keeping track of the statistics

    for(i=0;i<NUM_HEURISTICS;i++)
    {
        num_evaluations[i]=0;
        num_moves[i]=0;

    }
    // Create the solution warehouse
    this->solution_wh=new VRPSolutionWarehouse(NUM_ELITE_SOLUTIONS,n);

    this->route_wh=NULL;

    // Set this to true only if we have valid coordinates
    // This is valid only for plotting the solution
    can_display=false;
}


VRP::~VRP()
{
    ///
    /// Destructor for the VRP.
    ///

    delete [] this->best_sol_buff;
    delete [] this->current_sol_buff;
    delete [] this->d[0];
    delete [] this->d;
    delete [] this->fixed[0];
    delete [] this->fixed;
    delete [] this->next_array;
    delete [] this->search_space;
    delete [] this->nodes;
    delete [] this->pred_array;
    delete [] this->route;
    delete [] this->route_num;
    delete [] this->routed;
    delete this->solution_wh;

}


// Accessor functions for private data
double VRP::get_total_route_length()
{
    ///
    /// Returns the total route length of the current solution.
    ///

    return this->total_route_length;
}


double VRP::get_best_total_route_length()
{
    ///
    /// Returns the total route length of the best solution discovered so far.
    ///

    return this->best_total_route_length;
}

int VRP::get_total_number_of_routes()
{
    ///
    /// Returns the number of routes in the current solution.
    ///

    return this->total_number_of_routes;
}

double VRP::get_best_known()
{
    return this->best_known;

}

void VRP::create_distance_matrix(int type)
{
    ///
    /// Creates the O(n^2) size distance matrix for the
    /// provided data using the distance function referenced by type.
    /// If the type is EXPLICIT, then the entire distance matrix should
    /// be provided in the actual TSPLIB file.
    ///


    int i,j,k,n;


    if(type==VRPH_EXPLICIT)
        // We have presumably already loaded in the distance matrix!
        return;

    n= this->num_original_nodes;
    k=0;

    // Otherwise construct the matrix - we store the whole thing even though
    // it is symmetric as we found that this was quite a bit faster...

    for(i=0;i<=n+1;i++)
    {
        for(j=0;j<=n+1;j++)
            this->d[i][j]=VRPDistance(type, this->nodes[i].x,this->nodes[i].y,this->nodes[j].x,
                this->nodes[j].y);

    }

    return;

}

void VRP::create_neighbor_lists(int nsize)
{
    ///
    /// Creates the neighbor list of size nsize for each node
    /// including the VRPH_DEPOT.
    ///

    if(nsize>num_nodes )
    {
        fprintf(stderr,"Requested neighbor list size is greater than num_nodes!\n%d>%d\n",
            nsize,num_nodes);
        report_error("%s: Neighbor list error!!\n",__FUNCTION__);
    }

    if(nsize>MAX_NEIGHBORLIST_SIZE )
    {
        fprintf(stderr,"Requested neighbor list size is greater than MAX_NEIGHBORLIST_SIZE!\n%d>%d\n",
            nsize,MAX_NEIGHBORLIST_SIZE);
        report_error("%s: Neighbor list error!!\n",__FUNCTION__);
    }


    int i,j,n,b;
    int k;
    double dd;
    double max;
    int maxpos;
    VRPNeighborElement *NList;

    n= num_nodes;

    // Set the neighbor_list_size value
    neighbor_list_size=nsize;

    NList=new VRPNeighborElement[nsize];

    // First, do the neighbor_list for the VRPH_DEPOT
    max=0.0;
    // NEW
    maxpos=0;

    for(i=1;i<=nsize;i++)
    {
        NList[i-1].val=d[VRPH_DEPOT][i];

        NList[i-1].position = i;

        // keep track of the largest value
        if(NList[i-1].val > max)
        {
            max=NList[i-1].val;
            maxpos=i-1;
        }

    }

    // Now go through the rest of the nodes.
    for(i=nsize+1;i<=n;i++)
    {
        if(d[VRPH_DEPOT][i]<max)
        {
            // Replace element in position maxpos
            NList[maxpos].val=d[VRPH_DEPOT][i];
            //nodes[i].depot_distance;
            NList[maxpos].position=i;
        }
        // Now find the new max
        max=0.0;
        for(b=0;b<nsize;b++)
        {
            if(NList[b].val>max)
            {
                maxpos=b;
                max=NList[b].val;
            }
        }
    }

    qsort(NList,nsize,sizeof(VRPNeighborElement), VRPNeighborCompare);
    i=0;
    for(b=0;b<nsize;b++)
    {
        nodes[VRPH_DEPOT].neighbor_list[b].val=NList[b].val;
        nodes[VRPH_DEPOT].neighbor_list[b].position=NList[b].position;

#        if NEIGHBOR_DEBUG
        printf("(%d,%d,%f) \n",VRPH_DEPOT,nodes[VRPH_DEPOT].neighbor_list[b].position,
            nodes[VRPH_DEPOT].neighbor_list[b].val);
#endif


    }

#    if NEIGHBOR_DEBUG
    printf("\n\n");
#endif


    k=0;
    // Done with NeighborList for VRPH_DEPOT.  Now do it for the rest of the nodes
    for(i=1;i<=n;i++)
    {
        // Loop through all the nodes and create their Neighbor Lists
        // First initialize the NList
        for(j=0;j<nsize;j++)
        {
            NList[j].position=VRP_INFINITY;
            NList[j].val=VRP_INFINITY;
        }


        // VRPH_DEPOT can be in NList, so set NList[0] to depot_distance
        NList[0].position=VRPH_DEPOT;
        NList[0].val=d[i][VRPH_DEPOT];


        for(j=1;j<i;j++)
        {
#if NEIGHBOR_DEBUG
            printf("Loop 1; i=%d; in j loop(%d)\n",i,j);
#endif

            dd=d[i][j];

            if(j<nsize)
            {
                // Construct the original nsize long neighbor list
                NList[j].val=dd;
                NList[j].position=j;
                // Update max size
                if(NList[j].val>max)
                {
                    max=NList[j].val;
                    maxpos=j;

                }
            }
            else
            {
                // j >= nsize
                if(dd<max)
                {
                    NList[maxpos].val=dd;
                    NList[maxpos].position=j;
                    // Now find the new max element;
                    max=0.0;
                    for(b=0;b<nsize;b++)
                    {
                        if(NList[b].val>max)
                        {
                            max=NList[b].val;
                            maxpos=b;
                        }
                    }
                }
            }

        }


        for(j=i+1;j<=n;j++)
        {
#if NEIGHBOR_DEBUG
            printf("Loop 2; i=%d; in j loop(%d)\n",i,j);
#endif

            dd=d[i][j];

            if(j<=nsize)// was <
            {
                // Construct the original nsize long neighbor list
                NList[j-1].val=dd;
                NList[j-1].position=j;
                // Update max size
                if(NList[j-1].val>max)
                {
                    max=NList[j-1].val;
                    maxpos=j-1;

                }
            }
            else
            {
                if(dd<max)
                {
                    // Replace maxpos with this one
                    NList[maxpos].val=dd;
                    NList[maxpos].position=j;
                    // Now find the new max element;
                    max=0.0;
                    for(b=0;b<nsize;b++)
                    {
                        if(NList[b].val>max)
                        {
                            max=NList[b].val;
                            maxpos=b;
                        }
                    }
                }
            }

        }

#if NEIGHBOR_DEBUG
        printf("Node %d neighbor list before sorting\n",i);
        for(b=0;b<nsize;b++)
        {
            printf("NList[%d]=%d,%f[%f]\n",b,NList[b].position,NList[b].val,d[i][NList[b].position]);
        }
#endif
        // Now sort the NList and stick the resulting list into
        // the neighbor list for node i
        qsort (NList, nsize, sizeof(VRPNeighborElement), VRPNeighborCompare);

        for(b=0;b<nsize;b++)
        {
            nodes[i].neighbor_list[b].position=NList[b].position;
            nodes[i].neighbor_list[b].val=NList[b].val;
            if(i== nodes[i].neighbor_list[b].position)
            {
                fprintf(stderr,"ERROR:: Node %d is in it's own neighbor list!!\n", i);
                fprintf(stderr,"i=%d; %d[%d],%f[%f])\n",i,nodes[i].neighbor_list[b].position,
                    NList[b].position,nodes[i].neighbor_list[b].val,NList[b].val);

                report_error("%s: Error creating neighbor lists\n",__FUNCTION__);
            }
#if NEIGHBOR_DEBUG

            printf("NList[%d]=%d,%f[%f]",b,nodes[i].neighbor_list[b].position,nodes[i].neighbor_list[b].val,d[i][nodes[i].neighbor_list[b].position]);

#endif
        }

        // Hackk to make the VRPH_DEPOT in everyone's list
        //nodes[i].neighbor_list[nsize-1].position=VRPH_DEPOT;
        //nodes[i].neighbor_list[nsize-1].val=d[i][VRPH_DEPOT];

    }

    delete [] NList;

    return;

}

void VRP::refresh_routes()
{
    ///
    /// Ignores the current route length and load values and
    /// recalculates them directly from the next_array.
    ///


    int i, n, cnt;
    double len=0;
    double rlen=0;
    double tot_len=0;
    int next_node;
    int current_node, current_route, route_start, current_start, current_end;
    int total_load = 0;
    int current_load = 0;


    n=num_nodes;

    i = 1;
    cnt = 0;
    route_start = -next_array[VRPH_DEPOT];

    current_node = route_start;
    current_route = route_num[current_node];

    current_start = route[current_route].start;
    current_end = route[current_route].end;

    total_load+=nodes[current_node].demand;
    current_load+=nodes[current_node].demand;
    len+=d[VRPH_DEPOT][current_node];
    rlen+=d[VRPH_DEPOT][current_node];

    while(route_start != 0 && i<num_nodes+1)
    {
        // When we finally get a route beginning at 0, this is the last route
        // and there is no next route, so break out
        if(next_array[current_node]==current_node)
        {
            // We've entered a loop
            fprintf(stderr,"(2)Self loop found in next array(%d)\n",current_node);
            report_error("%s: Error in refresh_routes()\n",__FUNCTION__);
        }

        if(next_array[current_node]==0)
        {
            // We're at the end of the Solution!

            len+=d[current_node][VRPH_DEPOT];
            rlen+=d[current_node][VRPH_DEPOT];
            current_route=route_num[current_node];
            route[current_route].length = rlen;
            route[current_route].load = current_load;
            total_route_length=len;

            // We're done now
            return;
        }

        if(next_array[current_node]>0)
        {
            // Next node is somewhere in the middle of a route

            next_node = next_array[current_node];
            len+=d[current_node][next_node];
            rlen+=d[current_node][next_node];
            current_node=next_node;
            total_load+=nodes[current_node].demand;
            current_load+=nodes[current_node].demand;
            cnt++;
        }
        else
        {
            // We must have a non-positive "next" node indicating the beginning of a new route
            //len+=nodes[current_node].depot_distance;
            len+=d[current_node][VRPH_DEPOT];
            //rlen+=nodes[current_node].depot_distance;
            rlen+=d[current_node][VRPH_DEPOT];
            tot_len+=rlen;
            current_route=route_num[current_node];
            current_end = route[current_route].end;

            route[current_route].length = rlen;
            route[current_route].load = current_load;

            i++;
            route_start = - (next_array[current_node]);
            current_route = route_num[route_start];
            current_start = route[current_route].start;
            current_end = route[current_route].end;
            if(route_start != current_start)
            {
                fprintf(stderr,"Route %d:  %d != %d\n",current_route, route_start, current_start);
                report_error("%s: Error in refresh_routes()\n",__FUNCTION__);
            }

            current_node = route_start;
            total_load+=nodes[current_node].demand;
            // reset current_load to 0
            current_load=nodes[current_node].demand;
            len+=d[VRPH_DEPOT][current_node];
            rlen=d[VRPH_DEPOT][current_node];
            cnt++;
        }
    }


    return;


}
void VRP::create_pred_array()
{
    ///
    /// This function creates a pred_array from the existing next_array
    ///
    int i,j;

    i=VRPH_DEPOT;
    j=next_array[i];
    while(j!=VRPH_DEPOT)
    {
        if(j>0)
            pred_array[j]=i;
        else
            pred_array[-j]=-i;

        i=VRPH_ABS(j);
        j=next_array[i];

    }
    // The VRPH_DEPOT
    pred_array[j]=-i;
    return;





    /*for(i=0;i<=num_nodes;i++)
    {
        j=next_array[i];
        if(j>0)
            pred_array[j] = i;
        else
            pred_array[-j] = -i;
    }
    return;*/
}
void VRP::reverse_route(int i)
{
    ///
    /// This function reverses route number i - does no
    /// error checking since we don't know if the routes
    /// have normalized numbers or not...
    ///


    int current_node, start_node, last_node, prev_route, next_route, temp;
    int orig_start, orig_end ;

    if(i<=0)
    {
        // Illegal!
        fprintf(stderr,"Reversing route  of negative index?? i=%d\n",i);
        report_error("%s: Can't reverse route!\n",__FUNCTION__);
    }

    orig_end = route[i].end;
    orig_start = route[i].start;

    start_node=orig_start;
    current_node=start_node;

    // We begin with the first node in the route - we will change its next_array[]
    // value at the very end

    temp = next_array[current_node];
    prev_route = -pred_array[current_node];
    // So next[prev_route] should equal start_node
    // We will change this so that next[prev_route]=-last_node

    pred_array[current_node] = temp;
    current_node = temp;
    while( (temp = next_array[current_node]) >0)
    {
        next_array[current_node] = pred_array[current_node];
        pred_array[current_node] = temp;
        current_node = temp;
    }

    // current_node is now the end of the original route, so we can finish the reversal

    temp= pred_array[current_node];
    last_node = current_node;
    next_route = -next_array[last_node];

    route[i].end=orig_start;
    route[i].start=orig_end;

#if REVERSE_DEBUG
    printf("start_node: %d; last_node: %d; prev_route: %d; next_route: %d\n",start_node,last_node, prev_route,next_route);
#endif

    // Final set of updates
    next_array[prev_route]=-last_node;
    pred_array[next_route]=-start_node;
    next_array[start_node]=-next_route;
    pred_array[last_node]= -prev_route;
    next_array[last_node]=temp;
    next_array[prev_route]=-last_node;

    // Need to update length if asymmetric
    if(!this->symmetric)
    {
        // Update length as it possibly changed!
        this->refresh_routes();
    }

    return;
}
bool VRP::create_default_routes()
{
    ///
    /// This function creates routes VRPH_DEPOT-i-VRPH_DEPOT for all nodes i
    /// and properly initializes all the associated arrays.  Returns
    /// true if successful, false if the default routes violate some
    /// capacity or route length constraint.
    ///


    int i,n;
    bool is_feasible=true;
    // No violations yet...
    violation.capacity_violation = 0;
    violation.length_violation = 0;


    total_route_length=0;

    n = this->num_original_nodes;
    // First, create the initial routes:  VRPH_DEPOT - node - VRPH_DEPOT for all nodes

    routed[VRPH_DEPOT]=true;

    next_array[VRPH_DEPOT] = -1;
    for(i=1;i<=n;i++)
    {
        next_array[i] = -(i+1);
        total_route_length+= (d[VRPH_DEPOT][i] + d[i][VRPH_DEPOT]);

        route_num[i]=i;
        route[i].start=i;
        route[i].end=i;
        route[i].load= nodes[i].demand;
        route[i].length= d[VRPH_DEPOT][i] + d[i][VRPH_DEPOT];


        // Check capacities
        if(route[i].load > max_veh_capacity)
            is_feasible=false;
        if(route[i].length > max_route_length)
            is_feasible=false;

        route[i].num_customers=1;

        routed[i]=true;

    }


    next_array[n]=VRPH_DEPOT;

    route_num[VRPH_DEPOT]=0;

    // Now create the associated pred_array implied by the newly created next_array
    create_pred_array();

    total_number_of_routes=n;





    if(is_feasible == false)
    {
        // We had infeasible routes - record the violation in the Solution and
        // return false;


        for(i=1;i<=n;i++)
        {
            routed[i]=false;
            // Set routed status to false since default routes are infeasible

            // Check capacities
            if(route[i].load > max_veh_capacity)
            {
                // Update the worst violations

                printf("Default routes load violation: %d > %d\n",route[i].load,
                    max_veh_capacity);

                if( (route[i].load - max_veh_capacity) >
                    violation.capacity_violation)
                {
                    violation.capacity_violation =
                        (route[i].load - max_veh_capacity);
                }

            }
            if(route[i].length > max_route_length)
            {
                // Update the worst violations

                printf("Default routes length violation: %f > %f\n",route[i].length,
                    max_route_length);

                if( (route[i].length -max_route_length ) >
                    violation.length_violation)
                {
                    violation.length_violation =
                        (route[i].length -max_route_length );
                }


            }


        }

        return false;
    }
    else
    {
        // All routes were feasible
        for(i=1;i<=n;i++)
        {
            routed[i]=true;
        }
        return true;
    }


}


int VRP::count_num_routes()
{
    ///
    /// Manually counts the # of routes in the current solution.
    ///

    int current,next;
    int num=0;

    current=VRPH_DEPOT;
    next=-1;

    while(next!=VRPH_DEPOT)
    {
        next= next_array[current];
        if(next<0)
        {
            // We're in a new route
            num++;
            current=-next;
        }
        else
            current=next;
    }

    return num;


}

bool VRP::check_move(VRPMove *M, int rules)
{

    ///
    /// Evaluates the move in terms of the rules.  Can consider savings,
    /// as well as other aspects of the VRPMove M.
    ///


    double savings;

    savings=M->savings;


    if(this->forbid_tiny_moves)
    {
        // See if it is a "meaningless" move
        if(savings>-VRPH_EPSILON && savings < VRPH_EPSILON)
            return false;
    }

    if( (rules & VRPH_DOWNHILL) == VRPH_DOWNHILL)
    {
        if(savings<-VRPH_EPSILON )
            return true;
        else
            return false;

    }

    if( rules & VRPH_SIMULATED_ANNEALING )
    {
        if(M->evaluated_savings==true)
            return true;    // We already checked the random acceptance

        // Otherwise, determine whether to accept or not.
        if( exp(- (M->savings / this->temperature)) > lcgrand(10) )
            return true;
        else
            return false;

    }

    report_error("%s: didn't return yet!\n",__FUNCTION__);

    return false;

}


bool VRP::is_feasible(VRPMove *M, int rules)
{
    ///
    /// Determines whether a proposed move is feasible or not.
    /// Could add time window feasibility checks here, etc.  The rules
    /// is currently not used.
    ///

    for(int i=0;i<M->num_affected_routes;i++)
    {
        if( (M->route_lens[i]>this->max_route_length) || (M->route_loads[i]>this->max_veh_capacity) )
            return false;
    }

    return true;

}

void VRP::normalize_route_numbers()
{
    ///
    /// Renumbers the routes so that with R total routes, they
    /// are numbered 1,2, ..., R instead of all over the place
    /// as they typically are after Clarke Wright.
    ///

    int current, end, n, R, i;
    int current_route;
    int *indices;
    R= count_num_routes();
    n= this->num_original_nodes;


    // First check to see if we are already normalized

    // Make the arrays 1-based to avoid insanity...
    indices=new int[n+1];

    for(i=0;i<=n;i++)
        indices[i]=1;

    int ctr=0;
    i=VRPH_ABS(next_array[VRPH_DEPOT]);
    while(i!=VRPH_DEPOT)
    {
        if(route_num[i] <= R && routed[i]==true)
        {
            // route_num[i] is less than R, so we have to
            // avoid this index.
            indices[route_num[i]]=0;
        }
        else
            ctr++;

        i=VRPH_ABS(next_array[i]);
    }

    if(ctr==0)
    {
        delete [] indices;
        return;// route numbers are already normalized since no route_nums were >R.
    }

    // Now all of the available indices that are less than or equal to R
    // are marked with a 1. The unavailable indices are set to zero.

    int next_index=1;
    while(indices[next_index]==0)
        next_index++;

    // So next_index is the first index available for use.

    // Get the first route and change the information
    current=VRPH_ABS(next_array[VRPH_DEPOT]);

    while(current!=VRPH_DEPOT)
    {
        current_route= route_num[current];
        end= route[current_route].end;

        // Get the next available index
        while(indices[next_index]==0)
            next_index++;

        // Modify the current index if necessary
        if(current_route>R)
        {
            // Update the route_num
            i=VRPH_ABS(next_array[VRPH_DEPOT]);
            while(i!=VRPH_DEPOT)
            {
                if(route_num[i]==current_route)
                    route_num[i]=next_index;
                i=VRPH_ABS(next_array[i]);

            }
            // Update the route information
            route[next_index].start = route[current_route].start;
            route[next_index].end = route[current_route].end;
            route[next_index].length = route[current_route].length;
            route[next_index].load = route[current_route].load;
            route[next_index].num_customers = route[current_route].num_customers;

            next_index++;


        }

        // Set current to be the first node in the next route
        current=VRPH_ABS(next_array[end]);


    }

    delete [] indices;

    return;



}


bool VRP::create_search_neighborhood(int j, int rules)
{
    ///
    /// Creates the search_size and search_space fields for the
    /// current VRP in terms of the given node j and the rules.
    ///

    int i,k, cnt;
    // Define the search space


    if( rules & VRPH_USE_NEIGHBOR_LIST )
    {

        // Search only those nodes that are in the neighbor list
        search_size=0;
        cnt=0;
        for(i=0;i<neighbor_list_size;i++)
        {
            // Consider node k
            k=nodes[j].neighbor_list[i].position;
            if( routed[k] == true)
            {
                // The node is routed
                        // No intra/inter restrictions
                        search_space[cnt] = k;
                        cnt++;
            }
        }
        search_size=cnt;
        goto randomize;
    }


    // No rules given - search space is set of all nodes
    search_size=0;
    i=0;

    search_space[i]=VRPH_ABS(next_array[VRPH_DEPOT]);
    search_size++;

    for(;;)
    {
        search_space[i+1]=VRPH_ABS(next_array[search_space[i]]);
        search_size++;
        if(search_space[i+1]==VRPH_DEPOT)
            goto randomize;
        i++;
    }

randomize:

    // Now permute it if the rules is VRPH_RANDOMIZED
    if(rules & VRPH_RANDOMIZED)
        random_permutation(search_space, search_size);

    return true;



}

void VRP::clean_route(int r, int heuristics)
{
    ///
    /// Runs the provided set of heuristics on route r
    /// until a local minimum is reached.
    ///

    int i,j, r_start, r_end;
    double start_val, end_val, start_rlen, end_rlen;

    OnePointMove    OPM;
    TwoPointMove    TPM;

    int rules= VRPH_DOWNHILL+VRPH_FIRST_ACCEPT+VRPH_SAVINGS_ONLY;


start_improving:

    start_val = route[r].length;

#if CLEAN_DEBUG
    printf("CLEAN::start_val=%f\n",start_val);
#endif

    end_val = - VRP_INFINITY;
    r_start=route[r].start;
    r_end=route[r].end;

    if((heuristics & ONE_POINT_MOVE)==ONE_POINT_MOVE)
    {
        // Try the ONE_POINT_MOVE for all nodes in route r;
        r_start=route[r].start;

opm_search:
        start_rlen=route[r].length;
        i=r_start;
        j=VRPH_MAX(next_array[i],0);
        while(i!=VRPH_DEPOT)
        {
            while(OPM.search(this,i,rules));
            i=j;
            j=VRPH_MAX(next_array[j],0);
        }

        end_rlen = route[r].length;
        if( (end_rlen<start_rlen) && (VRPH_ABS(end_rlen-start_rlen)>VRPH_EPSILON))
            goto opm_search;

#if CLEAN_DEBUG
        printf("CLEAN::OPM end_rlen=%f\n",end_rlen);
#endif

    }

    if((heuristics & TWO_POINT_MOVE)==TWO_POINT_MOVE)
    {
        // Try the TWO_POINT_MOVE for all nodes in route r;
tpm_search:
        r_start=route[r].start;
        start_rlen=route[r].length;
        i=r_start;
        j=VRPH_MAX(next_array[i],0);
        while(i!=VRPH_DEPOT)
        {
            while(TPM.search(this,i,rules));
            i=VRPH_MAX(next_array[i],VRPH_DEPOT);
        }

        end_rlen = route[r].length;
        if( (end_rlen<start_rlen) && (VRPH_ABS(end_rlen-start_rlen)>VRPH_EPSILON))
            goto tpm_search;

#if CLEAN_DEBUG
        printf("CLEAN::TPM end_rlen=%f\n",end_rlen);
#endif

    }
    end_val= route[r].length;

#if CLEAN_DEBUG
    printf("CLEAN::final end_val=%f\n",end_val);
#endif


    if(VRPH_ABS(start_val-end_val)>VRPH_EPSILON)
        goto start_improving;
    else
        return;

}

bool VRP::before(int a, int b)
{
    ///
    /// This function returns TRUE if a comes before b in their route
    /// and FALSE if b is before a. An error is reported if a and b are in different routes.
    /// Should be used sparingly as it loops and can be slow for large routes.
    ///

    int i;

    if(a==VRPH_DEPOT || b==VRPH_DEPOT)
        report_error("%s: before called with VRPH_DEPOT\n",__FUNCTION__);

    if(route_num[a]!=route_num[b])
    {
        fprintf(stderr,"Ordering error: before called with %d and %d not in the same route!\n",a,b);
        report_error("%s: differnet routes\n",__FUNCTION__);
    }

    if(next_array[a]==b)
        return true;
    if(next_array[b]==a)
        return false;

    i=a;
    while(i>0 && i!=b)
        i=next_array[i];

    // At the end of this loop, if i<=0, then we're at the end of a route
    // and haven't encountered b ==> must have a after b in the route
    // If i==b, then we know that b follows a

    if(i==b)
        return true;
    else
        return false;

}


void VRP::update(VRPMove *M)
{
    ///
    /// Updates the solution information in terms of the move M.
    ///

    if(M->num_affected_routes==0)    // Nothing to do!
        return;

    int i;

    for(i=0; i < M->num_affected_routes; i++)
    {
        // Update length
        route[M->route_nums[i]].length = M->route_lens[i];

        // Update load
        route[M->route_nums[i]].load = M->route_loads[i];

        // Update # of customers
        route[M->route_nums[i]].num_customers = M->route_custs[i];
    }

    // Now update total_route_length
    total_route_length = M->new_total_route_length;

    // # of routes
    total_number_of_routes = M->total_number_of_routes;

    return;

}


void VRP::capture_best_solution()
{
    ///
    /// Determines if the current solution is the best found so far.
    ///

    if( (this->total_route_length < this->best_total_route_length) &&
        (VRPH_ABS(this->total_route_length - this->best_total_route_length) > VRPH_EPSILON) )
    {
        this->best_total_route_length=this->total_route_length;
        this->export_solution_buff(this->best_sol_buff);

    }


    if(this->total_route_length < this->solution_wh->worst_obj ||
        this->solution_wh->num_sols < this->solution_wh->max_size)
    {
        VRPSolution this_sol(this->num_nodes);

        this_sol.obj=this->total_route_length;
        this_sol.in_IP=false;

        // Export buffer
        this->export_canonical_solution_buff(this_sol.sol);

        this->solution_wh->add_sol(&this_sol, 0);
        // We don't know any information to help us know where to insert
    }

    return;


}
void VRP::add_route(int *route_buff)
{
    ///
    /// Adds the route in the provided buffer to the solution.  The new route should
    /// not have any nodes in common with the existing solution! The route_buff[] should
    /// be terminated with a -1.
    ///

    // We will do this by exporting the current solution to a buffer, appending the
    // new route to this buffer, and then importing the new resulting solution.
    // Probably not the fastest way, but we have many fields to update when adding a
    // new route!

    int *temp_buff;

    this->verify_routes("Before adding route\n");

    temp_buff=new int[this->num_original_nodes+2];
    this->export_solution_buff(temp_buff);


    int old_num=this->num_nodes;
    int i=0;
    while(route_buff[i]!=-1)
    {
        temp_buff[old_num+1+i]=route_buff[i];
        if(i==0)
            temp_buff[old_num+1+i]=-temp_buff[old_num+1+i];

        temp_buff[0]++;
        i++;
    }

    temp_buff[old_num+1+i]=VRPH_DEPOT;

    // Now import the new solution
    this->import_solution_buff(temp_buff);

    this->verify_routes("After adding route\n");

    delete [] temp_buff;

}

void VRP::print_stats()
{
    ///
    /// Prints the # of evaluations and moves performed for each
    /// heuristic operator.
    ///

    printf("                       (Moves,      Evaluations)\n");
    printf("     One Point Move:   (%010d, %010d)\n",
        this->num_moves[ONE_POINT_MOVE_INDEX], this->num_evaluations[ONE_POINT_MOVE_INDEX]);
    printf("     Two Point Move:   (%010d, %010d)\n",
        this->num_moves[TWO_POINT_MOVE_INDEX], this->num_evaluations[TWO_POINT_MOVE_INDEX]);

    return;

}

