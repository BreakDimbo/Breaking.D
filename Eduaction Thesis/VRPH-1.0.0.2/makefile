# Set compiler and flags
CC=g++
CFLAGS= -O3 -Wall

# Set directory for static library and binaries 
# Defaults to ./lib and ./bin
VRPH_LIB_DIR = ./lib
VRPH_BIN_DIR = ./bin

# Set names of executables
SA_EXE = $(VRPH_BIN_DIR)/vrp_sa


# Set name of libraries needed by applicaitons
LIBS= -lvrph -lm


# Set to 0 if you don't have PLPLOT, 1 if you do
# and modify the directories below
HAS_PLPLOT= 1
ifeq ($(HAS_PLPLOT),1)
PLPLOT_INC_DIR= -I/usr/local/include/plplot
PLPLOT_LIB_DIR= -L/usr/local/lib
#PLPLOT_LIB= -lplplotd -lqsastime -lnistcd 
PLPLOT_LIB= -lplplotd -lqsastime -lnistcd
PLDEF=-DHAS_PLPLOT
else
PLPLOT_INC_DIR= 
PLPLOT_LIB_DIR=
PLPLOT_LIB=
PLDEF=
endif


# Various directories needed by the library and applications
INC_DIR= -I./inc/
LIB_DIR = -L$(VRPH_LIB_DIR)
VRPH_LIB = $(VRPH_LIB_DIR)/libvrph.a
TEST_OUTPUT = ./test_sols.out
SRCS= ./src/ClarkeWright.cpp ./src/Concatenate.cpp ./src/Flip.cpp\
./src/OnePointMove.cpp ./src/Postsert.cpp ./src/Presert.cpp\
./src/RNG.cpp ./src/Swap.cpp ./src/VRPTSPLib.cpp ./src/TwoPointMove.cpp ./src/VRP.cpp ./src/SwapEnds.cpp ./src/TwoOpt.cpp\
./src/VRPIO.cpp ./src/VRPDebug.cpp ./src/VRPMove.cpp  ./src/VRPNode.cpp ./src/VRPRoute.cpp \
./src/VRPSolvers.cpp ./src/VRPUtils.cpp ./src/VRPGraphics.cpp

OBJS=$(SRCS:.cpp=.o)

SA_SRC= ./src/apps/vrp_sa.cpp

all: $(VRPH_LIB) vrp_sa 

$(VRPH_LIB): $(OBJS)
	mkdir -p $(VRPH_LIB_DIR)
	$(AR) $(ARFLAGS) $@ $(OBJS)
	ranlib $@
	rm -rf $(OBJS)
	
.cpp.o:
	$(CC) $(CFLAGS) $(PLDEF) -c $(INC_DIR) $(PLPLOT_INC_DIR) $< -o $@


# An implementation of a Simulated Annealing-based algorithm for generating solutions
vrp_sa: $(OBJS) $(SA_SRC)
	mkdir -p $(VRPH_BIN_DIR)
	$(CC) $(CFLAGS) $(PLDEF) $(PLPLOT_INC_DIR) $(SA_SRC) $(INC_DIR) $(LIB_DIR) $(PLPLOT_LIB_DIR) $(LIBS) $(PLPLOT_LIB) -o $(SA_EXE) 


# Doxygen automatic documentation generation
doc: $(DOXYFILE)
	@echo Creating Doxygen documentation in ./doc directory
	mkdir -p ./doc
	$(DOX) $(DOXYFILE)
	@echo Run pdflatex or latex on ./doc/latex/refman.tex to create Doxygen manual

clean:
	-rm -rf $(OBJS)
	-rm -rf $(VRPH_LIB)
	-rm -rf $(SA_EXE)

