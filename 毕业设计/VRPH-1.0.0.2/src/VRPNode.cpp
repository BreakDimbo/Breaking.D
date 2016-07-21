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

VRPNode::VRPNode()
{
    ///
    /// Default constructor for the VRPNode class.
    /// Allocates an array of MAX_NEIGHBORLIST_SIZE VRPNeighborElements
    /// for the node.
    ///
    int i;

    x = 0;
    y = 0;

    for(i = 0;i < MAX_NEIGHBORLIST_SIZE; i++)
    {
        this->neighbor_list[i].position = 0;
        this->neighbor_list[i].val = 0;
    }

}

VRPNode::~VRPNode()
{

}


