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

#ifndef _VRP_NODE_H
#define _VRP_NODE_H

#define VRPTW                    0
#define MAX_NEIGHBORLIST_SIZE    75

class VRPNode
{
public:

    double x;
    double y;
    double r;        // For polar
    double theta;    // coordinates
    int id;
    int demand;
    int cluster;
    VRPNeighborElement neighbor_list[MAX_NEIGHBORLIST_SIZE];


    // Constructor
    VRPNode();
    // Constructor for a d-day problem
    VRPNode(int d);
    // Destructor
    ~VRPNode();

    // Duplication
    void duplicate(VRPNode *N);
    void show();

  };

#endif

