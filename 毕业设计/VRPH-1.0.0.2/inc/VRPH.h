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

// This is the primary include file for VRPH.  Applications should
// just need to include this file and everything else will
// be taken care of

#ifndef _VRPH_H
#define _VRPH_H

// Supported TSPLIB Problem types
#define VRPH_TSP                1
#define VRPH_CVRP               2

// Supported TSPLIB Edge Weight Formats
#define VRPH_FUNCTION           1
// Supported TSPLIB Coord types
#define VRPH_TWOD_COORDS        2

// Supported TSPLIB Edge Weight Types

#define VRPH_GEO                1
#define VRPH_EXACT_2D           2

// Useful macros
#define VRPH_MIN(X,Y)   ((X) < (Y) ?  (X) : (Y))
#define VRPH_MAX(X,Y)   ((X) < (Y) ?  (Y) : (X))
#define VRPH_ABS(a)     (((a) < 0) ? -(a) : (a))

// Different types of searches for inject_set
#define VRPH_RANDOM_SEARCH      1
#define VRPH_REGRET_SEARCH      2

#ifdef HAS_PLPLOT
#include "plplot.h"
#endif

// Colors for PLPlot
#define VRPH_BLACK          0
#define VRPH_RED            1
#define VRPH_YELLOW         2
#define VRPH_GREEN          3
#define VRPH_AQUA           4
#define VRPH_PINK           5
#define VRPH_WHEAT          6
#define VRPH_GRAY           7
#define VRPH_BROWN          8
#define VRPH_BLUE           9
#define VRPH_VIOLET         10
#define VRPH_CYAN           11
#define VRPH_TURQUOISE      12
#define VRPH_MAGENTA        13
#define VRPH_SALMON         14
#define VRPH_WHITE          15

// Options for plotting
#define VRPH_DEFAULT_PLOT           0
#define VRPH_BLACK_AND_WHITE        1
#define VRPH_COLOR                  2
#define VRPH_BOXED                  4
#define VRPH_NO_TITLE               8
#define VRPH_BARE_BONES             16
#define VRPH_NO_POINTS              32
#define VRPH_NO_DEPOT_EDGES         64
#define VRPH_WEIGHTED               128


#define VRPH_MAX_NUM_LAMBDAS        100
#define VRPH_STRING_SIZE            200
#define VRPH_DEPOT                  0
#define VRPH_PI                     3.14159265358979323846264
#define VRPH_RRR                    6378.3888
#define VRP_INFINITY                (1<<30)
#define VRP_INFEASIBLE              VRP_INFINITY
#define VRPH_EPSILON                .00001
#define VRPH_MAX_NUM_ROUTES         10000


#include "RNG.h"
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <time.h>
#include <string.h>
#include <memory.h>
#include <assert.h>
#include "VRPDebug.h"
#include "VRPHeuristic.h"
#include "VRPUtils.h"
#include "VRPNode.h"
#include "VRPRoute.h"
#include "VRPMove.h"
#include "VRP.h"
#include "Postsert.h"
#include "Presert.h"
#include "Concatenate.h"
#include "Swap.h"
#include "OnePointMove.h"
#include "TwoPointMove.h"
#include "ClarkeWright.h"
#include "Flip.h"
#include "TwoOpt.h"
#include "SwapEnds.h"


#endif


