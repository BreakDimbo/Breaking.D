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

#ifndef _VRP_UTIL_H
#define _VRP_UTIL_H


#define MAX_FILES				20000
#define MAX_FILENAME_LENGTH		40


class VRPSavingsElement
{
public:
	// Useful to make some of the bookkeeping simpler when
	// calculating the savings.
	double savings;
	int position;
	int i;
	int j;
};


class VRPNeighborElement
{
public:
	double val;
	int position;
};


class VRPNeighborhood
{
public:
	int move_type;
	int node_1, node_2;
	class VRPMove *Moves;
	int size;

	VRPNeighborhood(int n);
};

struct VRPSegment
{
	///
	/// Contains information about a particular segment of
	/// a route.
	///

	int segment_start;
	int segment_end;

	int num_custs;
	int load;
	double len;

};


double VRPDistance(int type, double x1, double y1, double x2, double y2);
int VRPDistanceCompare(const void *a, const void *b);
int VRPIntCompare(const void *a, const void *b);
int VRPSavingsCompare (const void *a, const void *b);
int VRPNeighborCompare (const void *a, const void *b);
int VRPCheckTSPLIBString(char *s);
int VRPGetDimension(char *filename);


#endif





