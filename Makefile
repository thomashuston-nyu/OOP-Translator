# Makefile
#***************************************************************************
#
# Master Makefile
#
# (C) 1999 Jacob Dreyer - Geotechnical Software Services
# jacob.dreyer@geosoft.no - http://geosoft.no
#
# Modifications Copyright (C) 2001-2007 Robert Grimm
# rgrimm@alum.mit.edu
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
#
#***************************************************************************

ifdef OOP_ROOT
include $(OOP_ROOT)/Makebase
endif

#***************************************************************************
#
# Packages
#
# Use fully qualified package names for the list of packages.
#
#***************************************************************************

PACKAGES = \
					 pcp \
					 pcp.translator

#***************************************************************************
#
# Javadoc
#
# These variables define some of the layout of the javadoc HTML
# output. See javadoc manual for details.
#
#***************************************************************************

WINDOWTITLE     = 'pcp - the Producer of C++ Programs'
DOCTITLE        = 'Java Class Documentation for pcp'
BOTTOM          = '<center>(C) Copyright 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan</center>'
JAVADOC         = javadoc
SUN_JAVADOCS    = http://download.oracle.com/javase/1,5.0/docs/api

JAVADOC_OPTIONS = \
	-J-Xmx1024m \
	-source 1.5 \
	-protected \
	-d $(DOC_DIR) \
	-sourcepath $(SOURCE_DIR) \
	-use \
	-version \
	-splitIndex \
	-windowtitle $(WINDOWTITLE) \
	-doctitle $(DOCTITLE) \
	-bottom $(BOTTOM) \
	-overview overview.html \
	-linkoffline $(SUN_JAVADOCS) $(JAVA_DEV_ROOT) \
	-linkoffline $(JAVA_DEV_ROOT)/doc $(OOP_ROOT)

#***************************************************************************
#
# Rules and dependencies.
#
# The following commands are valid:
#
#  1. make configure       - Configure local system's xtc.Limits
#  2. make classes         - Make all class files for all packages
#  3. make                 - Same as (2)
#  4. make parsers         - Make all parsers
#  5. make doc             - Make all documentation
#  6. make jars            - Make binary distribution
#  7. make dist            - Make source distribution
#  8. make stats           - Print source code statistics
#  9. make check           - Run all regression tests
# 10. make clean           - Clean classes
# 11. make clobber         - Clean classes, documentation, distributions
# 12. make clobber-parsers - Clean parser sources
#
#
# For the CLASS, JNI, JAR, and CLEAN variables, all backslashes are
# replaced by forward slashes. This substitution is only important
# when using this makefile on Windows. When building in an MS-DOS
# shell, make treats both backslash and forward slash as a path
# segment delimiter. However, Unix shells, such as bash, require
# forward slashes. File names passed to the Java tools may have a mix
# of backslash and forward slash, because they treat both as a path
# segment delimiter.
#
#***************************************************************************

PACK_PATHS  = $(subst .,/,$(PACKAGES))
JAVA        = $(subst \,/,$(PACK_PATHS:%=$(SOURCE_DIR)/%.java))
CLEAN       = $(subst \,/,$(PACK_PATHS:%=$(SOURCE_DIR)/%.clean))
CLASS       = $(subst \,/,$(PACK_PATHS:%=$(SOURCE_DIR)/%.class))

.PHONY  : default classes
.PHONY  : clean
.PHONY  : doc

default : classes

classes : $(JAVA)
%.java :
	$(MAKE) -C $* classes

doc     :
	$(JAVADOC) $(JAVADOC_OPTIONS) $(PACKAGES)

clean   : $(CLEAN)
%.clean :
	$(RM) $*/*.class
