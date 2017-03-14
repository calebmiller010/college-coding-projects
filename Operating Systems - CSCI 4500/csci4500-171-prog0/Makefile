# (Last modification: January 18, 2016)
#
# This makefile is used to create (or recreate) the executable file named
# prunw from the source file prunw.c in this directory.
#
# REMEMBER THAT THE FIRST CHARACTER IN A COMMAND LINE IN A MAKEFILE
# MUST BE A TAB CHARACTER, NOT SOME NUMBER OF SPACES.
#
# TEST YOUR MAKEFILE AND CORRECT IT IF IT FAILS!
#
# Lines in a makefile (or Makefile) that begin with # are comment lines,
# like these. They don't affect the makefile at all. If you choose to use
# this file as a template for your own makefile, please remove all these
# pedantic comments.
#
# There are (minimally) two other types of lines in a makefile (other than
# comments, of course): lines that define the dependencies between files,
# and lines that indicate how to create # a file.
#
# DEPENDENCY LINES
# ----------------
# A line that begins with a name (which is frequently the name of a file),
# then a colon, and a space-separated list of other names specifies that the
# first named "object" is dependent on the other objects. For example, look
# (below) at the line that begins with the word "prunw". It has only one name
# after the colon, specifically "prunw.c". This tells the make program that
# the object (in this case, the file prunw) is dependent on the file prunw.c
# If the make program needs to construct prunw, it then knows what files it
# must use. In addition, if prunw already exists, make will check the date of
# last modification for prunw (the so-called "target") and the file(s) on which
# it is dependent (in this case, just prunw.c). If prunw.c was modified more
# recently that prunw, then make knows it must recreate prunw from the modified
# prunw.c .
#
# COMMAND LINES
# -------------
# The other kind of line that appears in a makefile is a command. Commands
# always # follow dependency lines and begin with a tab character.
# In this simple makefile we see that if the make program decides it needs to
# recreate prunw (because it is out of date -- prunw.c was modified after prunw
# was created, or because it doesn't exist), then it must execute the command
# "cc -o prunw prunw.c". Of course there could be more than one command line
# given, and they're used in the order specified. Remember that each command
# line MUST begin with a tab character, NOT eight spaces.
#
# INVOKING THE MAKE COMMAND
# -------------------------
# Make is often invoked by just using the command "make". In this cases it will
# first look for a file named "makefile" or "Makefile" in the current working
# directory. If it doesn't find such a file, it will report failure. A specific
# makefile with a different name can be used by supplying the "-f name" option,
# where "name" is replaced with the name of the makefile to be used. For
# example, you could use this command to invoked make and have it use the file
# rebuild:  "make -f rebuild".
#
# Regardless of the particular makefile being used, make will check the file
# for syntax; if errors exist, make will report them and quit.
# Otherwise, with no command line arguments (other than perhaps -f), make will
# attempt to build the first named target. That is, make looks for the first
# dependency line to identify the target it is to buld, determines if it needs
# to build it, and if necessary, executes the specified commands following the
# dependency line.
#
# In this makefile, the first target is "all". Of course, "all" depends on
# prunw, so make then looks for the dependency line for prunw.
#
# MAKE WITH AN EXPLICIT TARGET SPECIFIED
# --------------------------------------
# Make could be invoked with a target name on the command line, as in this
# example: "make clean". This causes make to attempt to build (if necessary)
# the specified target. In this case, "clean" really isn't a file that will
# ever exist, but make doesn't care -- it just notes that "clean" isn't there
# so it executes the command(s) needed to "construct" clean. Here that just
# deletes the executable file "prunw", which "cleans" up the directory.
# Providing a "clean" target is useful, since it makes removal of all old
# crufty files before rebuilding the main target.
#
# OTHER MAKE FEATURES
# -------------------
# There are numerous other features of the make program, but this should give
# you enough information to get started using it. Also note that you will not
# be capable of successfully executing the make command in this (distribution)
# directory, since it is protected from writing. Instead, copy this makefile
# and prunw.c to one of your directories and then use the make command there.
#
# Everything above is commentary and should (ideally) be deleted before using
# this as a template for your own makefiles. Also note that the name of the
# files mentioned in the "meat" of the makefile (below) should be changed to
# appropriate names.
#
#############################################################################
# ONCE AGAIN, NOTE THAT LINES THAT APPEAR TO BEGIN WITH BLANKS DO NOT.
# SUCH COMMAND LINES **MUST** BEGIN WITH A TAB CHARACTER.
#
# PLEASE REMOVE THESE EXTREMELY PEDANTIC COMMENTS IN YOUR OWN MAKEFILE(S).
#
# TEST YOUR MAKEFILE AND CORRECT IT IF IT FAILS.
#############################################################################
#
all: prunw

prunw: prunw.c
	cc -o prunw prunw.c

clean:
	rm -f prunw
