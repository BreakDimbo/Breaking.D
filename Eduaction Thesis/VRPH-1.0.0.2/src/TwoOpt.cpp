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

// SEARCH
bool TwoOpt::search(class VRP *V, int b, int rules)
{
    ///
    /// Attempts to find the best Two-Opt move involving node b using the specified
    /// rules.
    /// If acceptable move is found, the move is made and all relevant solution
    /// modifications are made.
    ///

    VRPMove M;
    VRPMove BestM;
    int i,ii,j,k,a,c;
    int best_edges[4];
    int accept_type;

    memset(best_edges,-1,4*sizeof(int));
    accept_type=VRPH_FIRST_ACCEPT;    //default


    int *old_sol=NULL;

    // Create the search_space
    V->create_search_neighborhood(b, rules);

    BestM.savings=VRP_INFINITY;

    // Get the existing edges a-b and b-c
    a=VRPH_MAX(V->pred_array[b],0);
    c=VRPH_MAX(V->next_array[b],0);


    for(ii=0; ii<V->search_size; ii++)
    {
        j=V->search_space[ii];
        if(V->routed[j]==false)
        {
            fprintf(stderr,"Unrouted node %d found in TO.search\n",j);
            report_error("%s: Error in routed array\n",__FUNCTION__);
        }

        if(j!=b && j!=VRPH_DEPOT)
        {
            i=VRPH_MAX(V->pred_array[j],0);
            k=VRPH_MAX(V->next_array[j],0);

            // Four edges:  a-b, b-c, i-j, j-k
            // Now evaluate the 4 moves

            M.savings=VRP_INFINITY ;

            if(evaluate(V,a,b,i,j,rules, &M)==true)
            {

                    if(move(V, &M)==false)
                        report_error("%s: move error 1\n",__FUNCTION__);
                    else
                        return true;
            }


            if(evaluate(V,a,b,j,k,rules, &M)==true)
            {

                    if(move(V, &M)==false)
                        report_error("%s: move error 2\n",__FUNCTION__);
                    else
                        return true;
            }


            if(evaluate(V,b,c,i,j,rules, &M)==true)
            {
                    if(move(V, &M)==false)
                        report_error("%s: move error 3\n",__FUNCTION__);
                    else
                        return true;
            }


            if(evaluate(V,b,c,j,k,rules, &M)==true)
            {

                    if(move(V, &M)==false)
                        report_error("%s: move error 4\n",__FUNCTION__);
                    else
                        return true;
            }

        }
        // end if j!=VRPH_DEPOT && j!=b


        if(j==VRPH_DEPOT && j!=b)
        {
            // In this case we have many edges to consider
            // We will consider all edges of the form VRPH_DEPOT-t
            // and t-VRPH_DEPOT

            int current_start, current_end, current_route;
            current_start=abs(V->next_array[VRPH_DEPOT]);


            for(;;)
            {
                // Consider the edge VRPH_DEPOT-current_start
                int t=current_start;

                if(evaluate(V,a,b,VRPH_DEPOT,t,rules, &M)==true)
                {
                        if(move(V, &M)==false)
                            report_error("%s: VRPH_DEPOT move error 1\n",__FUNCTION__);
                        else
                            return true;
                }

                if(evaluate(V,b,c,VRPH_DEPOT,t,rules, &M)==true)
                {
                        if(move(V, &M)==false)
                            report_error("%s: VRPH_DEPOT move error 2\n",__FUNCTION__);
                        else
                            return true;
                }

                // Now try the t-VRPH_DEPOT edge
                current_route= V->route_num[current_start];
                current_end= V->route[current_route].end;
                t=current_end;

                if(evaluate(V,a,b,t,VRPH_DEPOT,rules, &M)==true)
                {
                        if(move(V, &M)==false)
                            report_error("%s: VRPH_DEPOT move error 3\n",__FUNCTION__);
                        else
                            return true;
                }

                if(evaluate(V,b,c,t,VRPH_DEPOT,rules, &M)==true)
                {
                        if(move(V, &M)==false)
                            report_error("%s: VRPH_DEPOT move error 4\n",__FUNCTION__);
                        else
                            return true;
                }

                // Now advance to the next node adjacent to the VRPH_DEPOT
                current_start=abs(V->next_array[current_end]);
                if(current_start==VRPH_DEPOT)    // We're done
                    break;
            }

            // end VRPH_DEPOT loop

        }
        // end j loop
    }
    // end ii loop


    if(accept_type==VRPH_FIRST_ACCEPT || BestM.savings==VRP_INFINITY)
    {
        return false;        // No moves found
    }


    report_error("%s: search shouldn't get here!\n",__FUNCTION__);

    return false;

}

bool TwoOpt::evaluate(class VRP *V, int a, int b, int c, int d, int rules, VRPMove *M)
{
    ///
    /// Considers the Two-Opt move involving the edges a-b and c-d and the
    /// provided rules.  If the move meets the rules, then
    /// the relevant changes to the solution are stored in the VRPMove M and
    /// the function returns true.  Returns false otherwise.
    ///
    ///

    V->num_evaluations[TWO_OPT_INDEX]++;

    // Look for overlaps
    if(  (b==c) || (a==c) || (a==d) || (b==d) || V->routed[a]==false ||
        V->routed[b]==false || V->routed[c]==false || V->routed[d]==false )
    {
        // Two Opt move doesn't make sense here...
        return false;
    }


    // Look for two edges involving the VRPH_DEPOT
    int num_depot_edges=0;
    if(a==VRPH_DEPOT) num_depot_edges++;
    if(b==VRPH_DEPOT) num_depot_edges++;
    if(c==VRPH_DEPOT) num_depot_edges++;
    if(d==VRPH_DEPOT) num_depot_edges++;
    if(num_depot_edges>1)
        return false;

    M->eval_arguments[0]=a;M->eval_arguments[1]=b;M->eval_arguments[2]=c;M->eval_arguments[3]=d;
    M->evaluated_savings=false;

    int a_route, c_route;

    if(a!=VRPH_DEPOT)
        a_route= V->route_num[a];
    else
        a_route= V->route_num[b];

    if(c!=VRPH_DEPOT)
        c_route= V->route_num[c];
    else
        c_route= V->route_num[d];

#if 1
    // First, we can see if any of the possible moves passes the savings rules
    // before going into more expensive evaluations.

    if(a_route==c_route)
    {
        M->num_affected_routes=1;
        M->savings = ( V->d[a][c]+V->d[b][d]) - ( V->d[a][b]+V->d[c][d]);

        // Can check feasibility
        if(M->savings+V->route[a_route].length > V->max_route_length)
            return false;

    }
    else
    {
        M->num_affected_routes=2;
        M->savings = (V->d[a][d]+V->d[c][b]) - (V->d[a][b]+V->d[c][d]) ;

        // Observation:  if savings>0, then one route must increase its length
        // by at least savings/2
        // So if both increase their lengths by savings/2 and are both infeasible
        // then we can say for certain that the move is infeasible.

        if(M->savings/2+V->route[a_route].length> V->max_route_length &&
            M->savings/2+V->route[c_route].length> V->max_route_length)
            return false;
    }

    M->evaluated_savings=false;
    if( (V->check_savings(M, rules))==false)
        return false;


#endif

    // Otherwise, the move is reasonable to consider further...


    if(a_route==c_route)
    {

        // same route-- the 2opt move here corresponds to reversing the portion of
        // this route that is between a and c
        Flip flip;

        // First the easy case - VRPH_DEPOT not involved

        if(a!=VRPH_DEPOT && b!=VRPH_DEPOT && c!=VRPH_DEPOT && d!=VRPH_DEPOT)
        {

            if(V->before(a,c)==true)
            {


                if( (flip.evaluate(V,a,d,M)==true)&&(V->check_move(M, rules)==true))
                {

                    return true;
                }
                else
                {

                    return false;
                }
            }
            else
            {
                // a is after c
                if( (flip.evaluate(V,c,b,M)==true) &&(V->check_move(M, rules)==true))
                {

                    return true;
                }
                else
                {

                    return false;
                }

            }
        }
        else // VRPH_DEPOT involved
        {
            // Otherwise, we have a VRPH_DEPOT node
            // We'll need to use the dummy node
            int dummy= V->dummy_index;

            if(a==VRPH_DEPOT)
            {
                // Put a dummy node between a and b and evaluate
                V->presert_dummy(b);
                if( (flip.evaluate(V,dummy,d,M)==true)&&(V->check_move(M, rules)==true))
                {
                    V->remove_dummy();
                    return true;
                }
                else
                {
                    V->remove_dummy();
                    return false;
                }
            }

            if(b==VRPH_DEPOT)
            {
                V->postsert_dummy(a);
                if( (flip.evaluate(V,c,dummy,M)==true)&&(V->check_move(M, rules)==true))
                {
                    V->remove_dummy();
                    return true;
                }
                else
                {
                    V->remove_dummy();
                    return false;
                }
            }

            if(c==VRPH_DEPOT)
            {

                V->presert_dummy(d);
                if( (flip.evaluate(V,dummy,b,M)==true)&&(V->check_move(M, rules)==true))
                {
                    V->remove_dummy();
                    return true;
                }
                else
                {
                    V->remove_dummy();
                    return false;
                }
            }

            if(d==VRPH_DEPOT)
            {

                V->postsert_dummy(c);

                if( (flip.evaluate(V,a,dummy,M)==true)&&(V->check_move(M, rules)==true))
                {
                    V->remove_dummy();
                    return true;
                }
                else
                {
                    V->remove_dummy();
                    return false;
                }
            }
        }
    }

    // else the edges are in different routes

    SwapEnds swap_ends;

    // First the easy case - VRPH_DEPOT not involved

    if(a!=VRPH_DEPOT && b!=VRPH_DEPOT && c!=VRPH_DEPOT && d!=VRPH_DEPOT)
    {
        if( (swap_ends.evaluate(V,a,c,M)==true)&&(V->check_move(M, rules)==true))
            return true;
        else
            return false;
    }
    // else we have some VRPH_DEPOT nodes to deal with

    int dummy= V->dummy_index;

    // Case 2:  a is the VRPH_DEPOT
    if(a==VRPH_DEPOT && b!=VRPH_DEPOT && c!=VRPH_DEPOT && d!=VRPH_DEPOT)
    {
        V->presert_dummy(b);
        if( (swap_ends.evaluate(V,dummy,c,M)==true)&&(V->check_move(M, rules)==true))
        {
            V->remove_dummy();
            return true;
        }
        else
        {
            V->remove_dummy();
            return false;
        }



    }

    // Case 3:  d is the VRPH_DEPOT
    if(d==VRPH_DEPOT && b!=VRPH_DEPOT && c!=VRPH_DEPOT && a!=VRPH_DEPOT)
    {
        V->postsert_dummy(c);
        if( (swap_ends.evaluate(V,a,c,M)==true)&&(V->check_move(M, rules)==true))
        {
            V->remove_dummy();
            return true;
        }
        else
        {
            V->remove_dummy();
            return false;
        }


    }

    // Case 4:  c is the VRPH_DEPOT
    if(a!=VRPH_DEPOT && b!=VRPH_DEPOT && c==VRPH_DEPOT && d!=VRPH_DEPOT)
    {

        V->presert_dummy(d);
        if( (swap_ends.evaluate(V,dummy,a,M)==true)&&(V->check_move(M, rules)==true))
        {
            V->remove_dummy();
            return true;
        }
        else
        {
            V->remove_dummy();
            return false;
        }
    }

    // Case 5:  b is the VRPH_DEPOT
    if(a!=VRPH_DEPOT && b==VRPH_DEPOT && c!=VRPH_DEPOT && d!=VRPH_DEPOT)
    {

        V->postsert_dummy(a);
        if( (swap_ends.evaluate(V,c,a,M)==true)&&(V->check_move(M, rules)==true))
        {
            V->remove_dummy();
            return true;
        }
        else
        {
            V->remove_dummy();
            return false;
        }

    }

    // Should never get here!

    report_error("%s: should never get here!\n",__FUNCTION__);
    return false;

}

bool TwoOpt::move(class VRP *V, VRPMove *M)
{
    ///
    /// Makes the actual solution modification implied by the Two-Opt
    /// move involving edges a-b and c-d.  Handles both intraroute and interroute
    /// moves
    ///
    int a,b,c,d;

    a=M->eval_arguments[0];b=M->eval_arguments[1];c=M->eval_arguments[2];d=M->eval_arguments[3];

    V->num_moves[TWO_OPT_INDEX]++;

    if(M->move_type==FLIP && M->move_type==SWAP_ENDS)
        report_error("%s: unknown move type\n",__FUNCTION__);

    bool uses_dummy=false;

    // We have at most one VRPH_DEPOT node
    if(a==VRPH_DEPOT)
    {
        uses_dummy=true;
        V->presert_dummy(b);
    }

    if(b==VRPH_DEPOT)
    {
        uses_dummy=true;
        V->postsert_dummy(a);
    }

    if(c==VRPH_DEPOT)
    {
        uses_dummy=true;
        V->presert_dummy(d);
    }

    if(d==VRPH_DEPOT)
    {
        uses_dummy=true;
        V->postsert_dummy(c);
    }

    if(M->move_type==FLIP)
    {
        Flip flip;

        if(flip.move(V, M->move_arguments[0], M->move_arguments[1])==true)
        {

            if(uses_dummy)
                V->remove_dummy();

            V->capture_best_solution();
            return true;
        }
        else
        {
            fprintf(stderr,"flip.move(%d,%d) is false\n",M->move_arguments[0],M->move_arguments[1]);
            report_error("%s: Flip error 1\n",__FUNCTION__);
        }
    }

    // SWAP_ENDS otherwise
    SwapEnds swap_ends;

    if(swap_ends.move(V,M->move_arguments[0],M->move_arguments[1])==true)
    {
        if(uses_dummy)
            V->remove_dummy();

        V->capture_best_solution();

        return true;
    }
    else
        report_error("%s: SwapEnds error 1\n",__FUNCTION__);


    return false;

}

