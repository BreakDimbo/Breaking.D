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

VRPMove::VRPMove()
{
    this->evaluated_savings=false;
    this->move_type=-1;
    this->new_total_route_length=VRP_INFINITY;
    this->num_affected_routes=-1;
    this->num_arguments=-1;
    this->savings=-1;
    this->total_number_of_routes=-1;


}


VRPMove::~VRPMove()
{

}


bool VRPMove::is_better(VRP *V, VRPMove *M2, int rules)
{
    ///
    /// Evaluates this move versus M2 in terms of the provided
    /// rules.  Returns true of this move is superior to M2
    /// and false otherwise.
    ///

    if(M2->num_affected_routes==-1)
    {
        // M2 does not have meaningful information, so return true
        // Probably has savings=VRP_INFINITY
        return true;
    }


    // We are only concerned about the savings (increase/decrese) in total length
    // This is the default approach
    if(rules & VRPH_SAVINGS_ONLY)
    {
        // Decide in terms of total length only

        if(this->savings <= M2->savings)
            return true;
        else
            return false;

    }


    // We will try to maximize the sum of the squares of the # of customers on a route



    // Shouldn't get here!
    report_error("%s: Reached bizarre place with rules=%08x\n",__FUNCTION__,rules);
    return false;

}

