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
#include <stdarg.h>


void report_error(const char* format, ...)
{
    ///
    /// Prints the message and function name to stderr and terminates the program.
    ///

    va_list args;

    va_start(args, format);
    vfprintf(stderr, format, args);
    va_end(args);

    fprintf(stderr,"Exiting\n");
    exit(-1);
}

