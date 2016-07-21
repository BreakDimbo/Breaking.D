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

#ifndef _VRP_ROUTE_H
#define _VRP_ROUTE_H


#define MAX_NEIGHBORING_ROUTES		5
#define DUPLICATE_ROUTE				0
#define OVERWRITTEN_ROUTE			1
#define ADDED_ROUTE					2
#define BETTER_ROUTE				3


class VRPRoute
{
	///
	/// Stores information about a particular route.  The ordering
	/// field is not updated during the search and is filled in
	/// only when requested.
	///
public:

	VRPRoute();
	~VRPRoute();

	int start;
	int end;
	double length;
	int load;
	int num_customers;
	double obj_val;

};
#endif

